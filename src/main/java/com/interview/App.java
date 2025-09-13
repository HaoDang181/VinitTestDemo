package com.interview;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gson.Gson;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;

public class App {

    public static void main(String[] args) {
        final List<String> playerNames = new ArrayList<>();
        final List<Double> playerRatings = new ArrayList<>();
        final List<Response> playerInfo = new ArrayList<>();

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                    .setHeadless(false)
                    .setArgs(List.of("--disable-blink-features=AutomationControlled"))
            );

            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                              "AppleWebKit/537.36 (KHTML, like Gecko) " +
                              "Chrome/120.0.0.0 Safari/537.36")
                .setViewportSize(1366, 768)
                .setLocale("en-US")
                .setTimezoneId("America/New_York")
                .setIgnoreHTTPSErrors(true)
            );

            Page page = context.newPage();
            get2700GreaterRatingPlayers(page, playerNames, playerRatings);

            processPlayerInfo(context, playerNames, playerInfo);
            String sheetId = args.length > 0 ? args[0] : "1BiAnBg8elL2pETWfWWrIw1s4QuHesrJRRAhKO5zGMZc";
            writeToSheet(sheetId, "A1", playerInfo, playerRatings);

            browser.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void get2700GreaterRatingPlayers(Page page, List<String> playerNames, List<Double> playerRatings) {
        page.navigate(
            "https://2700chess.com/",
            new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                .setTimeout(60000)
        );

        page.waitForSelector("table#live-ratings-table tr");

        for (ElementHandle row : page.locator("table#live-ratings-table tr").elementHandles()) {
            List<ElementHandle> cells = row.querySelectorAll("td");
            if (cells.size() >= 5) {
                String rating = cells.get(4).innerText().trim();
                try {
                    double ratingValue = Double.parseDouble(rating);
                    if (ratingValue >= 2700) {
                        playerRatings.add(ratingValue);
                        playerNames.add(cells.get(2).innerText().trim());
                    }
                } catch (NumberFormatException ignore) {
                }
            }
        }

        page.close();
    }

    private static void processPlayerInfo(BrowserContext context, List<String> playerLinks, List<Response> playerInfo) {
        Page page = context.newPage();
        try {
            List<String> urls = new ArrayList<>(playerLinks.size());
            for (String name : playerLinks) {
                String encoded = URLEncoder.encode(name, StandardCharsets.UTF_8).replace("+", "%20");
                urls.add("https://2700chess.com/next/search-player?name=" + encoded);
            }

            Object result = page.evaluate(
                "urls => Promise.all(urls.map(u => fetch(u).then(r => r.text())))",
                urls
            );

            @SuppressWarnings("unchecked")
            List<String> responses = result instanceof List ? (List<String>) result : Collections.emptyList();

            Gson gson = new Gson();
            responses.forEach(r -> {
                try {
                    Response res = gson.fromJson(r, Response.class);
                    if (res != null) {
                        playerInfo.add(res);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to parse JSON: " + r);
                    e.printStackTrace();
                }
            });
        } finally {
            page.close();
        }
    }

    private static void writeToSheet(String spreadSheetId, String range, List<Response> playerInfo, List<Double> playerRatings) throws Exception {
        Sheets service = GoogleSheetUtil.getSheetsService();

        List<List<Object>> values = new ArrayList<>();
        values.add(List.of(
            "ID", "FIDE ID", "Name", "Rating", "Title", "Country", "Birth Year", "Birth Date",
            "Birth Place", "Death Date", "Death Place", "Bio",
            "Wiki Link", "Facebook Link", "Twitter Link", "Instagram Link", "Last Update"
        ));

        for (int i = 0; i < playerInfo.size(); i++) {
            Response p = playerInfo.get(i);
            Profile profile = p.getProfile();
            if (profile == null) continue;

            String cleanBio   = profile.getBio() != null ? Jsoup.parse(profile.getBio()).text() : "";
            String deathDate  = profile.getDdate() != null ? profile.getDdate() : "";
            String deathPlace = profile.getDplace() != null && !profile.getDplace().isBlank() ? profile.getDplace() : "";

            Double rating = i < playerRatings.size() ? playerRatings.get(i) : null;

            values.add(Arrays.asList(
                profile.getId(),
                profile.getFideid(),
                profile.getName(),
                rating, 
                profile.getTitle(),
                profile.getCountry(),
                profile.getByear(),
                profile.getBdate(),
                profile.getBplace(),
                deathDate,
                deathPlace,
                cleanBio,
                profile.getWikiLink(),
                profile.getFacebookLink(),
                profile.getTwitterLink(),
                profile.getInstagramLink(),
                profile.getLastupdate()
            ));
        }

        ValueRange body = new ValueRange().setValues(values);
        service.spreadsheets().values()
            .update(spreadSheetId, range, body)
            .setValueInputOption("RAW")
            .execute();

        System.out.println("Data written to Google Sheet successfully.");
    }

}

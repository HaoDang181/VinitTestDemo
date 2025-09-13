package com.interview;

import java.io.FileInputStream;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

public class GoogleSheetUtil {
    private static final String APPLICATION_NAME = "Chess player info";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public static Sheets getSheetsService() throws Exception {
        GoogleCredential credential = GoogleCredential.fromStream(
            new FileInputStream("C:\\Project\\demo2700rating\\src\\main\\resources\\credentials.json")
        ).createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();
    }
}

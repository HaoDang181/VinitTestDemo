## Setup

1. Clone the repo.
2. Create a project in [Google Cloud Console](https://console.cloud.google.com/).
3. Enable the Google Sheets API.
4. Create OAuth credentials and download `credentials.json`.
5. Place it in the project root (do NOT commit it). (place it in folder resources)
6. Share your target Google Sheet with the client email from your credentials.
7. Run the program:

   ```bash
   mvn clean compile exec:java -Dexec.args="YOUR_SHEET_ID"
8. Wait until the termial show this message "Data written to Google Sheet successfully.", then your sheets is fill with data!

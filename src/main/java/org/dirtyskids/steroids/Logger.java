package org.dirtyskids.steroids;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Logger {
    private final String webhookUrl;
    private final FileConfiguration config;

    public Logger(FileConfiguration config) {
        this.webhookUrl = config.getString("webhook_url");
        this.config = config;
    }

    public void log(String title, String description, int color) {
        if (webhookUrl == null || webhookUrl.isEmpty()) {
            Bukkit.getLogger().warning("Webhook URL is not set. Skipping logging.");
            return;
        }

        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = String.format(
                    "{\"embeds\":[{\"title\":\"%s\",\"description\":\"%s\",\"color\":%d}]}",
                    title, description, color
            );

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes());
                os.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 204) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    Bukkit.getLogger().warning("Failed to send log to Discord webhook. Response code: " + responseCode + ", Response: " + response.toString());
                }
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Error while sending log to Discord webhook: " + e.getMessage());
        }
    }
}
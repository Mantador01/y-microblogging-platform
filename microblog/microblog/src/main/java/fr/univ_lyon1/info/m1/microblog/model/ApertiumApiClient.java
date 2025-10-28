package fr.univ_lyon1.info.m1.microblog.model;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Client for the Apertium translation API.
 */
public final class ApertiumApiClient {

    // Constructeur privé pour empêcher l'instanciation de la classe
    private ApertiumApiClient() {
    }
    
    // URL de l'API de traduction
    private static String baseUrl = "https://apertium.org/apy/translate";

    // Setter pour définir une URL dynamique
    public static void setBaseUrl(final String baseUrl) {
        ApertiumApiClient.baseUrl = baseUrl;
    }

    // Méthode existante avec l'utilisation de l'URL dynamique
    /**
     * Translates the given text from the source language to the target language 
     * using the Apertium API.
     *
     * @param text       The text to be translated.
     * @param sourceLang The source language code (e.g., "en" for English).
     * @param targetLang The target language code (e.g., "es" for Spanish).
     * @return The translated text, or null if an error occurred.
     * @throws IOException          If an I/O error occurs when sending or receiving.
     * @throws InterruptedException If the operation is interrupted.
     */
    public static String translate(final String text,
                                   final String sourceLang,
                                   final String targetLang)
            throws IOException, InterruptedException {
        String params = "q=" + URLEncoder.encode(text, StandardCharsets.UTF_8)
                + "&langpair=" + URLEncoder.encode(
                    sourceLang + "|" + targetLang, StandardCharsets.UTF_8);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "?" + params))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            try {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
                JsonObject responseData = jsonObject.getAsJsonObject("responseData");
                return responseData.get("translatedText").getAsString();
            } catch (Exception e) {
                System.err.println("Erreur lors du parsing de la réponse JSON:");
                e.printStackTrace();
                return null;
            }
        } else {
            System.err.println("Erreur lors de l'appel à l'API de traduction:");
            System.err.println("Statut HTTP: " + response.statusCode());
            return null;
        }
    }
}

package fr.univ_lyon1.info.m1.microblog.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Classe utilitaire pour gérer l'authentification avec l'API Reddit.
 */
public final class RedditAuth {

    // URL de base pour l'authentification
    private static String baseUrl = "https://www.reddit.com";

    /**
     * Définit l'URL de base pour l'authentification avec l'API Reddit.
     *
     * @param actBaseUrl L'URL de base pour l'authentification
     */
    public static void setBaseUrl(final String actBaseUrl) {
        baseUrl = actBaseUrl;
    }

    // Constructeur privé pour empêcher l'instanciation
    private RedditAuth() {
        throw new UnsupportedOperationException("Classe utilitaire, ne peut pas être instanciée.");
    }

    /**
     * Authentifie l'utilisateur avec l'API Reddit et retourne un token d'accès.
     *
     * @return Le token d'accès OAuth2
     * @throws Exception Si l'authentification échoue
     */
    public static String authenticate() throws Exception {
        OkHttpClient client = new OkHttpClient();
    
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        String credentials = Credentials.basic("FZHHenp01FA_g-gshFRi5Q", 
                                                "uqLJVTwQ8gaGr6RclZYiu3pHoFwtkg");
    
        Request request = new Request.Builder()
                .url(baseUrl + "/api/v1/access_token")
                .header("Authorization", credentials)
                .header("User-Agent", "JavaRedditApp/1.0")
                .post(formBody)
                .build();
    
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                // Ajout d'une trace pour afficher la réponse exacte
                System.err.println("Erreur d'authentification : " + response.body().string());
                throw new Exception("Erreur d'authentification : " + response.message());
            }
    
            String responseBody = response.body().string();
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            return json.get("access_token").getAsString();
        }
    }
    
}

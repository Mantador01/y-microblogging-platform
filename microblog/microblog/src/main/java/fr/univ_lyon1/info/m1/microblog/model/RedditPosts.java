package fr.univ_lyon1.info.m1.microblog.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Classe utilitaire pour gérer la récupération des posts depuis Reddit.
 */
public final class RedditPosts {

    // URL de base pour les requêtes Reddit
    private static String baseUrl = "https://oauth.reddit.com";

    /**
     * Définit l'URL de base pour les requêtes Reddit.
     *
     * @param actBaseUrl L'URL de base pour les requêtes Reddit
     */
    public static void setBaseUrl(final String actBaseUrl) {
        baseUrl = actBaseUrl;
    }

    // Constructeur privé pour empêcher l'instanciation
    private RedditPosts() {
        throw new UnsupportedOperationException("Classe utilitaire, ne peut pas être instanciée.");
    }

    /**
     * Récupère les posts depuis un subreddit donné.
     *
     * @param accessToken Le token d'accès pour l'API Reddit
     * @param subreddit Le subreddit cible
     * @throws Exception En cas d'erreur dans l'appel à l'API
     */
    public static void getPosts(final String accessToken,
                                 final String subreddit, 
                                final Y model) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(baseUrl + "/r/" + subreddit + "/hot?limit=10")
                .header("Authorization", "Bearer " + accessToken)
                .header("User-Agent", "JavaRedditApp/1.0")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Erreur lors de la récupération des posts : " 
                + response.message());
            }

            String responseBody = response.body().string();
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonArray posts = json.getAsJsonObject("data").getAsJsonArray("children");

            for (JsonElement postElement : posts) {
                JsonObject postData = postElement.getAsJsonObject().getAsJsonObject("data");
                String title = postData.get("title").getAsString();
                String authorName = postData.get("author").getAsString();
            
                // Créez un utilisateur s'il n'existe pas déjà
                User author = model.getUsers().stream()
                    .filter(u -> u.getId().equals(authorName))
                    .findFirst()
                    .orElseGet(() -> {
                        User newUser = new User(authorName);
                        model.createUser(authorName);
                        return newUser;
                    });
            
                // Ajoutez le message au modèle
                TextMessage message = new TextMessage(title);
                message.setAuthor(author);
                model.addMessage(message);
            }            
        }
    }
}

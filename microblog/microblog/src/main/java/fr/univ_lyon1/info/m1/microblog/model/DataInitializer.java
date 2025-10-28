package fr.univ_lyon1.info.m1.microblog.model;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
* la classe DataInitializer charge les données initiales à partir d'un fichier JSON.
*/
public class DataInitializer {
    private Y model;

    /**
     * Constructeur de la classe DataInitializer.
     *
     * @param model le modèle à initialiser
     */
    public DataInitializer(final Y model) {
        this.model = model;
    }

    /**
     * Charge les données initiales à partir d'un fichier JSON.
     */
    public void initializeData() {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() { }.getType();

            // Charger le fichier JSON depuis les ressources
            InputStreamReader reader = new InputStreamReader(
                getClass().getResourceAsStream("/initialData.json"),
                "UTF-8"
            );

            Map<String, Object> data = gson.fromJson(reader, type);

            // Charger les utilisateurs
            List<String> users = (List<String>) data.get("users");
            for (String userId : users) {
                model.createUser(userId);
            }

            // Charger les messages
            List<Map<String, String>> messages = (List<Map<String, String>>) data.get("messages");
            for (Map<String, String> messageData : messages) {
                String content = messageData.get("content");
                String authorId = messageData.get("author");

                // Trouver l'utilisateur auteur
                User author = model.getUsers().stream()
                    .filter(u -> u.getId().equals(authorId))
                    .findFirst()
                    .orElse(null);

                if (author != null) {
                    TextMessage message = new TextMessage(content);
                    message.setAuthor(author);
                    model.addMessage(message);
                } else {
                    System.err.println("Auteur non trouvé pour le message : " + content);
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

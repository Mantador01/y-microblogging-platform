package fr.univ_lyon1.info.m1.microblog.controller;

import fr.univ_lyon1.info.m1.microblog.model.DataInitializer;
import fr.univ_lyon1.info.m1.microblog.model.RedditAuth;
import fr.univ_lyon1.info.m1.microblog.model.RedditPosts;
import fr.univ_lyon1.info.m1.microblog.model.Y;
import fr.univ_lyon1.info.m1.microblog.view.JfxView;
import fr.univ_lyon1.info.m1.microblog.view.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The App class initializes the model, view, and controller,
 * and starts the application.
 */
public class App extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {

        // Affichez la fenêtre de connexion
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.display(primaryStage);

        // Vérifiez si l'utilisateur est authentifié
        if (!loginWindow.isAuthenticated()) {
            System.out.println("Connexion annulée !");
            System.exit(0); // Quittez l'application si non authentifié
        }

        // Continuez avec l'application principale
        System.out.println("Connecté avec l'utilisateur : " + loginWindow.getUsername());

        // Initialize the model
        Y model = new Y();

        // Initialize the data
        DataInitializer initializer = new DataInitializer(model);
        initializer.initializeData();

        // Intégration de l'API Reddit (pour récupérer des posts)
        try {
            String accessToken = RedditAuth.authenticate(); 
            RedditPosts.getPosts(accessToken, "programming", model); 
        } catch (Exception e) {
            e.printStackTrace(); // Affiche les erreurs liées à l'API de Reddit
        }        

        // Initialize the first view
        JfxView view1 = new JfxView();
        view1.initialize(primaryStage, 600, 600);

        // Initialize the second view
        Stage secondStage = new Stage();
        JfxView view2 = new JfxView();
        view2.initialize(secondStage, 600, 400);

        // Create the main controller with both views
        MainController controller = new MainController(model, view1, view2);

        // Start the application
        controller.startApp();
    }

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }
}

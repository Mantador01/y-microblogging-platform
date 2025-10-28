package fr.univ_lyon1.info.m1.microblog.view;

import java.io.BufferedReader;
import java.io.FileReader;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/**
 * Fenêtre de connexion pour demander les identifiants de l'utilisateur.
 */
public class LoginWindow {
    private boolean authenticated = false;
    private String username;
    private String password;

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Affiche la fenêtre de connexion.
     *
     * @param primaryStage Le stage principal.
     */
    public void display(final Stage primaryStage) {
        Stage loginStage = new Stage();
        loginStage.setTitle("Connexion");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Champs pour l'utilisateur et le mot de passe
        Label usernameLabel = new Label("Nom d'utilisateur :");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Mot de passe :");
        PasswordField passwordField = new PasswordField();

        // Boutons pour connexion et inscription
        Button loginButton = new Button("Se connecter");
        Button signupButton = new Button("S'inscrire");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        loginButton.setOnAction(event -> {
                try (BufferedReader reader = new BufferedReader(
                        new FileReader("./src/main/resources/users.txt"))) {
                    String line;
                    boolean valid = false;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(":");
                        if (parts[0].equals(usernameField.getText()) 
                                && parts[1].equals(passwordField.getText())) {
                            valid = true;
                            break;
                        }
                    }
                    if (valid) {
                        this.username = usernameField.getText();
                        authenticated = true;
                        loginStage.close();
                    } else {
                        errorLabel.setText("Nom d'utilisateur ou mot de passe incorrect.");
                    }
                } catch (Exception e) {
                    errorLabel.setText("Erreur lors de la vérification.");
                }
            });

        signupButton.setOnAction(event -> {
            SignupWindow signupWindow = new SignupWindow();
            signupWindow.display(primaryStage); // Affiche la fenêtre d'inscription
        });

        // Disposition des éléments
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 2);
        gridPane.add(signupButton, 1, 2);
        gridPane.add(errorLabel, 0, 3, 2, 1);

        // Création de la scène
        Scene loginScene = new Scene(gridPane, 400, 250);
        loginStage.setScene(loginScene);
        loginStage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée
    }
}

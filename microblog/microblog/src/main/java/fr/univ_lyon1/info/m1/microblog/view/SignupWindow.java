package fr.univ_lyon1.info.m1.microblog.view;

import java.io.FileWriter;
import java.io.PrintWriter;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Fenêtre d'inscription pour permettre à l'utilisateur de créer un compte.
 */
public class SignupWindow {
    private boolean signupSuccessful = false;

    public boolean isSignupSuccessful() {
        return signupSuccessful;
    }

    /**
     * Affiche la fenêtre d'inscription.
     *
     * @param primaryStage Le stage principal.
     */
    public void display(final Stage primaryStage) {
        Stage signupStage = new Stage();
        signupStage.setTitle("Inscription");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Champs pour l'utilisateur et le mot de passe
        Label usernameLabel = new Label("Nom d'utilisateur :");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Mot de passe :");
        PasswordField passwordField = new PasswordField();

        // Bouton pour soumettre l'inscription
        Button signupButton = new Button("S'inscrire");
        Label successLabel = new Label();
        successLabel.setStyle("-fx-text-fill: green;");

        signupButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                successLabel.setText("Les champs ne peuvent pas être vides.");
                successLabel.setStyle("-fx-text-fill: red;");
            } else {
                // Stocker les informations d'inscription dans un fichier
                try (PrintWriter writer = 
                        new PrintWriter(new FileWriter("./src/main/resources/users.txt", true))) {
                    writer.println(username + ":" + password);
                    successLabel.setText("Inscription réussie !");
                    signupSuccessful = true;
                    signupStage.close(); // Fermez la fenêtre après l'inscription
                } catch (Exception e) {
                    successLabel.setText("Erreur lors de l'inscription.");
                    successLabel.setStyle("-fx-text-fill: red;");
                }
            }
        });

        // Disposition des éléments
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(signupButton, 0, 2);
        gridPane.add(successLabel, 1, 2);

        // Création de la scène
        Scene signupScene = new Scene(gridPane, 400, 200);
        signupStage.setScene(signupScene);
        signupStage.showAndWait(); // Bloque jusqu'à ce que la fenêtre soit fermée
    }
}

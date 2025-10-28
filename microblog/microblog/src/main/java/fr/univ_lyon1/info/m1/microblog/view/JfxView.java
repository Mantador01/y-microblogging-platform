package fr.univ_lyon1.info.m1.microblog.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import fr.univ_lyon1.info.m1.microblog.model.ChronologicalStrategy;
import fr.univ_lyon1.info.m1.microblog.model.DisplayStrategy;
import fr.univ_lyon1.info.m1.microblog.model.IModel;
import fr.univ_lyon1.info.m1.microblog.model.IModelListener;
import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.MostRelevantStrategy;
import fr.univ_lyon1.info.m1.microblog.model.RecentRelevantStrategy;
import fr.univ_lyon1.info.m1.microblog.model.User;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Main class of the View (GUI) of the application. Implements
 * {@link IModelListener} to be notified when the model changes.
 */
public class JfxView implements IView, IModelListener {

    private HBox users = new HBox();
    private ViewListener listener;
    private ComboBox<DisplayStrategy> strategyComboBox;

    /**
     * Interface to handle view events such as message publishing and bookmark
     * toggling.
     */
    public interface ViewListener {

        /**
         * Triggered when a message is published by the user.
         *
         * @param content the content of the published message
         * @param user the user who published the message
         */
        void onMessagePublished(String content, User user);

        /**
         * Triggered when a bookmark is toggled on a message by the user.
         *
         * @param message the message for which the bookmark is toggled
         * @param user the user who toggled the bookmark
         */
        void onBookmarkToggled(Message message, User user);

        /**
         * Notifie que la stratégie d'affichage a changé. Cette méthode est
         * appelée lorsque l'utilisateur sélectionne une nouvelle stratégie
         * d'affichage dans l'interface.
         *
         * @param strategy la nouvelle stratégie d'affichage sélectionnée
         */
        void onDisplayStrategyChanged(DisplayStrategy strategy);

        /**
         * Gère la sélection de la stratégie d'affichage. Cette méthode est
         * appelée lorsque l'utilisateur choisit une stratégie d'affichage
         * spécifique, par exemple à partir d'un menu déroulant (ComboBox).
         *
         * @param strategy la stratégie d'affichage sélectionnée
         */
        void onStrategySelected(DisplayStrategy strategy);

        /**
         * Triggered quand un message est supprimé par l'utilisateur.
         *
         * @param message le message supprimé
         * @param user l'utilisateur qui a supprimé le message
         */
        void onMessageDeleted(Message message, User user);

        /**
         * Triggered when more messages need to be loaded for a user.
         *
         * @param user the user for whom more messages need to be loaded
         */
        void onLoadMoreMessages(User user);

        /**
         * Triggered when a translation is requested for a message.
         *
         * @param message the message to translate
         * @param user the user requesting the translation
         */
        void onTranslateRequested(Message message, User user);
    }

    /**
     * Sets the listener for view events.
     *
     * @param listener the listener to be set
     */
    @Override
    public void setViewListener(final ViewListener listener) {
        this.listener = listener;
    }

    /**
     * Initializes the stage with specified dimensions.
     *
     * @param stage the primary stage
     * @param width the width of the stage
     * @param height the height of the stage
     */
    public void initialize(final Stage stage, final int width, final int height) {
        stage.setTitle("Y Microblogging");
        final VBox root = new VBox(10);

        // Initialize ComboBox with display strategies
        strategyComboBox = new ComboBox<>();
        strategyComboBox.getItems().addAll(
                new ChronologicalStrategy(),
                new RecentRelevantStrategy(),
                new MostRelevantStrategy());

        // Set a listener for strategy selection
        strategyComboBox.setOnAction(e -> {
            DisplayStrategy selectedStrategy = strategyComboBox.getValue();
            if (listener != null && selectedStrategy != null) {
                listener.onStrategySelected(selectedStrategy);
            }
        });

        users = new HBox(10);
        root.getChildren().addAll(strategyComboBox, users); // Add ComboBox to root layout

        // Set the scene and display it
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates user panes for the list of users.
     *
     * @param userList the list of users
     */
    public void createUsersPanes(final List<User> userList) {
        if (users == null) {
            users = new HBox();
        }
        users.getChildren().clear(); // Clear previous user panes
        for (User u : userList) {
            ScrollPane pane = new ScrollPane();
            VBox userBox = new VBox();
            pane.setMinWidth(500);
            pane.setContent(userBox);
            users.getChildren().add(pane);

            VBox userMsgBox = new VBox();
            Label userID = new Label(u.getId());
            Pane textBox = createInputWidget(u);
            userBox.getChildren().addAll(userID, userMsgBox, textBox);

            enableContinuousScrolling(pane, u); // Pass the ScrollPane and user
        }
    }

    /**
     * Updates the messages for a specific user.
     *
     * @param user the user for whom messages are being updated
     * @param messagesData the messages and their corresponding data
     */
    @Override
    public void updateMessages(final User user,
            final LinkedHashMap<Message, MessageData> messagesData) {
        for (Node u : users.getChildren()) {
            VBox userBox = (VBox) ((ScrollPane) u).getContent();
            Label userID = (Label) userBox.getChildren().get(0);
            if (userID.getText().equals(user.getId())) {
                VBox userMsg = (VBox) userBox.getChildren().get(1);

                userMsg.getChildren().clear();
                for (Entry<Message, MessageData> e : messagesData.entrySet()) {
                    Message m = e.getKey();
                    MessageData d = e.getValue();
                    userMsg.getChildren().add(createMessageWidget(m, d, user));
                }
                break;
            }
        }
    }

    private static final String MSG_STYLE = "-fx-background-color: white; "
            + "-fx-border-color: black; -fx-border-width: 1;"
            + "-fx-border-radius: 10px;"
            + "-fx-background-radius: 10px;"
            + "-fx-padding: 8px; "
            + "-fx-margin: 5px; ";

    /**
     * Creates a widget for displaying a message.
     *
     * @param message the message to display
     * @param messageData the data of the message
     * @param user the user viewing the message
     * @return a VBox containing the message widget
     */
    public VBox createMessageWidget(
            final Message message,
            final MessageData messageData,
            final User user) {
        VBox msgBox = new VBox();
        msgBox.setStyle(MSG_STYLE);
        User author = message.getAuthor();
        if (author == null) {
            throw new IllegalStateException("Message author cannot be null");
        }
        // Bouton pour supprimer le message
        Button deleteButton = new Button("x");
        deleteButton.setOnAction(e -> {
            if (listener != null) {
                listener.onMessageDeleted(message, user);
            }
        });

        // Bouton pour bookmark
        String bookmarkText = messageData.isBookmarked() ? "⭐" : "Click to bookmark";
        Button bookButton = new Button(bookmarkText);
        bookButton.setOnAction(e -> {
            if (listener != null) {
                listener.onBookmarkToggled(message, user);
            }
        });

        // Bouton pour traduction
        Button translateButton = new Button("Traduire");
        translateButton.setOnAction(e -> {
            if (listener != null) {
                listener.onTranslateRequested(message, user);
            }
        });

        HBox buttonsBox = new HBox(deleteButton, bookButton, translateButton);
        msgBox.getChildren().add(buttonsBox);

        // Affichage du contenu du message
        msgBox.getChildren().add(new Label(message.getContent()));

        // Affichage de la traduction si disponible
        String translatedContent = message.getTranslatedContent(user);
        if (translatedContent != null && !translatedContent.isEmpty()) {
            Label translationLabel = new Label("Traduction: " + translatedContent);
            msgBox.getChildren().add(translationLabel);
        }

        // Affichage de la date
        Label dateLabel = new Label("Published on: " + message.getFormattedDate());
        dateLabel.setTextFill(Color.LIGHTGRAY);
        msgBox.getChildren().add(dateLabel);

        // Affichage du score
        Label score = new Label("Score: " + messageData.getScore());
        score.setTextFill(Color.DARKORANGE);
        msgBox.getChildren().add(score);

        // Affiche l'auteur du message
        Label authorLabel;

        if (message.getAuthor().equals(user)) {
            // Si l'utilisateur est l'auteur du message, affiche "Me" et retire le
            // soulignement
            authorLabel = new Label("Me");
            authorLabel.setStyle("-fx-text-fill: black;"); // Pas de soulignement ni de clic
        } else {
            // Sinon, affiche le nom de l'auteur et permet de s'abonner ou se désabonner
            authorLabel = new Label("Author: " + message.getAuthor().getId());
            authorLabel.setStyle("-fx-underline: true; -fx-text-fill: blue;");

            // Gestion de l'abonnement/désabonnement
            authorLabel.setOnMouseClicked(e -> {
                if (user.getSubscriptions().contains(message.getAuthor())) {
                    // Si déjà abonné, se désabonner
                    user.unsubscribe(message.getAuthor());
                    System.out.println("Unsubscribed from " + message.getAuthor().getId());
                } else {
                    // Sinon, s'abonner
                    user.subscribe(message.getAuthor());
                    System.out.println("Subscribed to " + message.getAuthor().getId());
                }
            });
        }
        msgBox.getChildren().add(authorLabel);

        return msgBox;
    }

    /**
     * Creates the input widget for message publishing.
     *
     * @param user the user who will publish the message
     * @return the Pane containing the input widget
     */
    private Pane createInputWidget(final User user) {
        final Pane input = new HBox();
        TextArea textArea = new TextArea();
        textArea.setMaxSize(200, 150);
        textArea.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && e.isControlDown()) {
                publish(textArea, user);
                textArea.clear();
            }
        });
        Button publishButton = new Button("Publish");
        publishButton.setOnAction(e -> {
            publish(textArea, user);
            textArea.clear();
        });
        input.getChildren().addAll(textArea, publishButton);
        return input;
    }

    /**
     * Returns the HBox containing the users pane.
     *
     * @return the HBox representing the users pane.
     */
    public HBox getUsersPane() {
        return users;
    }

    /**
     * Publishes a message.
     *
     * @param textArea the text area containing the message content
     * @param user the user publishing the message
     */
    public void publish(final TextArea textArea, final User user) {
        if (listener != null) {
            listener.onMessagePublished(textArea.getText(), user);
        }
    }

    /**
     * Notifies the view that the model has been updated.
     *
     * @param users the list of users in the model
     * @param messages the list of messages in the model
     */
    @Override
    public void onModelUpdated(final List<User> users, final List<Message> messages) {
        // When the model is updated, refresh the user panes and display messages
        // accordingly
        createUsersPanes(users);

        for (User user : users) {
            LinkedHashMap<Message, MessageData> messagesData = new LinkedHashMap<>();
            for (Message m : messages) {
                messagesData.put(m, m.getMessageData(user));
            }
            updateMessages(user, messagesData);
        }
    }

    /**
     * Initializes the view and registers it to the model.
     *
     * @param model the model instance
     */
    public void initializeModel(final IModel model) {
        model.registerListener(this); // Register this view with the model
    }

    /**
     * Enables continuous scrolling for the given ScrollPane. When the
     * ScrollPane is scrolled to the bottom, it notifies the listener to load
     * more messages for the specified user.
     *
     * @param scrollPane the ScrollPane to enable continuous scrolling on
     * @param user the user for whom more messages should be loaded
     */
    public void enableContinuousScrolling(final ScrollPane scrollPane, final User user) {
        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() == 1.0) { // When scrolled to the bottom
                if (listener != null) {
                    listener.onLoadMoreMessages(user); // Notify controller to load more messages
                }
            }
        });
    }

}

package fr.univ_lyon1.info.m1.microblog.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.univ_lyon1.info.m1.microblog.model.ApertiumApiClient;
import fr.univ_lyon1.info.m1.microblog.model.BookmarkScoring;
import fr.univ_lyon1.info.m1.microblog.model.ChronologicalStrategy;
import fr.univ_lyon1.info.m1.microblog.model.DisplayStrategy;
import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.ScoreStrategy;
import fr.univ_lyon1.info.m1.microblog.model.ScoreStrategyFactory;
import fr.univ_lyon1.info.m1.microblog.model.TextMessage;
import fr.univ_lyon1.info.m1.microblog.model.User;
import fr.univ_lyon1.info.m1.microblog.model.Y;
import fr.univ_lyon1.info.m1.microblog.view.JfxView;
import fr.univ_lyon1.info.m1.microblog.view.JfxView.ViewListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * The MainController class controls the interaction between the model and the
 * view.
 */
public class MainController {

    private final Y model;
    private final JfxView view1;
    private final JfxView view2;
    private final BookmarkScoring scoring;
    private DisplayStrategy currentDisplayStrategy; // The current display strategy

    // Add a score threshold constant or a field
    private static final int SCORE_THRESHOLD = 0; // Only show messages with scores > 0

    /**
     * Constructor to initialize the controller with the views and model.
     *
     * @param model the model instance
     * @param view1 the first view instance
     * @param view2 the second view instance
     */
    public MainController(final Y model, final JfxView view1, final JfxView view2) {
        this.model = model;
        this.view1 = view1;
        this.view2 = view2;
        this.scoring = new BookmarkScoring();

        // Default display strategy
        this.currentDisplayStrategy = new ChronologicalStrategy();

        // Set listeners for view1
        this.view1.setViewListener(new ViewListener() {
            @Override
            public void onMessagePublished(final String content, final User user) {
                publishMessage(content, user);
            }

            @Override
            public void onBookmarkToggled(final Message message, final User user) {
                toggleBookmark(message, user);
            }

            @Override
            public void onStrategySelected(final DisplayStrategy strategy) {
                setDisplayStrategy(strategy);
            }

            @Override
            public void onDisplayStrategyChanged(final DisplayStrategy strategy) {
                currentDisplayStrategy = strategy; // Change la stratégie actuelle
                updateViews(); // Met à jour toutes les vues
            }

            @Override
            public void onMessageDeleted(final Message message, final User user) {
                model.deleteMessage(message);
                updateViews(); // Met à jour les vues après suppression
            }

            @Override
            public void onLoadMoreMessages(final User user) {
                loadMoreMessages(user); // Load more messages for the user
            }

            @Override
            public void onTranslateRequested(final Message message, final User user) {
                translateMessage(message, user);
            }
        });

        // Set listeners for view2 (same logic as view1)
        this.view2.setViewListener(new ViewListener() {
            @Override
            public void onMessagePublished(final String content, final User user) {
                publishMessage(content, user);
            }

            @Override
            public void onBookmarkToggled(final Message message, final User user) {
                toggleBookmark(message, user);
            }

            @Override
            public void onStrategySelected(final DisplayStrategy strategy) {
                setDisplayStrategy(strategy);
            }

            @Override
            public void onDisplayStrategyChanged(final DisplayStrategy strategy) {
                currentDisplayStrategy = strategy; // Change la stratégie actuelle
                updateViews(); // Met à jour toutes les vues
            }

            @Override
            public void onMessageDeleted(final Message message, final User user) {
                model.deleteMessage(message);
                updateViews(); // Met à jour les vues après suppression
            }

            @Override
            public void onLoadMoreMessages(final User user) {
                loadMoreMessages(user); // Load more messages for the user
            }

            @Override
            public void onTranslateRequested(final Message message, final User user) {
                translateMessage(message, user);
            }
        });
    }


    // Method to handle publishing a message
    /**
     * Publishes a message with the given content and author.
     * 
     * @param content The content of the message to be published.
     * @param user The user who authored the message.
     */
    public void publishMessage(final String content, final User user) {
        // Utilisez TextMessage au lieu de Message
        TextMessage message = new TextMessage(content, user);
        message.setAuthor(user); // Définissez l'auteur du message
        model.addMessage(message);

        // Update all views for each user
        for (User u : model.getUsers()) {
            computeScores(u);
        }
        updateViews();
    }

    // Method to handle toggling a bookmark
    /**
     * Toggles the bookmark status of a given message for a specific user.
     * If the message is already bookmarked by the user, it will be unbookmarked.
     * If the message is not bookmarked by the user, it will be bookmarked.
     *
     * @param message the message whose bookmark status is to be toggled
     * @param user the user for whom the bookmark status is to be toggled
     */
    public void toggleBookmark(final Message message, final User user) {
        message.toggleBookmark(user);
        updateViews();
    }

    // Method to update both views based on the selected display strategy
    private void updateViews() {
        for (User user : model.getUsers()) {
            updateView(view1, user);
            updateView(view2, user);
        }
    }

    /**
     * Sets the current display strategy for the application and updates the views for all users.
     *
     * @param strategy the display strategy to be set
     */
    public void setDisplayStrategy(final DisplayStrategy strategy) {
        this.currentDisplayStrategy = strategy;
        for (User user : model.getUsers()) {
            updateView(view1, user);
            updateView(view2, user);
        }
    }

    private void updateView(final JfxView view, final User user) {
        // Compute scores before applying display strategy
        List<Message> messages = model.getMessages();
        Map<Message, MessageData> messagesData = new HashMap<>();

        // Retrieve the MessageData for the user and each message, then compute scores
        for (Message m : messages) {
            MessageData data = m.getMessageData(user);
            messagesData.put(m, data);
        }

        // Apply scoring strategies before filtering/sorting
        applyScoringStrategies(messagesData);

        // Filter and sort messages using the current display strategy
        LinkedHashMap<Message, MessageData> sortedMessagesData = currentDisplayStrategy
                .filterAndSortMessages(messagesData);

        // Update the view with the sorted and filtered messages
        view.updateMessages(user, sortedMessagesData);
    }

    // Method to apply all scoring strategies to messages data
    private void applyScoringStrategies(final Map<Message, MessageData> messagesData) {
        ScoreStrategy bookmarkScoring = ScoreStrategyFactory.getStrategy("bookmark");
        ScoreStrategy recentMessageBonusScoring = ScoreStrategyFactory.getStrategy("recent");
        ScoreStrategy lengthBasedScoring = ScoreStrategyFactory.getStrategy("length");

        // Apply each strategy to compute scores
        bookmarkScoring.computeScores(messagesData);
        recentMessageBonusScoring.computeScores(messagesData);
        lengthBasedScoring.computeScores(messagesData);
    }

    /**
     * Starts the application by initializing the model and both views.
     */

    public void startApp() {
        // Supprimez l'appel à model.createExampleMessages();
        // Les données sont désormais initialisées dans DataInitializer

        // Créez les panneaux d'utilisateurs dans les vues
        view1.createUsersPanes(model.getUsers());
        view2.createUsersPanes(model.getUsers());

        // Initialize views with the current display strategy
        updateViews();
    }

    /**
     * Computes the bookmark scores for a given user.
     *
     * @param user the user for whom the scores are computed
     */
    private void computeScores(final User user) {
        Map<Message, MessageData> messagesData = new HashMap<>();

        // Retrieve the MessageData for the user and each message
        for (Message m : model.getMessages()) {
            messagesData.put(m, m.getMessageData(user));
        }

        // Apply scoring strategies
        ScoreStrategy bookmarkScoring = ScoreStrategyFactory.getStrategy("bookmark");
        ScoreStrategy recentMessageBonusScoring = ScoreStrategyFactory.getStrategy("recent");
        ScoreStrategy lengthBasedScoring = ScoreStrategyFactory.getStrategy("length");

        bookmarkScoring.computeScores(messagesData);
        recentMessageBonusScoring.computeScores(messagesData);
        lengthBasedScoring.computeScores(messagesData);

        // Notify listeners after computing scores
        model.notifyListeners();
    }


    private static final int BATCH_SIZE = 10; 
    private Map<User, Integer> userMessageOffsets = new HashMap<>(); 
    /**
     * Loads more messages for the specified user. This method is typically
     * called when the user scrolls to the bottom of the message list to load
     * additional messages.
     *
     * @param user the user for whom more messages should be loaded
     */
    
     public void loadMoreMessages(final User user) {
        System.out.println("Loading more messages for user: " + user.getId());
        int offset = userMessageOffsets.getOrDefault(user, 0);
    
        // Fetch additional messages and add them
        List<Message> newBatch = model.fetchAdditionalMessages(user, BATCH_SIZE);
        if (newBatch.isEmpty()) {
            System.out.println("No more messages to load for user: " + user.getId());
            return;
        }
    
        // Update the user offset
        userMessageOffsets.put(user, offset + newBatch.size());
    
        // Refresh the views
        updateViews();
    }
    

    /**
     * Appends new messages to the view for a specific user.
     *
     * This method updates two views (view1 and view2) by adding new messages to the user's 
     * message box.
     * It iterates through the children of the users' pane in each view, finds the 
     * corresponding user box,
     * and appends the new messages to the user's message box.
     *
     * @param user The user whose messages are to be appended.
     * @param newMessagesData A LinkedHashMap containing the new messages and their 
     * associated data.
     */
    public void appendMessagesToView(final User user, 
        final LinkedHashMap<Message, MessageData> newMessagesData) {
        // Update view1
        for (Node u : view1.getUsersPane().getChildren()) { // Access the pane in view1
            VBox userBox = (VBox) ((ScrollPane) u).getContent();
            Label userID = (Label) userBox.getChildren().get(0);
            if (userID.getText().equals(user.getId())) {
                VBox userMsgBox = (VBox) userBox.getChildren().get(1);
    
                // Add new messages to the current box in view1
                for (Entry<Message, MessageData> entry : newMessagesData.entrySet()) {
                    userMsgBox.getChildren().add(
                        view1.createMessageWidget(entry.getKey(), entry.getValue(), user)
                    );
                }
                break;
            }
        }
    
        // Update view2
        for (Node u : view2.getUsersPane().getChildren()) { // Access the pane in view2
            VBox userBox = (VBox) ((ScrollPane) u).getContent();
            Label userID = (Label) userBox.getChildren().get(0);
            if (userID.getText().equals(user.getId())) {
                VBox userMsgBox = (VBox) userBox.getChildren().get(1);
    
                // Add new messages to the current box in view2
                for (Entry<Message, MessageData> entry : newMessagesData.entrySet()) {
                    userMsgBox.getChildren().add(
                        view2.createMessageWidget(entry.getKey(), entry.getValue(), user)
                    );
                }
                break;
            }
        }
    }
    

    /**
     * Translates a message from English to Spanish and then to French.
     *
     * @param message
     * @param user
     */
    public void translateMessage(final Message message, final User user) {
        try {
            // Étape 1 : Traduire de l'anglais vers l'espagnol
            String sourceLang1 = "eng";
            String targetLang1 = "spa";
            String translatedTextStep1
                    = ApertiumApiClient.translate(message.getContent(), 
                    sourceLang1, targetLang1);

            if (translatedTextStep1 != null) {
                // Étape 2 : Traduire de l'espagnol vers le français
                String sourceLang2 = "spa";
                String targetLang2 = "fra";
                String translatedTextStep2
                        = ApertiumApiClient.translate(
                            translatedTextStep1, sourceLang2, targetLang2);

                if (translatedTextStep2 != null) { // Si la traduction est réussie
                    message.setTranslatedContent(translatedTextStep2, user);
                    updateViews();
                } else {
                    System.out.println("La traduction de l'espagnol vers le français a échoué.");
                }
            } else {
                System.out.println("La traduction de l'anglais vers l'espagnol a échoué.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Affiche les erreurs liées à l'API de traduction
        }
    }
}

package fr.univ_lyon1.info.m1.microblog.model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Interface representing the model in the microblogging application.
 * This interface defines the operations for managing users and messages
 * in the system, as well as managing listeners that observe the model.
 */
public interface IModel {

    /**
     * Adds a new message to the model.
     *
     * @param message the message to be added
     */
    void addMessage(Message message);

    /**
     * Retrieves the list of all messages in the model.
     *
     * @return a list of {@code Message} objects
     */
    List<Message> getMessages();

    /**
     * Retrieves the list of all users in the model.
     *
     * @return a list of {@code User} objects
     */
    List<User> getUsers();

    /**
     * Retrieves the data of all messages in the model, including message content
     * and associated user-specific data.
     *
     * @return a {@code LinkedHashMap} containing messages as keys and
     *         their corresponding {@code MessageData} as values
     */
    LinkedHashMap<Message, MessageData> getMessagesData();

    /**
     * Creates a new user with the given ID and adds it to the model.
     *
     * @param id the unique identifier for the user
     * @return the created {@code User} object
     */
    User createUser(String id);

    /**
     * Creates example messages and users for demonstration purposes.
     */
    void createExampleMessages();

    /**
     * Registers a listener to observe changes in the model.
     *
     * @param listener the listener to be registered
     */
    void registerListener(IModelListener listener);

    /**
     * Notifies all registered listeners of changes in the model.
     */
    void notifyListeners();

    /**
     * Supprime un message du modèle.
     *
     * @param message le message à supprimer
     */
    void deleteMessage(Message message);
}

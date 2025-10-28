package fr.univ_lyon1.info.m1.microblog.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents the core model of the microblogging application. It manages users
 * and messages and notifies views when data changes.
 */
public class Y implements IModel {

    /**
     * List of users in the application.
     */
    private final List<User> users = new ArrayList<>();

    /**
     * List of messages in the application.
     */
    private final List<Message> messages = new ArrayList<>();

    /**
     * List of registered listeners (observers) of the model.
     */
    private final List<IModelListener> listeners = new ArrayList<>();

    @Override
    public User createUser(final String id) {
        User user = new User(id);
        users.add(user);
        notifyListeners(); // Notify listeners after creating a new user
        return user;
    }

    @Override
    public void addMessage(final Message message) {
        messages.add(message);
        notifyListeners(); // Notify listeners after adding a new message
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public LinkedHashMap<Message, MessageData> getMessagesData() {
        LinkedHashMap<Message, MessageData> data = new LinkedHashMap<>();
        for (Message message : messages) {
            data.put(message, new MessageData());
        }
        return data;
    }

    @Override
    public void createExampleMessages() {
        createUser("foo");
        createUser("bar");
        addMessage(new TextMessage("Hello, world!"));
        addMessage(new TextMessage("What is this message?"));
        addMessage(new TextMessage("Good bye, world!"));
        addMessage(new TextMessage("Hello, you!"));
        addMessage(new TextMessage("Hello hello, world world world."));

    }

    @Override
    public void registerListener(final IModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyListeners() {
        for (IModelListener listener : listeners) {
            listener.onModelUpdated(getUsers(), getMessages());
        }
    }

    @Override
    public void deleteMessage(final Message message) {
        messages.remove(message); // Suppression du message de la liste
        notifyListeners(); // Notifie les vues des changements
    }

    /**
     * Fetches additional messages for a user.
     *
     * @param user the user requesting more messages
     * @param batchSize the number of messages to fetch
     * @return a list of additional messages
     */
    public List<Message> fetchAdditionalMessages(final User user, final int batchSize) {
        List<Message> newMessages = new ArrayList<>();

        for (int i = 0; i < batchSize; i++) {
            Message newMessage = new Message("Additional message " + (messages.size() + 1), user);
            newMessages.add(newMessage);
            messages.add(newMessage); // Add to the internal list
        }
        notifyListeners(); // Ensure views are notified
        return newMessages;
    }

}

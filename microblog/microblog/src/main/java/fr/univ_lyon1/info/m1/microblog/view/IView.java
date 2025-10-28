package fr.univ_lyon1.info.m1.microblog.view;

import java.util.LinkedHashMap;
import java.util.List;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.User;

/**
 * Interface representing the view layer in the microblogging application.
 * The view interacts with the controller via listeners and is responsible
 * for displaying messages, users, and other UI components.
 */
public interface IView {

    /**
     * Sets the listener for view events such as message publishing and
     * bookmark toggling. This allows the controller to handle user actions.
     *
     * @param listener the listener to handle view events
     */
    void setViewListener(JfxView.ViewListener listener);

    /**
     * Updates the view to display the messages and their corresponding data
     * for a specific user. The messages are provided as a map, where the keys
     * are the messages and the values are their associated data.
     *
     * @param user the user whose messages are being updated in the view
     * @param messagesData a map of messages and their associated data
     */
    void updateMessages(User user, LinkedHashMap<Message, MessageData> messagesData);

    /**
     * Creates the user panes for the list of users in the view.
     * Each pane represents a user and displays their related information and messages.
     *
     * @param userList the list of users for whom panes are created
     */
    void createUsersPanes(List<User> userList);
}

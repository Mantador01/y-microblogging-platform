package fr.univ_lyon1.info.m1.microblog.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a message and its associated data, including content, 
 * creation date, and user-specific metadata.
 */
public class Message {
    private String content;
    private Date dateCreated;
    private User author;
    private Map<User, MessageData> messageDataMap;


    /**
     * Constructs a Message object with the specified content.
     *
     * @param content The content of the message.
     * @param author The author of the message.
     */
    public Message(final String content, final User author) {
        this.content = content;
        this.author = author;
        this.dateCreated = new Date();
        this.messageDataMap = new HashMap<>();
    }

    /**
     * Constructs a Message object with the specified content.
     *
     * @param content The content of the message.
     */
    public Message(final String content) {
        this.content = content;
        this.dateCreated = new Date();
        this.messageDataMap = new HashMap<>();
    }

    /**
     * Gets the content of the message.
     *
     * @return The content of the message.
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the creation date of the message.
     *
     * @return The date when the message was created.
     */
    public Date getDateCreated() {
        return dateCreated;
    }


    /**
     * Gets the formatted creation date of the message.
     *
     * @return The formatted date as a string in "dd/MM/yyyy HH:mm:ss" format.
     */
    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(dateCreated);
    }

    /**
     * Gets or creates the message data associated with the specified user.
     *
     * @param user The user for which the message data is retrieved or created.
     * @return The message data for the specified user.
     */
    public MessageData getMessageData(final User user) {
        return messageDataMap.computeIfAbsent(user, k -> new MessageData());
    }

    /**
     * Gets the author of the message.
     *
     * @return The author of the message.
     */
    public User getAuthor() {
        return author; 
    }

    /**
     * Sets the date when the message was created.
     *
     * @param dateCreated the date to set as the creation date of the message
     */
    public void setDateCreated(final Date dateCreated) {

        this.dateCreated = dateCreated;

    }

    /**
     * Toggles the bookmark status for the message for the specified user.
     *
     * @param user The user for whom the bookmark status is toggled.
     */
    public void toggleBookmark(final User user) {
        MessageData data = getMessageData(user);
        data.setBookmarked(!data.isBookmarked());
    }

    /**
     * Set translated content for the message for the specified user.
     * 
     * @param translatedContent The translated content to set.
     * @param user The user for whom the translated content is set.
     */
    public void setTranslatedContent(final String translatedContent, final User user) {
        MessageData data = getMessageData(user);
        data.setTranslatedContent(translatedContent);
    }

    /**
     * Gets the translated content for the message for the specified user.
     * 
     * @param user The user for whom the translated content is retrieved.
     * @return The translated content for the specified user.
     */
    public String getTranslatedContent(final User user) {
        MessageData data = getMessageData(user);
        return data.getTranslatedContent();
    }

    /**
     * Returns a string representation of the message, including the content
     * and formatted creation date.
     *
     * @return The string representation of the message.
     */
    @Override
    public String toString() {
        return content + " (Published on: " + getFormattedDate() + ")";
    }
}

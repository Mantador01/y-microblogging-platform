package fr.univ_lyon1.info.m1.microblog.model;

/**
 * Represents a text message in the microblogging application.
 * This class extends {@code Message} and can add custom behavior for text messages.
 */
public class TextMessage extends Message {

    private User author;

    /**
     * Constructs a new {@code TextMessage} with the specified content.
     *
     * @param content the content of the text message
     */
    public TextMessage(final String content, final User author) {
        super(content, author);
    }

    /**
     * Constructs a new {@code TextMessage} with the specified content.
     *
     * @param content the content of the text message
     */
    public TextMessage(final String content) {
        super(content);
    }

    /**
     * Sets the author of the text message.
     *
     * @param author the author of the message
     */
    public void setAuthor(final User author) {
        this.author = author;
    }

    /**
     * Returns the author of the text message.
     *
     * @return the author of the message
     */
    public User getAuthor() {
        return this.author;
    }

    /**
     * Returns the content of the text message.
     * This method currently returns the same content as the base {@code Message} class,
     * but can be overridden to add custom behavior for text messages.
     *
     * @return the content of the message
     */
    @Override
    public String getContent() {
        return super.getContent();  // You can add extra behavior if needed
    }
}

package fr.univ_lyon1.info.m1.microblog.model;

/**
 * Represents an image message in the microblogging application.
 * This class extends {@code Message} and adds custom behavior for image messages.
 */
public class ImageMessage extends Message {

    /**
     * Constructs a new {@code ImageMessage} with the specified content.
     *
     * @param content the content of the image message
     */
    public ImageMessage(final String content) {
        super(content);
    }

    /**
     * Returns the content of the image message, prepending "[Image]" to the original content.
     *
     * @return the content of the message with "[Image]" prepended
     */
    @Override
    public String getContent() {
        return "[Image] " + super.getContent();  // Add custom content for images
    }
}

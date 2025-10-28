package fr.univ_lyon1.info.m1.microblog.controller;

import fr.univ_lyon1.info.m1.microblog.model.ImageMessage;
import fr.univ_lyon1.info.m1.microblog.model.Message;

/**
 * Factory class to create instances of {@code ImageMessage}.
 * This class implements the {@code MessageFactory} and provides the
 * necessary implementation to create image messages.
 */
public class ImageMessageFactory extends MessageFactory {
    /**
     * Creates a new {@code ImageMessage} with the given content.
     *
     * @param content the content of the image message
     * @return a new {@code ImageMessage} instance
     */
    @Override
    public Message createMessage(final String content) {
        return new ImageMessage(content);
    }
}

package fr.univ_lyon1.info.m1.microblog.controller;

import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.TextMessage;

/**
 * Factory class to create instances of {@code TextMessage}.
 * This class implements the {@code MessageFactory} and provides the
 * necessary implementation to create text messages.
 */
public class TextMessageFactory extends MessageFactory {

    /**
     * Creates a new {@code TextMessage} with the given content.
     *
     * @param content the content of the text message
     * @return a new {@code TextMessage} instance
     */
    @Override
    public Message createMessage(final String content) {
        return new TextMessage(content);
    }
}

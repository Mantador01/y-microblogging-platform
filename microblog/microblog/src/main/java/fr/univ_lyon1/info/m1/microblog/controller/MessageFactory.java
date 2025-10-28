package fr.univ_lyon1.info.m1.microblog.controller;

import fr.univ_lyon1.info.m1.microblog.model.Message;

/**
 * Abstract factory class to create instances of {@code Message}.
 * This class defines a method that will be implemented by concrete
 * factories to create different types of messages.
 */
public abstract class MessageFactory {

    /**
     * Creates a new {@code Message} with the given content.
     * Concrete implementations of this method will determine the
     * type of message (e.g., text or image).
     *
     * @param content the content of the message to be created
     * @return a new {@code Message} instance
     */
    public abstract Message createMessage(String content);
}

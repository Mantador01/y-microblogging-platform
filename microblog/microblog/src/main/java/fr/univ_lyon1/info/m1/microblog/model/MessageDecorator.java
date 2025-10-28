package fr.univ_lyon1.info.m1.microblog.model;

/**
 * Abstract decorator class that provides a base for decorating {@code Message} objects.
 * This class allows additional behavior to be added to a message without modifying
 * the original message class. Concrete subclasses can extend this class to apply
 * specific decorations to messages.
 */
public abstract class MessageDecorator extends Message {

    /** The message being decorated. */
    private final Message decoratedMessage;

    /**
     * Constructs a new {@code MessageDecorator} that wraps the given message.
     *
     * @param decoratedMessage the message to be decorated
     */
    public MessageDecorator(final Message decoratedMessage) {
        super(decoratedMessage.getContent());
        this.decoratedMessage = decoratedMessage;
    }

    /**
     * Returns the content of the decorated message.
     *
     * @return the content of the decorated message
     */
    @Override
    public String getContent() {
        return decoratedMessage.getContent();
    }
}

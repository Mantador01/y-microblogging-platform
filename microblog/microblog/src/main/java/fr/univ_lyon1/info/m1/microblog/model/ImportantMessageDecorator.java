package fr.univ_lyon1.info.m1.microblog.model;

/**
 * A decorator class that adds the "IMPORTANT" label to a message's content.
 * This class extends {@code MessageDecorator} to modify the behavior of the
 * message by marking it as important.
 */
public class ImportantMessageDecorator extends MessageDecorator {

    /**
     * Constructs a new {@code ImportantMessageDecorator} that wraps the given message.
     *
     * @param decoratedMessage the message to be decorated as important
     */
    public ImportantMessageDecorator(final Message decoratedMessage) {
        super(decoratedMessage);
    }

    /**
     * Returns the content of the message, prepending "IMPORTANT: " to indicate its importance.
     *
     * @return the content of the message with "IMPORTANT: " prepended
     */
    @Override
    public String getContent() {
        return "IMPORTANT: " + super.getContent();
    }
}

package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents data associated with a message for a particular user, including
 * bookmark status, score, and associated words.
 */
public class MessageData {

    /**
     * Indicates whether the message is bookmarked.
     */
    private boolean isBookmarked = false;

    /**
     * The score of the message.
     */
    private int score = -1;

    /**
     * The set of words associated with the message.
     */
    private Set<String> words = new HashSet<>();

    private String translatedContent;
    private Date dateCreated;

    /**
     * Sets the date when the message was created.
     *
     * @param dateCreated the date to set as the creation date of the message
     */
    public void setDateCreated(final Date dateCreated) {

        this.dateCreated = dateCreated;

    }

    /**
     * Returns the set of words associated with the message.
     *
     * @return the set of words
     */
    public Set<String> getWords() {
        return words;
    }

    /**
     * Sets the set of words associated with the message.
     *
     * @param words the set of words to associate with the message
     */
    public void setWords(final Set<String> words) {
        this.words = words;
    }

    /**
     * Returns the score of the message.
     *
     * @return the score of the message
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the message.
     *
     * @param score the score to assign to the message
     */
    public void setScore(final int score) {
        this.score = score;
    }

    /**
     * Returns whether the message is bookmarked or not.
     *
     * @return true if the message is bookmarked, false otherwise
     */
    public boolean isBookmarked() {
        return isBookmarked;
    }

    /**
     * Sets whether the message is bookmarked or not.
     *
     * @param bookmarked true if the message is bookmarked, false otherwise
     */
    public void setBookmarked(final boolean bookmarked) {
        this.isBookmarked = bookmarked;
    }

    /**
     * Compares two {@code MessageData} instances, prioritizing bookmarked
     * messages first, and then sorting by score in descending order.
     *
     * @param rightData the {@code MessageData} to compare with
     * @return a negative integer if this object should be ordered before the
     * specified object, a positive integer if it should be ordered after, or 0
     * if they are equal
     */
    public int compare(final MessageData rightData) {
        // Prioritize bookmarked messages first
        if (this.isBookmarked() && !rightData.isBookmarked()) {
            return -1;
        } else if (!this.isBookmarked() && rightData.isBookmarked()) {
            return 1;
        }
        // Compare by score if both messages are bookmarked or not bookmarked
        return Integer.compare(rightData.getScore(), this.getScore());
    }

    /**
     * Sets the translated content for the message.
     *
     * @param translatedContent the translated content to set
     */
    public void setTranslatedContent(final String translatedContent) {
        this.translatedContent = translatedContent;
    }

    /**
     * Gets the translated content for the message.
     *
     * @return the translated content
     */
    public String getTranslatedContent() {
        return translatedContent;
    }
}

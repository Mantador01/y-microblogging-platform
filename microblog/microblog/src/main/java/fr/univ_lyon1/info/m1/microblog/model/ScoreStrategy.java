package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Map;

/**
 * Strategy interface for computing the score of messages.
 */
public interface ScoreStrategy {
    
    /**
     * Computes the score for all messages in the provided map.
     *
     * @param messagesData a map containing messages and their associated data
     */
    void computeScores(Map<Message, MessageData> messagesData);
}

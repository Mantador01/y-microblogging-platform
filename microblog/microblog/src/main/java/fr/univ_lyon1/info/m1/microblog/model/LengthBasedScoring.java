package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Map;

/**
 * Scoring strategy that adjusts score based on message length. +1 point for
 * messages shorter than 10 characters, -1 point for messages longer than 100
 * characters.
 */
public class LengthBasedScoring implements ScoreStrategy {
    @Override
    public void computeScores(final Map<Message, MessageData> messagesData) {
        for (Map.Entry<Message, MessageData> entry : messagesData.entrySet()) {
            Message message = entry.getKey();
            MessageData messageData = entry.getValue();

            int messageLength = message.getContent().length();

            // Start with the current score to ensure we build on it
            int currentScore = messageData.getScore();

            if (messageLength < 10) {
                currentScore += 1; // +1 for short messages
            } else if (messageLength > 100) {
                currentScore -= 1; // -1 for long messages
            }

            messageData.setScore(currentScore);

        }
    }
}


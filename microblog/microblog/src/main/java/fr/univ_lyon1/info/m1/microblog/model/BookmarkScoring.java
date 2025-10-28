package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Scoring of messages based on bookmarks.
 */
public class BookmarkScoring implements ScoreStrategy {

    @Override
    public void computeScores(final Map<Message, MessageData> messagesData) {
        Set<Message> messages = messagesData.keySet();
        Set<String> bookmarkedWords = new HashSet<>();

        // Collect words from bookmarked messages
        messages.forEach((Message m) -> {
            MessageData d = messagesData.get(m);
            String[] w = m.getContent().toLowerCase().split("[^\\p{Alpha}]+");
            Set<String> words = new HashSet<>(Arrays.asList(w));
            d.setWords(words);
            if (d.isBookmarked()) {
                bookmarkedWords.addAll(words);
            }
        });

        // Calculate the score of each message
        messages.forEach((Message m) -> {
            MessageData d = messagesData.get(m);
            int score = 0;
            for (String w : d.getWords()) {
                if (bookmarkedWords.contains(w)) {
                    score++;
                }
            }
            d.setScore(score);
        });
    }
}

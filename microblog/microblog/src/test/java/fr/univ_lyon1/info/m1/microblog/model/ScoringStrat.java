package fr.univ_lyon1.info.m1.microblog.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ScoringStrategiesTest {
    private Map<Message, MessageData> messagesData;
    private LengthBasedScoring lengthBasedScoring;
    private RecentMessageBonusScoring recentMessageBonusScoring;

    @BeforeEach
    void setUp() {
        messagesData = new HashMap<>(); // Always start with a clean map
        lengthBasedScoring = new LengthBasedScoring();
        recentMessageBonusScoring = new RecentMessageBonusScoring();
    }

    private void resetScores(final Map<Message, MessageData> messagesData) {
        for (MessageData data : messagesData.values()) {
            data.setScore(0);
        }
    }

    @Test
    void testLengthBasedScoring() {
        Message longMessage = new Message(
                "A very long message that exceeds one hundred characters"
                + "meant to trigger the penalty scoring since it is longer than 100 characters.");
        Message intermediateMessage = new Message("Intermediate length");
        Message shortMessage = new Message("Short");

        messagesData.put(longMessage, new MessageData());
        messagesData.put(intermediateMessage, new MessageData());
        messagesData.put(shortMessage, new MessageData());

        resetScores(messagesData); // Clear scores before testing
        lengthBasedScoring.computeScores(messagesData);

        assertThat(messagesData.get(longMessage).getScore(), is(-1)); // Penalty for long message
        assertThat(messagesData.get(intermediateMessage).getScore(), is(0)); // No penalty or bonus
        assertThat(messagesData.get(shortMessage).getScore(), is(1)); // Bonus for short message
    }

    @Test
    void testRecentMessageBonusScoring() {
        Message recentMessage1 = new Message("Recent message 1");
        Message recentMessage2 = new Message("Recent message 2");
        Message oldMessage = new Message("Old message");

        // Adjust dates for testing
        recentMessage1.getDateCreated().setTime(
                System.currentTimeMillis() - 5 * 60 * 60 * 1000); // 5 hours ago
        recentMessage2.getDateCreated().setTime(
                System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000); // 3 days ago
        oldMessage.getDateCreated().setTime(
                System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000); // 10 days ago

        messagesData.put(recentMessage1, new MessageData());
        messagesData.put(recentMessage2, new MessageData());
        messagesData.put(oldMessage, new MessageData());

        resetScores(messagesData); // Clear scores before testing
        recentMessageBonusScoring.computeScores(messagesData);

        assertThat(messagesData.get(
                recentMessage1).getScore(), is(2)); // 2 points: < 24 hours and < 7 days
        assertThat(messagesData.get(
                recentMessage2).getScore(), is(1)); // 1 point: < 7 days
        assertThat(messagesData.get(
                oldMessage).getScore(), is(0)); // 0 points: > 7 days
    }
}


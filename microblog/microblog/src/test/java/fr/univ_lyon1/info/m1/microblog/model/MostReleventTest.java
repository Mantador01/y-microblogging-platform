package fr.univ_lyon1.info.m1.microblog.model;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class MostReleventTest {

    @Test
    void testFilterAndSortMessages() {
        // Given
        MostRelevantStrategy strategy = new MostRelevantStrategy();
        Map<Message, MessageData> messagesData = new LinkedHashMap<>();

        // Create messages with varying scores and dates
        User user1 = new User("user1");
        User user2 = new User("user2");
        User user3 = new User("user3");

        Message msg1 = new Message("Bookmarked, High Score", user1); // Oldest
        Message msg2 = new Message("Non-Bookmarked, Low Score", user2); // Newest
        Message msg3 = new Message("Bookmarked, Medium Score", user3); // Middle

        // Set MessageData with scores and bookmark status
        MessageData data1 = msg1.getMessageData(null);
        data1.setScore(10);
        data1.setBookmarked(true);

        MessageData data2 = msg2.getMessageData(null);
        data2.setScore(2);
        data2.setBookmarked(false);

        MessageData data3 = msg3.getMessageData(null);
        data3.setScore(5);
        data3.setBookmarked(true);

        // Populate the messagesData map
        messagesData.put(msg1, data1);
        messagesData.put(msg2, data2);
        messagesData.put(msg3, data3);

        // When
        LinkedHashMap<Message, MessageData> result = strategy.filterAndSortMessages(messagesData);

        // Then
        assertEquals(3, result.size(), "All messages should be included after filtering");

        // Verify sorting: Bookmarked first, by score descending, then by date
        Message[] sortedMessages = result.keySet().toArray(new Message[0]);
        assertEquals(msg1, sortedMessages[0], "Highest score bookmarked message should come first");
        assertEquals(msg3, sortedMessages[1], "Medium score bookmarked message should come second");
        assertEquals(msg2, 
                sortedMessages[2], "Non-bookmarked, lowest score message should come last");
    }

    @Test
    void testToString() {
        // Given
        MostRelevantStrategy strategy = new MostRelevantStrategy();

        // When & Then
        assertEquals("Messages les plus pertinents", strategy.toString(), 
            "toString should return 'Messages les plus pertinents'");
    }
}

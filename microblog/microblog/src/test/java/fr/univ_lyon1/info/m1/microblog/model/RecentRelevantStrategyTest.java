package fr.univ_lyon1.info.m1.microblog.model;

import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecentRelevantStrategyTest {
    @Test
    void testFilterAndSortMessages() {
        // Given
        RecentRelevantStrategy strategy = new RecentRelevantStrategy();
        Map<Message, MessageData> messagesData = new LinkedHashMap<>();

        Message msg1 = new Message("High Score Recent");
        Message msg2 = new Message("Low Score Old");
        Message msg3 = new Message("Medium Score Recent");

        // Setting scores and creation dates
        MessageData data1 = msg1.getMessageData(null);
        data1.setScore(10);
        msg1.getDateCreated().setTime(System.currentTimeMillis() - 1000); // Recent (1 second ago)

        MessageData data2 = msg2.getMessageData(null);
        data2.setScore(-1); // Below threshold
        msg2.getDateCreated().setTime(System.currentTimeMillis() - 1000000000); // Old

        MessageData data3 = msg3.getMessageData(null);
        data3.setScore(5);
        msg3.getDateCreated().setTime(System.currentTimeMillis() - 2000); // Recent (2 seconds ago)

        messagesData.put(msg1, data1);
        messagesData.put(msg2, data2);
        messagesData.put(msg3, data3);

        // When
        LinkedHashMap<Message, MessageData> result = strategy.filterAndSortMessages(messagesData);

        // Then
        assertEquals(2, result.size(), "Messages below score threshold should be excluded");
        assertEquals(msg1, 
                result.keySet().iterator().next(),
                "Messages should be sorted by score and recency");
    }
}

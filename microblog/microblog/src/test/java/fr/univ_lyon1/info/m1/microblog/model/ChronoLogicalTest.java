package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ChronologicalStrategyTest {

    @Test
    void testFilterAndSortMessages() {
        // Arrange
        ChronologicalStrategy strategy = new ChronologicalStrategy();
        Map<Message, MessageData> messagesData = new LinkedHashMap<>();

        // Messages with explicit creation dates
        Message msg1 = new TextMessage("Oldest");
        msg1.setDateCreated(new Date(1000)); // Fixed timestamp
        Message msg2 = new TextMessage("Middle");
        msg2.setDateCreated(new Date(2000));
        Message msg3 = new TextMessage("Newest");
        msg3.setDateCreated(new Date(3000));

        // Add messages in random order
        messagesData.put(msg2, new MessageData()); // Middle first
        messagesData.put(msg3, new MessageData()); // Newest
        messagesData.put(msg1, new MessageData()); // Oldest last

        // Act
        LinkedHashMap<Message, MessageData> sortedMessages 
            = strategy.filterAndSortMessages(messagesData);

        // Assert: Validate the order of messages
        Message[] sortedArray = sortedMessages.keySet().toArray(new Message[0]);
        assertEquals(msg1, sortedArray[0], "Oldest message should be first");
        assertEquals(msg2, sortedArray[1], "Middle message should be second");
        assertEquals(msg3, sortedArray[2], "Newest message should be last");
    }

    @Test
    void testToString() {
        // Arrange
        ChronologicalStrategy strategy = new ChronologicalStrategy();

        // Act
        String result = strategy.toString();

        // Assert
        assertEquals("Tous les messages (Chronologique)", result,
                "toString should return the correct strategy name");
    }
}

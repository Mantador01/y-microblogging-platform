package fr.univ_lyon1.info.m1.microblog.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

class BookmarkScoringTest {
    @Test
    void testComputeScores() {
        BookmarkScoring scoring = new BookmarkScoring();
        Map<Message, MessageData> messagesData = new HashMap<>();

        Message message = new TextMessage("Hello");
        MessageData data = new MessageData();
        data.setBookmarked(true); 
        messagesData.put(message, data);

        scoring.computeScores(messagesData);

        assertThat(
                "Score should be positive for bookmarked message", 
                data.getScore(), 
                is(greaterThan(0))
        );
    }
}


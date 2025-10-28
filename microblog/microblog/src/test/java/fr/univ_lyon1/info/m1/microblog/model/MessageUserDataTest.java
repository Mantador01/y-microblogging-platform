package fr.univ_lyon1.info.m1.microblog.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univ_lyon1.info.m1.microblog.controller.ImageMessageFactory;
import fr.univ_lyon1.info.m1.microblog.controller.TextMessageFactory;

class MessageUserDataTest {
    private TextMessageFactory textFactory;
    private ImageMessageFactory imageFactory;
    private BookmarkScoring bookmarkScoring;

    @BeforeEach
    void setUp() {
        textFactory = new TextMessageFactory();
        imageFactory = new ImageMessageFactory();
        bookmarkScoring = new BookmarkScoring();
    }

    @Test
    void testMessageContent() {
        Message m = new Message("Some content");
        assertThat(m.getContent(), is("Some content"));
    }

    @Test
    void testSortMessages() {
        Map<Message, MessageData> msgs = new HashMap<>();
        Message m1 = new Message("Hello, world!");
        Message m2 = new Message("Hello, you!");
        Message m3 = new Message("What is this message?");
        add(msgs, m1);
        add(msgs, m2);
        add(msgs, m3);
        msgs.get(m1).setBookmarked(true);

        bookmarkScoring.computeScores(msgs);

        List<Message> sorted = msgs.entrySet()
            .stream()
            .sorted(Entry.comparingByValue(MessageData::compare))
            .map(Entry::getKey)
            .collect(Collectors.toList());

        assertThat(sorted, contains(m1, m2, m3));
    }

    @Test
    void testBookmarkInfluenceOnScoring() {
        Map<Message, MessageData> msgs = new HashMap<>();
        Message m1 = new Message("Hello, everyone!");
        Message m2 = new Message("Everyone loves Java.");
        Message m3 = new Message("Java is powerful.");

        add(msgs, m1);
        add(msgs, m2);
        add(msgs, m3);
        msgs.get(m1).setBookmarked(true);

        bookmarkScoring.computeScores(msgs);

        assertThat(msgs.get(m1).getScore(), is(2));
        assertThat(msgs.get(m2).getScore(), is(1));
        assertThat(msgs.get(m3).getScore(), is(0));
    }

    @Test
    void testTextMessageFactory() {
        Message textMessage = textFactory.createMessage("Hello, world!");
        assertThat(textMessage.getContent(), is("Hello, world!"));
    }

    @Test
    void testImageMessageFactory() {
        Message imageMessage = imageFactory.createMessage("An awesome image!");
        assertThat(imageMessage.getContent(), is("[Image] An awesome image!"));
    }

    @Test
    void testImportantMessageDecorator() {
        Message originalMessage = new Message("This is important");
        ImportantMessageDecorator importantMessage = new ImportantMessageDecorator(originalMessage);
        assertThat(importantMessage.getContent(), is("IMPORTANT: This is important"));
    }

    private void add(final Map<Message, MessageData> msgs, final Message m) {
        msgs.put(m, new MessageData());
    }
    
}

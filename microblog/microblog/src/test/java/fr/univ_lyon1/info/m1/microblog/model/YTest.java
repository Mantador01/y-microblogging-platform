package fr.univ_lyon1.info.m1.microblog.model;

import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class YTest {

    private Y model;

    @BeforeEach
    void setUp() {
        model = new Y();
    }

    @Test
    void testCreateUser() {
        String userId = "foo";
        User user = model.createUser(userId);

        List<User> users = model.getUsers();
        assertThat(users.size(), is(1));
        assertThat(users.get(0).getId(), is(userId));
        assertThat(user, is(users.get(0)));
    }

    @Test
    void testAddMessage() {
        Message message = new TextMessage("Hello, World!");

        model.addMessage(message);

        List<Message> messages = model.getMessages();
        assertThat(messages.size(), is(1));
        assertThat(messages.get(0).getContent(), is("Hello, World!"));
    }

    @Test
    void testDeleteMessage() {
        Message message = new TextMessage("Hello, World!");
        model.addMessage(message);

        model.deleteMessage(message);

        List<Message> messages = model.getMessages();
        assertThat(messages, is(empty()));
    }

    @Test
    void testCreateExampleMessages() {
        model.createExampleMessages();

        List<Message> messages = model.getMessages();
        assertThat(messages.size(), is(5));
        assertThat(messages.get(0).getContent(), is("Hello, world!"));
    }

    @Test
    void testFetchAdditionalMessages() {
        User user = model.createUser("foo");
        List<Message> newMessages = model.fetchAdditionalMessages(user, 3);

        assertThat(newMessages.size(), is(3));
        assertThat(newMessages.get(0).getContent(), is("Additional message 1"));
        assertThat(model.getMessages().size(), is(3));
    }

    @Test
    void testGetMessagesData() {
        User user = model.createUser("foo");
        Message message1 = new TextMessage("Message 1");
        Message message2 = new TextMessage("Message 2");
        model.addMessage(message1);
        model.addMessage(message2);

        LinkedHashMap<Message, MessageData> messagesData = model.getMessagesData();

        assertThat(messagesData.size(), is(2));
        assertThat(messagesData.containsKey(message1), is(true));
        assertThat(messagesData.containsKey(message2), is(true));
    }

    @Test
    void testRegisterListenerAndNotifyListeners() {
        IModelListener listener = mock(IModelListener.class);
        model.registerListener(listener);

        model.createUser("foo");
        model.addMessage(new TextMessage("Test message"));

        verify(listener, atLeastOnce()).onModelUpdated(anyList(), anyList());
    }

    @Test
    void testNotifyListeners() {
        IModelListener listener = mock(IModelListener.class);
        model.registerListener(listener);

        model.notifyListeners();

        verify(listener, times(1)).onModelUpdated(anyList(), anyList());
    }
}

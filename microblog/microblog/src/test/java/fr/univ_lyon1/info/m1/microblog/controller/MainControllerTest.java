package fr.univ_lyon1.info.m1.microblog.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import fr.univ_lyon1.info.m1.microblog.model.ChronologicalStrategy;
import fr.univ_lyon1.info.m1.microblog.model.DisplayStrategy;
import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.TextMessage;
import fr.univ_lyon1.info.m1.microblog.model.User;
import fr.univ_lyon1.info.m1.microblog.model.Y;
import fr.univ_lyon1.info.m1.microblog.view.JfxView;

/**
 * Test class for maincontroller.java.
 */
class MainControllerTest {

    private Y model;
    private JfxView view1;
    private JfxView view2;
    private MainController controller;
    private User user;
    private Message message;

    @BeforeEach
    void setUp() {
        model = mock(Y.class);
        view1 = mock(JfxView.class);
        view2 = mock(JfxView.class);
        controller = new MainController(model, view1, view2);
    }

    @Test
    void testStartApp() {
        // Arrange: Mock the users returned by the model
        User user1 = new User("user1");
        User user2 = new User("user2");
        List<User> users = Arrays.asList(user1, user2);
        when(model.getUsers()).thenReturn(users);

        // Act: Call the startApp() method
        controller.startApp();

        // Assert: Verify createUsersPanes is called with the correct user list
        verify(view1, times(1)).createUsersPanes(users);
        verify(view2, times(1)).createUsersPanes(users);

        // Assert: Verify that updateMessages is called indirectly through updateViews
        verify(view1, atLeastOnce()).updateMessages(eq(user1), any());
        verify(view2, atLeastOnce()).updateMessages(eq(user2), any());
    }


    @Test
    public void testPublishMessage() {
        when(model.getUsers()).thenReturn(Arrays.asList(user));
        controller.publishMessage("testContent", user);
        verify(model).addMessage(any(TextMessage.class));
        verify(model, times(1)).notifyListeners();
        verify(view1, times(1)).updateMessages(eq(user), any(LinkedHashMap.class));
        verify(view2, times(1)).updateMessages(eq(user), any(LinkedHashMap.class));
    }

    @Test
    void testToggleBookmark() {
        // Arrange
        model = new Y(); // Use a real instance of the model
        controller = new MainController(model, view1, view2);
        User user = model.createUser("testUser");
        Message message = new Message("Bookmark this", user);
        model.addMessage(message);

        // Act
        controller.toggleBookmark(message, user);

        // Assert
        assertTrue(message.getMessageData(user).isBookmarked(), "Message should be bookmarked");
        verify(view1, times(1)).updateMessages(eq(user), any(LinkedHashMap.class));
        verify(view2, times(1)).updateMessages(eq(user), any(LinkedHashMap.class));
    }

    @Test
    public void testSetDisplayStrategy() {
        DisplayStrategy strategy = new ChronologicalStrategy();
        when(model.getUsers()).thenReturn(Arrays.asList(user));
        controller.setDisplayStrategy(strategy);
        verify(view1, times(1)).updateMessages(eq(user), any(LinkedHashMap.class));
        verify(view2, times(1)).updateMessages(eq(user), any(LinkedHashMap.class));
    }

    @Test
    void testLoadMoreMessages() {
        // Arrange
        model = new Y(); // Use the real model
        controller = new MainController(model, view1, view2);
        User user = model.createUser("testUser");
        for (int i = 0; i < 5; i++) {
            model.addMessage(new Message("Message " + i, user));
        }

        // Act
        controller.loadMoreMessages(user);

        // Assert
        verify(view1, times(1)).updateMessages(eq(user), any(LinkedHashMap.class));
        verify(view2, times(1)).updateMessages(eq(user), any(LinkedHashMap.class));
    }

}

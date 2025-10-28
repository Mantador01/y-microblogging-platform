// package fr.univ_lyon1.info.m1.microblog.model;

// import java.util.Arrays;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import org.testfx.framework.junit5.ApplicationTest;

// import fr.univ_lyon1.info.m1.microblog.view.JfxView;
// import javafx.application.Platform;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.control.TextArea;

// class JfxViewTest extends ApplicationTest {

//     private JfxView view;
//     private JfxView.ViewListener listener;

//     @BeforeEach
//     public void setUp() {
//         // Initialize JavaFX Toolkit
//         new JfxView();
//         Platform.runLater(() -> {
//             view = new JfxView();
//             listener = mock(JfxView.ViewListener.class);
//             view.setViewListener(listener);
//         });
//     }

//     @Test
//     public void testCreateUsersPanes() {
//         Platform.runLater(() -> {
//             // Arrange
//             User user1 = new User("user1");
//             User user2 = new User("user2");

//             // Act
//             view.createUsersPanes(Arrays.asList(user1, user2));

//             // Assert
//             assertNotNull(view.getUsersPane());
//             assertEquals(
//                 2, view.getUsersPane().getChildren().size(),
//                  "Users pane should have 2 user nodes.");
//         });
//     }

//     @Test
//     public void testPublishMessage() {
//         Platform.runLater(() -> {
//             // Arrange
//             User user = new User("user1");
//             TextArea textArea = new TextArea("Test message");

//             // Act
//             view.publish(textArea, user);

//             // Assert
//             verify(listener, times(1)).onMessagePublished(eq("Test message"), eq(user));
//         });
//     }

//     @Test
//     public void testEnableContinuousScrolling() {
//         Platform.runLater(() -> {
//             // Arrange
//             User user = new User("user1");
//             ScrollPane scrollPane = new ScrollPane();

//             // Act
//             view.enableContinuousScrolling(scrollPane, user);
//             scrollPane.setVvalue(1.0); // Simulate scrolling to the bottom

//             // Assert
//             verify(listener, times(1)).onLoadMoreMessages(eq(user));
//         });
//     }

    

// }

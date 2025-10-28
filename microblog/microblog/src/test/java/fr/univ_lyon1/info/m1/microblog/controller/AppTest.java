package fr.univ_lyon1.info.m1.microblog.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.testfx.framework.junit5.ApplicationTest;

import fr.univ_lyon1.info.m1.microblog.model.DataInitializer;
import fr.univ_lyon1.info.m1.microblog.model.RedditAuth;
import fr.univ_lyon1.info.m1.microblog.model.RedditPosts;
import fr.univ_lyon1.info.m1.microblog.model.Y;
import fr.univ_lyon1.info.m1.microblog.view.JfxView;
import fr.univ_lyon1.info.m1.microblog.view.LoginWindow;
import javafx.stage.Stage;


@RunWith(MockitoJUnitRunner.class) 
class AppTest extends ApplicationTest {

        @Mock
        private LoginWindow loginWindow;

        @Mock
        private Y model;

        @Mock
        private DataInitializer dataInitializer;

        @Mock
        private RedditAuth redditAuth;

        @Mock
        private RedditPosts redditPosts;

        @Mock
        private JfxView view1;

        @Mock
        private JfxView view2;

        @Mock
        private MainController controller;

        @InjectMocks
        private App app;

        @Before
        public void setUp() throws Exception {
                MockitoAnnotations.initMocks(this);
        }

        @Test
        public void testStartAppWithAuthenticatedUser() throws Exception {
                Stage primaryStage = mock(Stage.class);
                when(loginWindow.isAuthenticated()).thenReturn(true);
                when(loginWindow.getUsername()).thenReturn("testUser");
                when(RedditAuth.authenticate()).thenReturn("testAccessToken");

                app.start(primaryStage);

                verify(loginWindow).display(primaryStage);
                verify(loginWindow).isAuthenticated();
                verify(loginWindow).getUsername();
                verify(dataInitializer).initializeData();
                verify(redditPosts).getPosts("testAccessToken", "programming", model);
                verify(view1).initialize(primaryStage, 600, 600);
                verify(view2).initialize(any(Stage.class), eq(600), eq(400));
                verify(controller).startApp();
        }

        @Test
        public void testStartAppWithUnauthenticatedUser() throws Exception {
                Stage primaryStage = mock(Stage.class);
                when(loginWindow.isAuthenticated()).thenReturn(false);

                app.start(primaryStage);

                verify(loginWindow).display(primaryStage);
                verify(loginWindow).isAuthenticated();
                verify(loginWindow, never()).getUsername();
                verify(dataInitializer, never()).initializeData();
                verify(redditPosts, never()).getPosts(anyString(), anyString(), any(Y.class));
                verify(view1, never()).initialize(any(Stage.class), anyInt(), anyInt());
                verify(view2, never()).initialize(any(Stage.class), anyInt(), anyInt());
                verify(controller, never()).startApp();
        }
}

package fr.univ_lyon1.info.m1.microblog.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

class RedditPostsTest {

    @Test
    public void testGetPostsSuccess() throws Exception {
        // Initialisation du serveur MockWebServer
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Réponse simulée
        mockWebServer.enqueue(new MockResponse()
            .setBody("{\"data\": {\"children\":"
            + "[{\"data\": {\"title\": \"Test Post\", \"author\": \"User1\"}}]}}")
            .setResponseCode(200));

        // Redirige la base URL vers MockWebServer
        RedditPosts.setBaseUrl(mockWebServer.url("/").toString());

        // Mock du modèle Y
        Y mockModel = mock(Y.class);
        when(mockModel.getUsers()).thenReturn(new java.util.ArrayList<>());

        // Appel de la méthode et vérification
        assertDoesNotThrow(() -> RedditPosts.getPosts(
            "fakeAccessToken", "testSubreddit", mockModel));

        // Vérification de l'ajout du message
        verify(mockModel, times(1)).addMessage(any(TextMessage.class));

        // Arrêt du serveur
        mockWebServer.shutdown();
    }
}

package fr.univ_lyon1.info.m1.microblog.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

class RedditAuthTest {

    @Test
    public void testAuthenticateSuccess() throws Exception {
        // Initialisation du serveur MockWebServer
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Réponse simulée
        mockWebServer.enqueue(new MockResponse()
            .setBody("{\"access_token\": \"fakeAccessToken\"}")
            .setResponseCode(200));

        // Redirige la base URL vers MockWebServer
        RedditAuth.setBaseUrl(mockWebServer.url("/").toString());

        // Appel de la méthode et vérification du résultat
        String accessToken = RedditAuth.authenticate();
        assertEquals("fakeAccessToken", accessToken);

        // Arrêt du serveur
        mockWebServer.shutdown();
    }
}

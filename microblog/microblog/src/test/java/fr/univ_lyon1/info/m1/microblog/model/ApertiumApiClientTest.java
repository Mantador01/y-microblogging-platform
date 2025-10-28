package fr.univ_lyon1.info.m1.microblog.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

class ApertiumApiClientTest {

    @Test
    public void testTranslateSuccess() throws Exception {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Configurer une réponse simulée
        mockWebServer.enqueue(new MockResponse()
                .setBody("{\"responseData\": {\"translatedText\": \"Bonjour le monde\"}}")
                .setResponseCode(200));

        // Configurer l'URL dynamique pour ApertiumApiClient
        ApertiumApiClient.setBaseUrl(mockWebServer.url("/").toString());

        // Appeler la méthode à tester
        String result = ApertiumApiClient.translate("Hello world", "en", "fr");

        // Vérification du résultat
        assertEquals("Bonjour le monde", result);

        mockWebServer.shutdown();
    }

    @Test
    public void testTranslateHttpError() throws Exception {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Configurer une réponse simulée avec une erreur HTTP
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error"));

        ApertiumApiClient.setBaseUrl(mockWebServer.url("/").toString());

        // Appeler la méthode et s'assurer qu'elle retourne null
        String result = ApertiumApiClient.translate("Hello world", "en", "fr");
        assertNull(result);

        mockWebServer.shutdown();
    }

    @Test
    public void testTranslateInvalidJson() throws Exception {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Configurer une réponse simulée avec un JSON mal formé
        mockWebServer.enqueue(new MockResponse()
                .setBody("{ invalid json }")
                .setResponseCode(200));

        ApertiumApiClient.setBaseUrl(mockWebServer.url("/").toString());

        // Appeler la méthode et s'assurer qu'elle retourne null
        String result = ApertiumApiClient.translate("Hello world", "en", "fr");
        assertNull(result);

        mockWebServer.shutdown();
    }

}

package fr.univ_lyon1.info.m1.microblog.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("foo");
    }

    @Test
    void testSubscribeAndUnsubscribe() {
        User bar = new User("bar");

        // Subscribe
        user.subscribe(bar);
        assertTrue(user.getSubscriptions().contains(bar), "Subscription should be added");

        // Unsubscribe
        user.unsubscribe(bar);
        assertFalse(user.getSubscriptions().contains(bar), "Subscription should be removed");
    }
}

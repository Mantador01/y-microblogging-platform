package fr.univ_lyon1.info.m1.microblog.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user of the application.
 */
public class User {

    /** Unique identifier for the user. */
    private String id;
    private Set<User> subscriptions = new HashSet<>();
    

    /**
     * Constructs a new {@code User} with the specified ID.
     *
     * @param id must be a unique identifier for the user
     */
    public User(final String id) {
        this.id = id;
        this.subscriptions = new HashSet<>();
    }

    /**
     * Returns the unique ID of the user.
     *
     * @return the ID of the user
     */
    public String getId() {
        return id;
    }

    /**
     * Subscribes to another user.
     *
     * @param user the user to subscribe to
     */
    public void subscribe(final User user) {
        subscriptions.add(user);
    }

    /**
     * Returns the list of users this user is subscribed to.
     *
     * @return the subscriptions
     */
    public Set<User> getSubscriptions() {
        return subscriptions;
    }

    /**
     * Sets a new unique ID for the user.
     *
     * @param id the new ID to set
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Unsubscribes from another user.
     *
     * @param user the user to unsubscribe from
     */
    public void unsubscribe(final User user) {
        subscriptions.remove(user);
    }
    

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (id == null) {
            return other.id == null;
        } else {
            return id.equals(other.id);
        }
    }
}

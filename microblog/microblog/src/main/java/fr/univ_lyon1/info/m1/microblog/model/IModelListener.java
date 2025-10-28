package fr.univ_lyon1.info.m1.microblog.model;

import java.util.List;

/**
 * Interface that views implement to listen to changes in the model.
 */
public interface IModelListener {
    /**
     * Called when the model is updated.
     *
     * @param users the updated list of users
     * @param messages the updated list of messages
     */
    void onModelUpdated(List<User> users, List<Message> messages);
}

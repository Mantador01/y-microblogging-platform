package fr.univ_lyon1.info.m1.microblog.model;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Scoring strategy that adds bonus points for recent messages.
 * +1 point if posted in the last 7 days, and an additional +1 point if posted in the last 24 hours.
 */
public class RecentMessageBonusScoring implements ScoreStrategy {
    @Override
    public void computeScores(final Map<Message, MessageData> messagesData) {
        Date now = new Date();

        for (Map.Entry<Message, MessageData> entry : messagesData.entrySet()) {
            Message message = entry.getKey();
            MessageData messageData = entry.getValue();

            Date publicationDate = message.getDateCreated();

            long differenceInMillis = now.getTime() - publicationDate.getTime();
            long daysAgo = TimeUnit.MILLISECONDS.toDays(differenceInMillis);
            long hoursAgo = TimeUnit.MILLISECONDS.toHours(differenceInMillis);

            int bonusScore = 0;
            if (daysAgo <= 7) {
                bonusScore++; // +1 if posted in the last 7 days
            }
            if (hoursAgo <= 24) {
                bonusScore++; // Additional +1 if posted in the last 24 hours
            }

            messageData.setScore(messageData.getScore() + bonusScore); // Apply bonus

        }
    }
}

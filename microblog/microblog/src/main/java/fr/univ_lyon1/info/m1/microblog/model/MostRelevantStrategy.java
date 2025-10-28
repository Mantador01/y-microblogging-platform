package fr.univ_lyon1.info.m1.microblog.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * La classe MostRelevantStrategy filtre et trie les messages pour afficher les plus pertinents
 * en fonction de leur score. Les messages sont filtrés selon un seuil de score défini
 * et triés par score décroissant. En cas d'égalité de score, les messages les plus récents
 * sont affichés en premier.
 */
public class MostRelevantStrategy implements DisplayStrategy {
    /** Seuil de score pour filtrer les messages pertinents. */
    private static final int SCORE_THRESHOLD = 0;

    /**
     * Filtre et trie les messages pour n'afficher que ceux ayant un score supérieur ou égal
     * au seuil défini, triés par score décroissant et date décroissante pour les ex-æquo.
     *
     * @param messagesData une map contenant les messages et leurs données associées
     * @return un LinkedHashMap des messages filtrés et triés
     */
    @Override
    public LinkedHashMap<Message, MessageData> filterAndSortMessages(
        final Map<Message, MessageData> messagesData) {
        // Séparer les messages bookmarkés des autres
        Map<Message, MessageData> bookmarkedMessages = messagesData.entrySet().stream()
            .filter(e -> e.getValue().isBookmarked())
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            ));

        // Filtrer et trier les messages non bookmarkés
        LinkedHashMap<Message, MessageData> 
            sortedNonBookmarkedMessages = messagesData.entrySet().stream()
            .filter(e -> e.getValue().getScore() >= SCORE_THRESHOLD && !e.getValue().isBookmarked())
            .sorted((e1, e2) -> {
                int scoreComparison = Integer.compare(
                    e2.getValue().getScore(), e1.getValue().getScore());
                if (scoreComparison != 0) {
                    return scoreComparison;
                }
                return e2.getKey().getDateCreated().compareTo(e1.getKey().getDateCreated());
            })
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));

        // Combiner les messages bookmarkés en premier
        LinkedHashMap<Message, MessageData> finalSortedMessages = new LinkedHashMap<>();

        // Ajouter les messages bookmarkés en premier
        bookmarkedMessages.entrySet().stream()
            .sorted((e1, e2) -> {
                int scoreComparison = Integer.compare(
                    e2.getValue().getScore(), e1.getValue().getScore());
                if (scoreComparison != 0) {
                    return scoreComparison;
                }
                return e2.getKey().getDateCreated().compareTo(e1.getKey().getDateCreated());
            })
            .forEachOrdered(e -> finalSortedMessages.put(e.getKey(), e.getValue()));

        // Ajouter les messages non bookmarkés
        finalSortedMessages.putAll(sortedNonBookmarkedMessages);

        return finalSortedMessages;
    }


    /**
     * Retourne le nom de la stratégie pour l'affichage dans l'interface utilisateur.
     *
     * @return le nom de la stratégie
     */
    @Override
    public String toString() {
        return "Messages les plus pertinents";
    }
}

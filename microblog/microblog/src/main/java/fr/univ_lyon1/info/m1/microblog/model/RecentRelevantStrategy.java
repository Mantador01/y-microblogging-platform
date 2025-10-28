package fr.univ_lyon1.info.m1.microblog.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * La classe RecentRelevantStrategy est une implémentation de l'interface DisplayStrategy
 * qui filtre et trie les messages pour afficher uniquement ceux qui sont récents et
 * pertinents. Les messages sont filtrés en fonction d'un seuil de score, puis triés
 * en fonction du score (ordre décroissant). En cas d'égalité de score, les messages
 * les plus récents sont affichés en premier.
 */
public class RecentRelevantStrategy implements DisplayStrategy {
    /** Seuil de score pour filtrer les messages pertinents. */
    private static final int SCORE_THRESHOLD = 0;

    /**
     * Filtre et trie les messages pour afficher uniquement ceux ayant un score supérieur
     * au seuil défini et en mettant en avant les messages récents.
     *
     * @param messagesData une map contenant les messages et leurs données associées
     * @return un LinkedHashMap des messages filtrés et triés par pertinence et date
     */
    @Override
    public LinkedHashMap<Message, MessageData> filterAndSortMessages(
        final Map<Message, MessageData> messagesData) {
        return messagesData.entrySet().stream()
            .filter(e -> e.getValue().getScore() > SCORE_THRESHOLD)
            .sorted((e1, e2) -> {
                // Tri par score en ordre décroissant
                int scoreComparison = Integer.compare(
                    e2.getValue().getScore(), e1.getValue().getScore());
                if (scoreComparison != 0) {
                    return scoreComparison;
                }
                // En cas d'égalité de score, tri par date en ordre décroissant
                return e2.getKey().getDateCreated().compareTo(e1.getKey().getDateCreated());
            })
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    /**
     * Retourne une chaîne de caractères décrivant la stratégie, qui sera utilisée
     * dans l'interface utilisateur (par exemple, dans un ComboBox).
     *
     * @return le nom de la stratégie sous forme de chaîne de caractères
     */
    @Override
    public String toString() {
        return "Relevant Recent Messages";
    }
}

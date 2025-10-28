package fr.univ_lyon1.info.m1.microblog.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Stratégie d'affichage pour afficher tous les messages dans l'ordre
 * chronologique.
 */
public class ChronologicalStrategy implements DisplayStrategy {

    /**
     * Trie les messages dans l'ordre chronologique basé sur leur date de création.
     *
     * @param messagesData Un map contenant les messages et leurs données associées.
     * @return Une map triée avec les messages dans l'ordre chronologique.
     */
    @Override
    public LinkedHashMap<Message, MessageData> filterAndSortMessages(
           final Map<Message, MessageData> messagesData) {
        return messagesData.entrySet().stream()
                .sorted((e1, e2) -> 
                e1.getKey().getDateCreated().compareTo(e2.getKey().getDateCreated()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

    /**
     * Retourne le nom de la stratégie d'affichage.
     *
     * @return Le nom de la stratégie.
     */
    @Override
    public String toString() {
        return "Tous les messages (Chronologique)";
    }
}

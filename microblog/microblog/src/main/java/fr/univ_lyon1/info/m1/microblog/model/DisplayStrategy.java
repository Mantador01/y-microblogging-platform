package fr.univ_lyon1.info.m1.microblog.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * L'interface DisplayStrategy définit une stratégie pour filtrer et trier 
 * l'affichage des messages dans l'application de microblogging.
 * Chaque implémentation de cette interface peut fournir une logique spécifique
 * pour décider quels messages afficher et dans quel ordre.
 */
public interface DisplayStrategy {

    /**
     * Filtre et trie les messages selon la stratégie définie.
     *
     * @param messagesData une map contenant les messages et leurs données associées
     * @return un LinkedHashMap contenant les messages filtrés et triés
     */
    LinkedHashMap<Message, MessageData> filterAndSortMessages(
        Map<Message, MessageData> messagesData);

    /**
     * Fournit le nom de la stratégie pour l'affichage (par exemple dans un ComboBox).
     * Cette méthode doit être implémentée pour retourner une chaîne de caractères 
     * décrivant la stratégie de manière conviviale.
     *
     * @return une chaîne de caractères représentant le nom de la stratégie
     */
    String toString();
}

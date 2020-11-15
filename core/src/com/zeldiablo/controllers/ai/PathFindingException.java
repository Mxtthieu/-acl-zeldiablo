package com.zeldiablo.controllers.ai;

public class PathFindingException extends Exception {

    /**
     * Produit une exception accompagnée d'un message
     * @param s Cause de l'exception
     */
    public PathFindingException(String s) {
        super(s);
    }
}

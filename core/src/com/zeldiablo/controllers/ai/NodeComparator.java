package com.zeldiablo.controllers.ai;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    /**
     * Compare un noeud n au noeud courant.
     * @param n1 Premier noeud
     * @param n2 Deuxième noeud
     * @return  1 si l'heuristique de n1 est inférieur à n2
     *          0 si les deux heuristiques sont égaux
     *          -1 si il est supérieur
     */
    @Override
    public int compare(Node n1, Node n2) {
        if (n1.f < n2.f)
            return 1;
        else if (n1.f == n2.f)
            return 0;
        else
            return -1;
    }

}

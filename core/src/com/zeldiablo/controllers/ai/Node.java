package com.zeldiablo.controllers.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe réprésente un noeud qui servira au calcule des chemins de coût minimal.
 * @author Sousa Ribeiro Pedro
 */
public class Node {

    public int x, y;
    public double cost, heuristic;
    private List<Node> neighbours;

    public Node(int x, int y, float cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.heuristic = 0;
        this.neighbours = new ArrayList<>();
    }

    /**
     * Compare un noeud n au noeud courant.
     * @param n Node à comparer
     * @return  1 si l'heuristique du noeud n est inférieur
     *          0 si les deux heuristiques sont égaux
     *          -1 si il est supérieur
     */
    public int compareNode(Node n) {
        if (this.heuristic < n.heuristic)
            return 1;
        else if (this.heuristic == n.heuristic)
            return 0;
        else
            return -1;
    }

    /**
     * Ajoute un voisin au noeud courant
     * @param n Node à ajouter
     */
    public void addNeighbour(Node n) {
        this.neighbours.add(n);
    }

    /**
     * Retourne les voisins qui ont été ajouté au noeud jusqu'à maintenant.
     * @return
     */
    public List<Node> getNeighbours() {
        return this.neighbours;
    }

    public double distanceWith(Node n) {
        return Math.sqrt((x - n.x)*(x - n.x) + (y - n.y)*(y - n.y));
    }
}

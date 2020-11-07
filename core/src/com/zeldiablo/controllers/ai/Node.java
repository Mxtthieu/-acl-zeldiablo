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
    public Node previous;
    private List<Node> neighbours;

    public Node(int x, int y, float cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.heuristic = 0;
        this.neighbours = new ArrayList<>();
    }

    /**
     * Ajoute un voisin au noeud courant
     * @param n Node à ajouter
     */
    public void addNeighbour(Node n) {
        if (n != null)
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

package com.zeldiablo.controllers.ai;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Cette classe permet de calculer un chemin de coût minimal entre deux noeuds avec l'algorithme A*.
 * @author Sousa Ribeiro Pedro
 */
public class PathFinding {

    private Node start;
    private Node goal;
    private Node[][] grid;

    private final int MAX_WIDTH;
    private final int MAX_HEIGHT;

    public PathFinding(boolean[][] g, int startX, int startY, int goalX, int goalY) {
        this.MAX_WIDTH = g[0].length;
        this.MAX_HEIGHT = g.length;

        this.grid = new Node[this.MAX_HEIGHT][this.MAX_WIDTH];
        this.start = new Node(startX, startY, 0);
        this.goal = new Node(goalX, goalY, 0);
        this.goal.heuristic = this.start.distanceWith(this.goal);


        // --- Construction de la grille de noeuds
        // Pour cela, on utilise le tableau de booléen pour déterminer si un noeud doit être construit ou non
        for (int j = 0; j < this.MAX_HEIGHT; j++) {
            for (int i = 0; i < this.MAX_WIDTH; i++) {
                if (g[j][i])
                    this.grid[j][i] = new Node(i, j, 0);
                else
                    this.grid[j][i] = null;
            }
        }

        // --- Ajout des voisins à chaque case
        for (Node[] line : this.grid) {
            for (Node node : line) {
                if (node != null) {

                }
            }
        }
    }

    private void getNeighbourOf(int x, int y) {
        Node node = this.grid[y][x];

        // Voisin droit
        if (x < MAX_WIDTH-1)
            node.addNeighbour(this.grid[y][x+1]);

        // Voisin gauche
        if (x > 0)
            node.addNeighbour(this.grid[y][x-1]);

        // Voisin haut
        if (y < MAX_HEIGHT - 1)
            node.addNeighbour(this.grid[y+1][x]);

        // Voisin bas
        if (y > 0)
            node.addNeighbour(this.grid[y-1][x]);

        // Diagonal bas-gauche
        if
    }

    /**
     * Redéfinie le noeud de départ du chemin
     * @param x coordonnées sur X
     * @param y coordonnées sur Y
     */
    public void setStart(int x, int y) {
        this.start = new Node(x, y, 0);
    }

    /**
     * Redéfinie le noeud d'arrivée du chemin
     * @param x coordonnées sur X
     * @param y coordonnées sur Y
     */
    public void setGoal(int x, int y) {
        this.goal = new Node(x, y, 0);
        this.goal.heuristic = this.start.distanceWith(this.goal);
    }

    /**
     * Calcule le chemin de coût minimal entre le point de départ et d'arrivée en utilisant l'algorithme A*
     * @return List des noeuds qui constituent le chemin.
     */
    public List<Node> calculatePath() {
        List<Node> closedList = new Stack<>();
        List<Node> openList = new Stack<>();
        List<Node> path = new ArrayList<>();

        openList.add(this.start);
        Node u;
        while (!openList.isEmpty()) {
            u = openList.remove(openList.size());
            if (u.x == this.goal.x && u.y == this.goal.y) {
                
            }
        }
        return null;
    }
}

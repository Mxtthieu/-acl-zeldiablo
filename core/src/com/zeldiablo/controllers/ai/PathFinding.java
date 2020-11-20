package com.zeldiablo.controllers.ai;

import java.util.*;

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

    // --- Initialisation des listes permettant le calcule du chemin
    private List<Node> closedList   = new ArrayList<>();
    private List<Node> openList     = new ArrayList<>();
    private List<Node> path         = new ArrayList<>();

    public PathFinding(boolean[][] g, int startX, int startY, int goalX, int goalY) throws PathFindingException {
        this.MAX_WIDTH = g[0].length;
        this.MAX_HEIGHT = g.length;

        this.grid = new Node[this.MAX_HEIGHT][this.MAX_WIDTH];

        // --- Construction de la grille de noeuds
        // Pour cela, on utilise le tableau de booléen pour déterminer si un noeud doit être construit ou non
        int trueCount = 0, falseCount = 0;
        for (int j = 0; j < this.MAX_HEIGHT; j++) {
            for (int i = 0; i < this.MAX_WIDTH; i++) {
                if (g[j][i]) {
                    this.grid[j][i] = new Node(i, j);
                    trueCount++;
                }
                else {
                    this.grid[j][i] = null;
                    falseCount++;
                }
            }
        }

        try {
            this.start = this.grid[startY][startX];
            this.goal = this.grid[goalY][goalX];
        } catch (ArrayIndexOutOfBoundsException a) {
            throw new PathFindingException(String.format("Start (%d, %d) or goal (%d, %d) node out of array [%d][%d].",
                    startX, startY, goalX, goalY, MAX_HEIGHT, MAX_WIDTH));
        }

        // --- Ajout des voisins à chaque case
        for (Node[] line : this.grid) {
            for (Node node : line) {
                if (node != null) {
                    this.setNeighbourOf(node);
                }
            }
        }

        this.calculatePath();
    }

    /**
     * Ajoute à un noeud, tous les voisins, dans la grille, de ce noeud.
     * @param node Node dont les voisins sont à ajouter
     */
    private void setNeighbourOf(Node node) {
        int x = node.x;
        int y = node.y;

        // Voisin droit
        if (x < MAX_WIDTH-1 && this.grid[y][x+1] != null)
            node.addNeighbour(this.grid[y][x+1]);

        // Voisin gauche
        if (x > 0 && this.grid[y][x-1] != null)
            node.addNeighbour(this.grid[y][x-1]);

        // Voisin haut
        if (y < MAX_HEIGHT - 1 && this.grid[y+1][x] != null)
            node.addNeighbour(this.grid[y+1][x]);

        // Voisin bas
        if (y > 0 && this.grid[y-1][x] != null)
            node.addNeighbour(this.grid[y-1][x]);

        // Diagonal bas-gauche
        if (x > 0 && y > 0 && this.grid[y-1][x-1] != null)
            node.addNeighbour(this.grid[y-1][x-1]);

        // Diagonal bas-droite
        if (x < MAX_WIDTH-1 && y > 0 && this.grid[y-1][x+1] != null)
            node.addNeighbour(this.grid[y-1][x+1]);

        //Diagonal haut-gauche
        if (x > 0 && y < MAX_HEIGHT-1 && this.grid[y+1][x-1] != null)
            node.addNeighbour(this.grid[y+1][x-1]);

        //Diagonal haut-droite
        if (x < MAX_WIDTH-1 && y < MAX_HEIGHT-1 && this.grid[y+1][x+1] != null)
            node.addNeighbour(this.grid[y+1][x+1]);
    }

    /**
     * Redéfinie le noeud de départ du chemin
     * @param x coordonnées sur X
     * @param y coordonnées sur Y
     */
    public void setStart(int x, int y) {
        if (x < MAX_WIDTH && x >= 0 && y < MAX_HEIGHT && y >= 0)
            this.start = this.grid[y][x];
    }

    /**
     * Redéfinie le noeud d'arrivée du chemin
     * @param x coordonnées sur X
     * @param y coordonnées sur Y
     */
    public void setGoal(int x, int y) {
        if (x < MAX_WIDTH && x >= 0 && y < MAX_HEIGHT && y >= 0)
            this.goal = this.grid[y][x];
    }

    /**
     * Ouvre le noeud en l'ajoutant à la liste des noeuds à exploré
     * @param n Node à ouvrir
     */
    private void openNode(Node n) {
        if (!this.closedList.contains(n))
            this.openList.add(n);
    }

    /**
     * Ferme le noeud en l'ajoutant à la liste des noeuds déja exploré
     * @param n Node à fermer
     */
    private void closeNode(Node n) {
        if (!this.closedList.contains(n)) {
            this.openList.remove(n);
            this.closedList.add(n);
        }
    }

    /**
     * Calcule le chemin de coût minimal entre le point de départ et d'arrivée en utilisant l'algorithme A*
     * @return List des noeuds qui constituent le chemin.
     */
    public void calculatePath() {
        this.openNode(this.start);
        boolean stop = false;

        while (!this.openList.isEmpty() && !stop) {
            int min = 0;
            for (int i = 0; i < this.openList.size(); i++) {
                if (this.openList.get(i).f < this.openList.get(min).f)
                    min = i;
            }

            Node current = this.openList.get(min);
            if (current.equals(this.goal)) {
                Node tmp = current;
                this.path.add(tmp);
                while (tmp.previous != null) {
                    tmp = tmp.previous;
                    this.path.add(tmp);
                }
                stop = true;
            } else {
                this.closeNode(current);
                List<Node> neighbours = current.getNeighbours();

                for (Node neighbour : neighbours) {
                    if (!this.closedList.contains(neighbour)) {
                        double tmpG = current.g + neighbour.g;
                        boolean newPath = false;

                        if (this.openList.contains(neighbour)) {
                            if (tmpG < neighbour.g) {
                                neighbour.g = tmpG;
                                newPath = true;
                            }
                        } else {
                            neighbour.g = tmpG;
                            newPath = true;
                            openNode(neighbour);
                        }

                        if (newPath) {
                            neighbour.h = neighbour.distanceWith(this.goal);
                            neighbour.f = neighbour.g + neighbour.h;
                            neighbour.previous = current;
                        }
                    }
                }
            }
        }

        this.path = buildPath();
        this.openList.clear();
        this.closedList.clear();
    }

    /**
     * Reconstitue le chemin à partir du noeud final
     * @return list de l'ensemble des noeuds du chemin
     */
    private List<Node> buildPath() {
        List<Node> res = new ArrayList<>();
        Node n = this.goal;

        while (n != null) {
            res.add(n);
            n = n.previous;
        }

        return res;
    }

    /**
     * Retourne le chemin de coût minimal calculé
     * @return Liste des noeuds du chemin
     */
    public List<Node> getPath() {
        return this.path;
    }

    @Override
    public String toString() {
        String str = "Chemin:";
        for (Node n : this.path)
            str += "\n(" + n.x + ", " + n.y + ")";
        return str;
    }
}

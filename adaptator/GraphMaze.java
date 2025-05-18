package adaptator;

import maze.Maze;
import graph.Graph;

import java.util.ArrayList;
import java.util.List;

// Adaptateur pour utiliser un Maze comme un Graph
public class GraphMaze<T> implements Graph<T> {
    private final Maze<T> maze; // Le labyrinthe à adapter

    // Constructeur : on passe un labyrinthe
    public GraphMaze(Maze<T> maze) {
        this.maze = maze;
    }

    // Retourne les arcs depuis un sommet donné
    @Override
    public List<Arc<T>> getSucc(T vertex) {
        List<Arc<T>> successeur = new ArrayList<>();

        // Pour chaque voisin du sommet
        for (T voisin : maze.neighbours(vertex)) {
            // Si le chemin entre le sommet et le voisin est ouvert
            if (maze.isOpen(vertex, voisin)) {
                // Ajouter un arc de poids 1 vers ce voisin
                successeur.add(new Arc<>(1, voisin));
            }
        }

        return successeur;
    }
}

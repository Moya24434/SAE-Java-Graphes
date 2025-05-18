package adaptator;

import maze.Maze;
import graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class GraphMaze<T> implements Graph<T> {
    private final Maze<T> maze;

    public GraphMaze(Maze<T> maze) {
        this.maze = maze;
    }

    @Override
    public List<Arc<T>> getSucc(T vertex) {
        List<Arc<T>> successeur = new ArrayList<>();
        for(T voisin  : maze.neighbours(vertex) ) {
            if(maze.isOpen(vertex, voisin)) {
                successeur.add(new Arc<>(1, voisin));
            }
        }
        return successeur;
    }
}

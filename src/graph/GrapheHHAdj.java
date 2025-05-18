package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrapheHHAdj implements VarGraph {

    // Représentation du graphe : un sommet -> liste de ses arcs sortants
    private Map<String, List<IGraph.Arc<String>>> adjacence;

    public GrapheHHAdj() {
        this.adjacence = new HashMap<>();
    }

    @Override
    public List<IGraph.Arc<String>> getSucc(String s) {
        return adjacence.getOrDefault(s,new ArrayList<>());
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!adjacence.containsKey(noeud)) {
            adjacence.put(noeud, new ArrayList<IGraph.Arc<String>>());
        }
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        //si les sommmets n'existent pas on les créer
        if (!adjacence.containsKey(source))
            ajouterSommet(source);
        if (!adjacence.containsKey(destination))
            ajouterSommet(destination);
        adjacence.get(source).add(new IGraph.Arc<>(valeur,destination));

    }

}

package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation d'un graphe par liste d'adjacence avec HashMap-HashMap.
 * HH pour "HashMap-HashMap"
 */
public class GrapheHHAdj implements VarGraph {

    // Structure pour stocker le graphe: map de sommet vers la liste de ses arcs sortants
    private Map<String, Map<String, Integer>> adjacencyList;

    /**
     * Constructeur initialisant un graphe vide
     */
    public GrapheHHAdj() {
        adjacencyList = new HashMap<>();
    }

    @Override
    public void ajouterSommet(String noeud) {
        if (!adjacencyList.containsKey(noeud)) {
            adjacencyList.put(noeud, new HashMap<>());
        }
    }

    @Override
    public void ajouterArc(String source, String destination, Integer valeur) {
        ajouterSommet(source);
        ajouterSommet(destination);
        if (adjacencyList.get(source).containsKey(destination)) {
            throw new IllegalArgumentException("L'arc " + source + " -> " + destination + " existe déjà");
        }

        adjacencyList.get(source).put(destination, valeur);
    }

    @Override
    public List<Arc<String>> getSucc(String s) {
        List<Arc<String>> successeurs = new ArrayList<>();

        if (!adjacencyList.containsKey(s)) {
            return successeurs;
        }

        for (Map.Entry<String, Integer> entry : adjacencyList.get(s).entrySet()) {
            successeurs.add(new Arc<>(entry.getValue(), entry.getKey()));
        }

        return successeurs;
    }

    /**
     * Méthode pour retirer un sommet du graphe
     */
    public void oterSommet(String noeud) {
        if (!adjacencyList.containsKey(noeud)) {
            return;
        }

        // Retire le sommet
        adjacencyList.remove(noeud);

        for (Map<String, Integer> arcs : adjacencyList.values()) {
            arcs.remove(noeud);
        }
    }

    /**
     * Méthode pour retirer un arc du graphe (non requise par l'interface mais utile)
     */
    public void oterArc(String source, String destination) {
        if (!adjacencyList.containsKey(source) || !adjacencyList.get(source).containsKey(destination)) {
            throw new IllegalArgumentException("L'arc " + source + " -> " + destination + " n'existe pas");
        }
        adjacencyList.get(source).remove(destination);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Map<String, Integer>> entry : adjacencyList.entrySet()) {
            String sommet = entry.getKey();

            if (entry.getValue().isEmpty()) {
                sb.append(sommet).append(":, ");
            } else {
                for (Map.Entry<String, Integer> arc : entry.getValue().entrySet()) {
                    sb.append(sommet).append("-").append(arc.getKey())
                            .append("(").append(arc.getValue()).append("), ");
                }
            }
        }

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }
}

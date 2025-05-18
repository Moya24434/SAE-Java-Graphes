package dijkstra;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import graph.IGraph;
import graph.IShortestPath;

/**
 * Implémentation de l'algorithme de Dijkstra pour trouver les plus courts chemins
 * depuis un sommet source vers tous les autres sommets dans un graphe.
 *
 * @param <T> Type des sommets du graphe
 */
public class Dijkstra<T> implements ShortestPath<T> {

    /**
     * Calcule les plus courts chemins depuis un sommet source vers tous les autres sommets
     * accessibles dans le graphe.
     *
     * @param g Le graphe sur lequel l'algorithme est appliqué
     * @param src Le sommet source à partir duquel les plus courts chemins sont calculés
     * @param animator L'animateur qui sera notifié chaque fois qu'une distance est définitivement connue
     * @return Le résultat contenant les distances minimales et les prédécesseurs
     */

    @Override
    public Distances<T> compute(IGraph<T> g, T src, Animator<T> animator) {
        Map<T, Integer> dist = new HashMap<>();
        Map<T, T> pred = new HashMap<>();

        Set<T> settled = new HashSet<>();

        PriorityQueue<NodeDistance<T>> pq = new PriorityQueue<>((nd1, nd2) ->
                Integer.compare(nd1.distance, nd2.distance));

        dist.put(src, 0);
        pred.put(src, null);
        pq.add(new NodeDistance<>(src, 0));

        while (!pq.isEmpty()) {
            NodeDistance<T> current = pq.poll();
            T u = current.node;

            if (settled.contains(u)) {
                continue;
            }
            settled.add(u);
            animator.accept(u, current.distance);

            for (IGraph.Arc<T> arc : g.getSucc(u)) {
                if (arc.val() < 0) {
                    throw new IllegalArgumentException("Arc de poids négatif détecté");
                }
                T v = arc.dst();
                int weight = arc.val();
                int newDist = dist.get(u) + weight;
                if (!dist.containsKey(v) || newDist < dist.get(v)) {
                    dist.put(v, newDist);
                    pred.put(v, u);
                    pq.add(new NodeDistance<>(v, newDist));
                }
            }
        }

        return new Distances<>(dist, pred);
    }

    /**
     * Classe interne pour stocker un sommet avec sa distance
     * dans la file de priorité.
     */
    private static class NodeDistance<T> {
        T node;
        int distance;

        NodeDistance(T node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}
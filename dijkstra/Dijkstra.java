package dijkstra;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import graph.Graph;
import graph.ShortestPath;

// Algorithme de Dijkstra pour trouver les plus courts chemins

public class Dijkstra<T> implements ShortestPath<T> {
    @Override
    public Distances<T> compute(Graph<T> g, T src, Animator<T> animator) {
        Map<T, Integer> dist = new HashMap<>();    // distance minimale connue vers chaque sommet
        Map<T, T> pred = new HashMap<>();          // prédécesseur de chaque sommet dans le plus court chemin
        Set<T> settled = new HashSet<>();          // sommets dont la distance est fixée

        PriorityQueue<NodeDistance<T>> pq = new PriorityQueue<>(
            (nd1, nd2) -> Integer.compare(nd1.distance, nd2.distance)
        );

        dist.put(src, 0);
        pred.put(src, null);
        pq.add(new NodeDistance<>(src, 0));

        while (!pq.isEmpty()) {
            NodeDistance<T> current = pq.poll();  // sommet avec la plus petite distance
            T u = current.node;

            if (settled.contains(u)) continue;    // si déjà traité, on ignore
            settled.add(u);                       // on le marque comme traité
            animator.accept(u, current.distance); // on notifie que sa distance est fixée

            for (Graph.Arc<T> arc : g.getSucc(u)) {
                if (arc.val() < 0) {
                    // Dijkstra ne supporte pas les poids négatifs
                    throw new IllegalArgumentException("Arc de poids négatif détecté");
                }

                T v = arc.dst();                // voisin de u
                int weight = arc.val();         // poids de l’arc u → v
                int newDist = dist.get(u) + weight; // distance potentielle vers v

                // Si le chemin trouvé est plus court que le précédent
                if (!dist.containsKey(v) || newDist < dist.get(v)) {
                    dist.put(v, newDist);       // on met à jour la distance
                    pred.put(v, u);             // on enregistre le prédécesseur
                    pq.add(new NodeDistance<>(v, newDist)); // on ajoute v à traiter
                }
            }
        }

        // On retourne les résultats finaux
        return new Distances<>(dist, pred);
    }

    // Représente un sommet avec sa distance (utilisé dans la file de priorité)

    private static class NodeDistance<T> {
        T node;
        int distance;

        NodeDistance(T node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}

package dijkstra;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import graph.Graph;
import graph.GrapheHHAdj;
import graph.ShortestPath.Distances;
import graph.VarGraph;
import org.junit.Test;


public class DijkstraTest {
    private static final String FROM = "A";
    private static final String TO = "F";
    private static final int EXPECTED_DIST = 5;
    private static final List<String> EXPECTED_PATH = List.of("F", "E", "B", "D", "A"); // in pred order

    @Test
    public void test() {
        VarGraph g = new GrapheHHAdj();
        g.peupler("A-B(6), A-C(1), A-D(2), B-E(1), C-E(4), D-B(1), E-F(1)");
        tester(g);
    }

    void tester(Graph g) {
        Distances<String> dst = new Dijkstra<String>().compute(g, FROM);
        assertEquals(EXPECTED_DIST, dst.dist().get(TO));
        String c = EXPECTED_PATH.get(0);
        for (String s : EXPECTED_PATH) {
            assertEquals(s, c);
            c = dst.pred().get(c);
        }
        assertNull(c);
    }

    @Test
    public void testBoucle() {
        // Graphe avec une boucle sur A, et un chemin A → B → C → D
        VarGraph g = new GrapheHHAdj();
        g.peupler("A-A(3), A-B(1), B-C(2), C-D(1)");

        // Calcul des plus courts chemins depuis A
        Distances<String> dst = new Dijkstra<String>().compute(g, "A");

        // Vérifie les distances minimales depuis A
        assertEquals(0, dst.dist().get("A")); // Distance de A à A = 0
        assertEquals(1, dst.dist().get("B")); // A-B = 1
        assertEquals(3, dst.dist().get("C")); // A-B-C = 1+2
        assertEquals(4, dst.dist().get("D")); // A-B-C-D = 1+2+1

        // Vérifie les prédécesseurs pour chaque sommet
        assertNull(dst.pred().get("A"));        // A est la source
        assertEquals("A", dst.pred().get("B")); // B vient de A
        assertEquals("B", dst.pred().get("C")); // C vient de B
        assertEquals("C", dst.pred().get("D")); // D vient de C
    }

    @Test
    public void testCheminsMultiples() {
        VarGraph g = new GrapheHHAdj();
        g.peupler("A-B(2), A-C(2), B-D(2), C-D(2)");
        Distances<String> dst = new Dijkstra<String>().compute(g, "A");

        // Distance minimale correcte vers D (via B ou C)
        assertEquals(4, dst.dist().get("D"));

        // Le prédécesseur de D peut être B ou C, les deux sont valides
        String predD = dst.pred().get("D");
        assertTrue(predD.equals("B") || predD.equals("C"));

        // Chemins valides possibles : A-B-D ou A-C-D
        if (predD.equals("B")) {
            assertEquals("A", dst.pred().get("B"));
        } else {
            assertEquals("A", dst.pred().get("C"));
        }

        // Vérification des distances de tous les sommets
        assertEquals(0, dst.dist().get("A"));
        assertEquals(2, dst.dist().get("B"));
        assertEquals(2, dst.dist().get("C"));
    }
}


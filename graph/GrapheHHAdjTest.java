package graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GrapheHHAdjTest {


    GrapheHHAdj g = new GrapheHHAdj();
    @Test
    void test() {

        ArrayList l = new ArrayList();
        ArrayList l2 = new ArrayList<Graph.Arc<String>>();
        l2.add("c");
        assertEquals(g.getSucc("a"), l);
        g.ajouterSommet("b");
        assertEquals(g.getSucc("b"), l);
        g.ajouterSommet("c");
        g.ajouterArc("b","c",4);
        assertEquals(g.getSucc("b").get(0).dst(), "c");
        assertEquals(g.getSucc("b").get(0).val(), 4);
        g.ajouterArc("d","e",6);
        assertEquals(g.getSucc("d").get(0).dst(), "e");
        assertEquals(g.getSucc("d").get(0).val(), 6);
        g.ajouterArc("b","d",7);
        assertEquals(g.getSucc("b").get(1).dst(), "d");
    }

    @Test
    void ajouterSommet() {
    }

    @Test
    void ajouterArc() {
    }
}
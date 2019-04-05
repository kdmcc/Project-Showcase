package hw5;

/**
 * This class contains a set of tests for the implementation of the Graph<String, String> class.
 */

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphTest {


    @Test
    public void testAddNode() {
        Graph<String, String> testGraph = new Graph<>();

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");

        testGraph.addNode(node1);
        assertEquals(testGraph.getNodeCount(), 1);
        testGraph.addNode(node2);
        assertEquals(testGraph.getNodeCount(), 2);
        testGraph.addNode(node2);
        assertEquals(testGraph.getNodeCount(), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullNode() {
        Graph<String, String> testGraph = new Graph<>();

        testGraph.addNode(null);
    }

    @Test
    public void testAddEdge() {
        Graph<String, String> testGraph = new Graph<>();

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");

        testGraph.addNode(node1);
        testGraph.addNode(node2);
        testGraph.addNode(node3);

        Edge<String, String> edge1 = new Edge<>(node1, node2, "edge1");
        Edge<String, String> edge2 = new Edge<>(node1, node3, "edge2");
        Edge<String, String> edge3 = new Edge<>(node1, node2, "edge1");
        Edge<String, String> edge4 = new Edge<>(node1, node2, "edge3");

        testGraph.addEdge(edge1);
        assertEquals(testGraph.getEdges().size(), 1);
        testGraph.addEdge(edge2);
        assertEquals(testGraph.getEdges().size(), 2);
        testGraph.addEdge(edge3);
        assertEquals(testGraph.getEdges().size(), 2);
        testGraph.addEdge(edge4);
        assertEquals(testGraph.getEdges().size(), 3);
    }

    @Test
    public void testAddEdgeBeforeNodes() {
        Graph<String, String> testGraph = new Graph<>();

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");
        Node<String>node4 = new Node<>("node 4");
        Node<String>node5 = new Node<>("node 5");

        Edge<String, String> edge1 = new Edge<>(node1, node1, "edge1");
        Edge<String, String> edge2 = new Edge<>(node2, node3, "edge2");
        Edge<String, String> edge3 = new Edge<>(node4, node5, "edge3");

        testGraph.addEdge(edge1);
        assertEquals(testGraph.getEdges().size(), 1);
        assertEquals(testGraph.getNodeCount(), 1);

        testGraph.addEdge(edge2);
        assertEquals(testGraph.getEdges().size(), 2);
        assertEquals(testGraph.getNodeCount(), 3);

        testGraph.addEdge(edge3);
        assertEquals(testGraph.getEdges().size(), 3);
        assertEquals(testGraph.getNodeCount(), 5);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNullEdge() {
        Graph<String, String> testGraph = new Graph<>();

        testGraph.addEdge(null);
    }

    // creates a simple graph to help test other methods.
    // nodes in graph are {"node 1", "node 2", "node 3", "node 4", "node 5"
    //edges in graph are (n1 -> n2, n1 -> n3, n1 -> n4, n1 -> n1, n2 -> n3, n3 -> n1
    private Graph<String, String> createSimpleGraph() {
        Graph<String, String> simple = new Graph<>();

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");
        Node<String>node4 = new Node<>("node 4");
        Node<String>node5 = new Node<>("node 5");

        simple.addNode(node1);
        simple.addNode(node2);
        simple.addNode(node3);
        simple.addNode(node4);
        simple.addNode(node5);

        simple.addEdge(new Edge<>(node1, node2, "edge1"));
        simple.addEdge(new Edge<>(node1, node3, "edge2"));
        simple.addEdge(new Edge<>(node1, node4, "edge3"));
        simple.addEdge(new Edge<>(node1, node1, "edge4"));
        simple.addEdge(new Edge<>(node2, node3, "edge5"));
        simple.addEdge(new Edge<>(node3, node1, "edge6"));

        return simple;
    }

    @Test
    public void testCreateSimpleGraph() {
        Graph<String, String> testGraph = createSimpleGraph();

        //test nodes
        Set<Node<String>> nodes = testGraph.nodeSet();

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");
        Node<String>node4 = new Node<>("node 4");
        Node<String>node5 = new Node<>("node 5");

        assertTrue(nodes.contains(node1));
        assertTrue(nodes.contains(node2));
        assertTrue(nodes.contains(node3));
        assertTrue(nodes.contains(node4));
        assertTrue(nodes.contains(node5));
        assertEquals(nodes.size(), 5);

        //test edges
        Set<Edge<String, String>> edges = testGraph.getEdges();
        assertEquals(edges.size(), 6);

        //create a set of the strings "edge1", ... , "edge6"
        Set<String> labels = new HashSet<>();
        for (int i = 1; i <= 6; i++) {
            labels.add("edge" + i);
        }
        for(Edge<String, String> e: edges) {
            assertTrue(labels.contains(e.getLabel()));
        }

    }

    @Test
    public void testRemoveNode() {
        Graph<String, String> testGraph = new Graph<>();

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");
        Node<String>node4 = new Node<>("node 4");

        testGraph.addNode(node1);
        testGraph.addNode(node2);
        testGraph.addNode(node3);
        testGraph.addNode(node4);

        assertEquals(testGraph.getNodeCount(), 4);

        testGraph.removeNode(node4);
        assertEquals(testGraph.getNodeCount(), 3);

        testGraph.removeNode(node3);
        assertEquals(testGraph.getNodeCount(), 2);

        testGraph.removeNode(node2);
        assertEquals(testGraph.getNodeCount(), 1);

        testGraph.removeNode(node2);
        assertEquals(testGraph.getNodeCount(), 1);

        testGraph.removeNode(node1);
        assertEquals(testGraph.getNodeCount(), 0);
    }

    @Test
    public void testRemoveNodeWhenEmpty() {
        Graph<String, String> testGraph = new Graph<>();

        Node<String>node1 = new Node<>("node 1");

        assertEquals(testGraph.getNodeCount(), 0);
        testGraph.removeNode(node1);
        assertEquals(testGraph.getNodeCount(), 0);

        testGraph.addNode(node1);
        assertEquals(testGraph.getNodeCount(), 1);
        testGraph.removeNode(node1);
        assertEquals(testGraph.getNodeCount(), 0);
        testGraph.removeNode(node1);
        assertEquals(testGraph.getNodeCount(), 0);
    }

    @Test
    public void testRemoveNodeWithEdges() {
        Graph<String, String> testGraph = createSimpleGraph();

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");
        Node<String>node4 = new Node<>("node 4");

        assertEquals(testGraph.getNodeCount(), 5);
        assertEquals(testGraph.getEdges().size(), 6);

        testGraph.removeNode(node4);
        assertEquals(testGraph.getNodeCount(), 4);
        assertEquals(testGraph.getEdges().size(), 5);

        testGraph.removeNode(node3);
        assertEquals(testGraph.getNodeCount(), 3);
        assertEquals(testGraph.getEdges().size(), 2);

        testGraph.removeNode(node2);
        assertEquals(testGraph.getNodeCount(), 2);
        assertEquals(testGraph.getEdges().size(), 1);

        testGraph.removeNode(node1);
        assertEquals(testGraph.getNodeCount(), 1);
        assertEquals(testGraph.getEdges().size(), 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveNodeNull() {
        Graph<String, String> testGraph = createSimpleGraph();

        testGraph.removeNode(null);
    }

    @Test
    public void testRemoveEdge() {
        Graph<String, String> testGraph = createSimpleGraph();
        assertEquals(testGraph.getEdges().size(), 6);

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");
        Node<String>node4 = new Node<>("node 4");

        testGraph.removeEdge(new Edge<>(node1, node2, "edge1"));
        assertEquals(testGraph.getEdges().size(), 5);

        testGraph.removeEdge(new Edge<>(node1, node2, "edge1"));
        assertEquals(testGraph.getEdges().size(), 5);

        testGraph.removeEdge(new Edge<>(node1, node3, "edge2"));
        assertEquals(testGraph.getEdges().size(), 4);

        testGraph.removeEdge(new Edge<>(node1, node4, "edge3"));
        assertEquals(testGraph.getEdges().size(), 3);
    }

    @Test
    public void testRemoveEdgeWhenEmpty() {
        Graph<String, String> testGraph = new Graph<>();

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");

        assertEquals(testGraph.getEdges().size(), 0);
        testGraph.removeEdge(new Edge<>(node1, node2, "edge1"));
        assertEquals(testGraph.getEdges().size(), 0);

        testGraph.addEdge(new Edge<>(node1, node2, "edge1"));
        assertEquals(testGraph.getEdges().size(), 1);

        testGraph.removeEdge(new Edge<>(node1, node2, "edge1"));
        assertEquals(testGraph.getEdges().size(), 0);
        testGraph.removeEdge(new Edge<>(node1, node2, "edge1"));
        assertEquals(testGraph.getEdges().size(), 0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveNullEdge() {
        Graph<String, String> testGraph = createSimpleGraph();

        testGraph.removeEdge(null);
    }

    @Test
    public void testContainsNode() {
        Graph<String, String> testGraph = new Graph<>();

        Node<String>node1 = new Node<>("node 1");
        assertFalse(testGraph.containsNode(node1));

        testGraph.addNode(node1);
        assertTrue(testGraph.containsNode(node1));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testContainsNullNode() {
        Graph<String, String> testGraph = new Graph<>();

        testGraph.containsNode(null);

    }

    @Test
    public void testContainsEdge() {
        Graph<String, String> testGraph = new Graph<>();

        Node<String>node1 = new Node<>("node 1");

        assertFalse(testGraph.containsEdge(new Edge<>(node1, node1, "edge1")));

        testGraph.addEdge(new Edge<>(node1, node1, "edge1"));
        assertTrue(testGraph.containsEdge(new Edge<>(node1, node1, "edge1")));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testContainsNullEdge() {
        Graph<String, String> testGraph = new Graph<>();

        testGraph.containsEdge(null);

    }

    @Test
    public void testNodeSet() {
        Graph<String, String> testGraph = new Graph<>();

        Set<Node<String>> expected = new HashSet<>();
        Set<Node<String>> nodes = testGraph.nodeSet();
        assertTrue(nodes.isEmpty());

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");
        Node<String>node4 = new Node<>("node 4");

        testGraph.addNode(node1);
        expected.add(node1);
        testGraph.addNode(node2);
        expected.add(node2);
        testGraph.addNode(node3);
        expected.add(node3);
        testGraph.addNode(node4);
        expected.add(node4);

        nodes = testGraph.nodeSet();
        for (Node<String>expNode: expected) {
            assertTrue(nodes.contains(expNode));
        }
        assertEquals(nodes.size(), expected.size());
    }

    @Test
    public void testGetEdgesFrom() {
        Graph<String, String> testGraph = new Graph<>();
        Set<Edge<String, String>> edges = testGraph.getEdges();
        assertTrue(edges.isEmpty());

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");

        Edge<String, String> edge1 = new Edge<>(node1, node2, "edge1");
        Edge<String, String> edge2 = new Edge<>(node1, node3, "edge2");
        Edge<String, String> edge3 = new Edge<>(node1, node1, "edge3");
        Edge<String, String> edge4 = new Edge<>(node2, node1, "edge4");
        Edge<String, String> edge5 = new Edge<>(node2, node3, "edge5");

        List<Edge<String, String>> expected1 = new ArrayList<>();
        List<Edge<String, String>> expected2 = new ArrayList<>();
        testGraph.addEdge(edge1);
        expected1.add(edge1); // parent is node 1
        testGraph.addEdge(edge2);
        expected1.add(edge2); // parent is node 1
        testGraph.addEdge(edge3);
        expected1.add(edge3); // parent is node 1
        testGraph.addEdge(edge4);
        expected2.add(edge4); // parent is node 2
        testGraph.addEdge(edge5);
        expected2.add(edge5); // parent is node 2

        Set<Edge<String, String>> edges1 = testGraph.getEdgesFrom(node1);
        for (Edge<String, String> expEdge: expected1) {
            assertTrue(edges1.contains(expEdge));
        }
        assertEquals(edges1.size(), expected1.size());

        Set<Edge<String, String>> edges2 = testGraph.getEdgesFrom(node2);
        for (Edge<String, String> expEdge: expected2) {
            assertTrue(edges2.contains(expEdge));
        }
        assertEquals(edges2.size(), expected2.size());
    }

    @Test
    public void testGetEdgesTo() {
        Graph<String, String> testGraph = new Graph<>();
        Set<Edge<String, String>> edges = testGraph.getEdges();
        assertTrue(edges.isEmpty());

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");

        Edge<String, String> edge1 = new Edge<>(node1, node2, "edge1");
        Edge<String, String> edge2 = new Edge<>(node1, node3, "edge2");
        Edge<String, String> edge3 = new Edge<>(node1, node1, "edge3");
        Edge<String, String> edge4 = new Edge<>(node2, node3, "edge4");
        Edge<String, String> edge5 = new Edge<>(node3, node2, "edge5");

        List<Edge<String, String>> expected2 = new ArrayList<>();
        List<Edge<String, String>> expected3 = new ArrayList<>();
        testGraph.addEdge(edge1);
        expected2.add(edge1); // goes to node 2

        testGraph.addEdge(edge2);
        expected3.add(edge2); // goes to node 3

        testGraph.addEdge(edge3);

        testGraph.addEdge(edge4);
        expected3.add(edge4); // goes to node 3

        testGraph.addEdge(edge5);
        expected2.add(edge5); // goes to node 2

        Set<Edge<String, String>> edges2 = testGraph.getEdgesTo(node2);
        for (Edge<String, String> expEdge: expected2) {
            assertTrue(edges2.contains(expEdge));
        }
        assertEquals(edges2.size(), expected2.size());

        Set<Edge<String, String>> edges3 = testGraph.getEdgesTo(node3);
        for (Edge<String, String> expEdge: expected3) {
            assertTrue(edges3.contains(expEdge));
        }
        assertEquals(edges3.size(), expected3.size());
    }

    @Test
    public void testGetEdges() {
        Graph<String, String> testGraph = new Graph<>();
        Set<Edge<String, String>> edges = testGraph.getEdges();
        assertTrue(edges.isEmpty());

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");

        Edge<String, String> edge1 = new Edge<>(node1, node2, "edge1");
        Edge<String, String> edge2 = new Edge<>(node1, node3, "edge2");
        Edge<String, String> edge3 = new Edge<>(node1, node1, "edge3");
        Edge<String, String> edge4 = new Edge<>(node2, node1, "edge4");
        Edge<String, String> edge5 = new Edge<>(node3, node1, "edge5");

        List<Edge<String, String>> expected = new ArrayList<>();
        testGraph.addEdge(edge1);
        expected.add(edge1);
        testGraph.addEdge(edge2);
        expected.add(edge2);
        testGraph.addEdge(edge3);
        expected.add(edge3);
        testGraph.addEdge(edge4);
        expected.add(edge4);
        testGraph.addEdge(edge5);
        expected.add(edge5);

        edges = testGraph.getEdges();
        for (Edge<String, String> expEdge: expected) {
            assertTrue(edges.contains(expEdge));
        }

        assertEquals(edges.size(), expected.size());
    }

    @Test
    public void testClear() {
        Graph<String, String> testGraph = createSimpleGraph();
        testGraph.clear();

        assertEquals(testGraph.getNodeCount(), 0);
        assertTrue(testGraph.nodeSet().isEmpty());
        assertTrue(testGraph.getEdges().isEmpty());
    }

    @Test
    public void testGetChildren() {
        Graph<String, String> testGraph = createSimpleGraph();

        Node<String>node1 = new Node<>("node 1");
        Node<String>node2 = new Node<>("node 2");
        Node<String>node3 = new Node<>("node 3");
        Node<String>node4 = new Node<>("node 4");

        Set<Node<String>> children1 = testGraph.getChildren(node1);
        assertEquals(children1.size(), 4);
        assertTrue(children1.contains(node1)
                        && children1.contains(node2)
                        && children1.contains(node3)
                        && children1.contains(node4));

        Set<Node<String>> children2 = testGraph.getChildren(node2);
        assertEquals(children2.size(), 1);
        assertTrue(children1.contains(node3));

        Set<Node<String>> children4 = testGraph.getChildren(node4);
        assertTrue(children4.isEmpty());



    }

}

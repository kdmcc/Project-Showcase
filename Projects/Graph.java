package hw5;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
/**
 * The Graph class represents a mutable mathematical graph made up of nodes and directed and labelled edges.
 * The graph supports multiple edges between the same two nodes as well as edges that connect a node with
 * itself. A Graph can contain any number of unique nodes, and any number of unique edges between any of
 * the nodes.
 *
 * Abstract Invariant:
 *  -Edges with the same parent node and child node cannot contain the same label.
 *  -The parent node and child node of an edge must be in the Graph
 *  -All nodes must have unique labels.
 * @param <N> is the type of the data stored in this graph's nodes.
 * @param <E> is the type labels of the edges of this graph.
 */
public class Graph<N extends Comparable<? super N>, E extends Comparable<? super E>> {
    // Abstract Function(this) = a graph where edges.keySet() is the set of nodes and edges.get(node) is the set.
    //                              of all directed edges starting at node.
    // Representation Invariant: - edges, children != null.
    //                           - edges.keySet() = children.keySet() = all nodes in the graph.
    //                           - both the parent and child of all edges are in the graph.
    //                           - all nodes have store unique data.
    //                           - all edges between the same two nodes must have unique labels.
    //                           - edges.get(node)  contains all edges with node is the parent node
    //                           - children.get(node) contains all nodes that have an edge from node to it.

    // Maps a node to a set containing all edges where that node is the parent
    HashMap<Node<N>, Set<Edge<N, E>>> edges;

    // Maps a node with a set of all of its children nodes.
    HashMap<Node<N>, Set<Node<N>>> children;

    /**
     * @spec.modifies this.
     * @spec.effects Creates a new graph with no nodes or edges
     */
    public Graph() {
        edges = new HashMap<>();
        children = new HashMap<>();
    }

    /**
     * @param node is the node that will be added to this graph
     * @spec.requires node != null.
     * @spec.modifies this.
     * @spec.effects adds the given node to this graph, does nothing if the graph already contains the node.
     * @throws IllegalArgumentException if node == null.
     */
    public void addNode(Node<N> node) {
        checkRep(0);
        if (node == null) {
            throw new IllegalArgumentException();
        }

        if (!this.containsNode(node)) {
            edges.put(node, new HashSet<Edge<N, E>>());
            children.put(node, new HashSet<Node<N>>());
        }
    }

    /**
     * @param edge is an Edge to be added to the graph.
     * @spec.requires edge != null
     * @spec.modifies this.
     * @spec.effects adds this edge to the graph if it does not already contain it. If the graph does not contain a
     * node corresponding to the edge's parent or child nodes, adds that node to the graph.
     * @throws IllegalArgumentException if edge == null.
     */
    public void addEdge(Edge<N, E> edge) {
        checkRep(0);
        if (edge == null) {
            throw new IllegalArgumentException();
        }

        if (!this.containsEdge(edge)) {
            Node<N> parent = edge.getParent();
            Node<N> child = edge.getChild();

            // Add nodes to graph if they are not already in it.
            this.addNode(parent);
            this.addNode(child);

            // Add edge to graph
            edges.get(parent).add(edge);
            children.get(parent).add(child);
        }
    }

    /**
     * @param node is the node that will be removed from the graph.
     * @spec.requires node != null
     * @spec.modifies this.
     * @spec.effects removes the given node and all edges where the node is its parent or child from
     * this graph. Does nothing if this graph does not contain a matching node.
     * @throws IllegalArgumentException if node == null
     */
    public void removeNode(Node<N> node) {
        checkRep(1);
        if (node == null) {
            throw new IllegalArgumentException();
        }
        // Remove this node's outgoing edges.
        edges.remove(node);
        children.remove(node);

        // Remove the edges where this node is the child
        for (Node<N> n: edges.keySet()) {
            if (children.get(n).contains(node)) {
                children.get(n).remove(node);
                Set<Edge<N, E>> toSearch = edges.get(n);
                Set<Edge<N, E>> toRemove = new HashSet<>();
                for (Edge<N, E> e: toSearch) {
                    if (e.getChild().equals(node)) {
                        toRemove.add(e);
                    }
                }
                toSearch.removeAll(toRemove);
            }
        }
        checkRep(1);
    }

    /**
     * @param edge is an Edge that will be removed from this graph.
     * @spec.requires edge != null
     * @spec.modifies this.
     * @spec.effects removes the given edge from this graph. Does nothing if the graph does not contain a matching edge.
     * @throws IllegalArgumentException if edge == null
     */
    public void removeEdge(Edge<N, E> edge) {
        checkRep(0);
        if (edge == null) {
            throw new IllegalArgumentException();
        }
        if (this.containsEdge(edge)) {
            edges.get(edge.getParent()).remove(edge);
        }
    }

    /**
     * @return the number of unique nodes in the graph
     */
    public int getNodeCount() {
        checkRep(0);
        return edges.keySet().size();
    }

    /**
     * @param node is the node that will be searched for in this graph
     * @spec.requires node != null.
     * @throws IllegalArgumentException if node == null.
     * @return a boolean representing whether or not this graph contains a node with the given label.
     */
    public boolean containsNode(Node<N> node) {
        checkRep(0);
        if (node == null) {
            throw new IllegalArgumentException();
        }
        return edges.containsKey(node);
    }

    /**
     * @param edge is an Edge that will be searched for in this graph.
     * @spec.requires edge != null.
     * @throws IllegalArgumentException if edge == null.
     * @return a boolean representing whether or not this graph contains a matching edge.
     */
    public boolean containsEdge(Edge<N, E> edge) {
        checkRep(0);
        if (edge == null) {
            throw new IllegalArgumentException();
        }
        Node<N> parent = edge.getParent();
        return edges.containsKey(parent) && edges.get(parent).contains(edge);
    }

    /**
     * @return a set of Strings that represent the unique labels of the nodes in the graph.
     */
    public Set<Node<N>> nodeSet() {
        checkRep(0);
        Set<Node<N>> nodeSet = new HashSet<>();
        nodeSet.addAll(edges.keySet());
        return nodeSet;
    }

    /**
     *
     * @param node will be the parent of all of the children nodes being returned.
     * @spec.requires this graph contains a node with the given label.
     * @throws IllegalArgumentException if this graph does not contain a node with the given label.
     * @return a Set of Strings corresponding to the labels of the given node's children nodes.
     */
    public Set<Node<N>> getChildren(Node<N> node) {
        checkRep(0);
        if (node == null) {
            throw new IllegalArgumentException();
        }

        Set<Node<N>> childSet = new HashSet<>();
        childSet.addAll(children.get(node));
        return childSet;
    }

    /**
     * @param node is the parent node of the edges to be returned.
     * @spec.requires node != null and the graph contains the node.
     * @throws IllegalArgumentException if node == null or if the graph does not contain the node.
     * @return a set of all the edges whose parent node is equal to the given node. returns an empty list if there are no
     * edges with a matching parent node.
     */
    public Set<Edge<N, E>> getEdgesFrom(Node<N> node) {
        checkRep(0);
        if (node == null || !edges.containsKey(node)) {
            throw new IllegalArgumentException();
        }
        Set<Edge<N, E>> edgesFrom = new HashSet<>();
        edgesFrom.addAll(edges.get(node));

        return edgesFrom;
    }

    /**
     * @param node is the child node of all of the edges to be returned.
     * @spec.requires node != null and the graph contains a the node.
     * @throws IllegalArgumentException if node == null or if the graph does not contain the node.
     * @return a set of all the edges whose child is the given node. returns an empty list if there are no
     * edges with a matching child node.
     */
    public Set<Edge<N, E>> getEdgesTo(Node<N> node) {
        checkRep(1);
        if (node == null || !edges.containsKey(node)) {
            throw new IllegalArgumentException();
        }

        Set<Edge<N, E>> edgesTo = new HashSet<>();

        // add the edges where this node is the child to edgesTo
        for (Node<N> n: children.keySet()) {
            if (children.get(n).contains(node)) {
                Set<Edge<N, E>> toSearch = edges.get(n);
                for (Edge<N, E> e: toSearch) {
                    if (e.getChild().equals(node)) {
                        edgesTo.add(e);
                    }
                }
            }
        }

        return edgesTo;
    }

    /**
     * @return a set of all the edges within this graph. If the graph contains no edges, this
     *          will return an empty list.
     */
    public Set<Edge<N, E>> getEdges() {
        checkRep(0);
        Set<Edge<N, E>> allEdges = new HashSet<>();

        for (Node<N> node: edges.keySet()) {
            allEdges.addAll(edges.get(node));
        }

        return allEdges;
    }

    /**
     * @spec.modifies this.
     * @spec.effects removes all nodes and edges from this graph
     */
    public void clear()
    {
        edges.clear();
        children.clear();
    }

    public void checkRep(int expense) {
        if (expense > 0) {
            assert (edges != null);
            assert (children != null);

            assert (edges.keySet().size() == children.keySet().size());
        }
        if (expense > 1) {
            Set<Node<N>> shouldContain = new HashSet<>();

            for (Node<N> n: edges.keySet()) {
                for (Edge<N, E> e: edges.get(n)) {
                    assert (n.equals(e.getParent()));
                    assert (children.get(n).contains(e.getChild()));

                    shouldContain.add(e.getParent());
                    shouldContain.add(e.getChild());
                }
            }

            for (Node<N> node: shouldContain) {
                assert (edges.keySet().contains(node));
                assert (children.keySet().contains(node));
            }
        }
    }
}

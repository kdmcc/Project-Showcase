package hw5;

/**
 * Edge represents an immutable edge of a directed and labelled graph.
 * An edge connects a parent node and a child node and has its own label.
 *
 * Specification fields:
*   @specfield parent node  : String    // the node that this edge "comes from"
 *  @specfield child node   : String    // the node that this edge "goes to"
 *  @specfield label        : String    // The label for the edge
 *
 * Abstract Invariant:
 *  -Each edge has a non-null parent node, child node, and label.
 *  -The parent node and child node may be the same.
 *
 * @param <N> is the type of the data stored in this edge's nodes.
 * @param <E> is the type labels of the edges of this edge.
 */
public class Edge<N extends Comparable<? super N>, E extends Comparable<? super E>> implements Comparable<Edge<N, E>> {

    // Rep Invariant: parent, child, label != null
    // Abs Function: This object is an Edge in a graph where parent is tha parent node of the edge, child is the
    //              child node, and label is the label of the edge.

    // The parent node of the edge, i.e. the node the edge starts from.
    private final Node<N> parent;
    // The child node of the edge, i.e. the node the edge goes to.
    private final Node<N> child;
    // The label of this edge.
    private final E label;

    /**
     * @param parent is the edge's parent node.
     * @param child is the edge's child node.
     * @param label is this edge's label.
     * @spec.requires parent, child, and label are all non-null.
     * @spec.modifies this.
     * @spec.effects creates a new edge object with the given label and parent and child nodes.
     * @throws IllegalArgumentException is parent, child, or label are null
     */
    public Edge (Node<N> parent, Node<N> child, E label) {
        if (parent == null || child == null || label == null) {
            throw new IllegalArgumentException();
        }
        this.parent = parent;
        this.child = child;
        this.label = label;
        checkRep();
    }

    /**
     * @return the label of this edge's parent node.
     */
    public Node<N> getParent() {
        return this.parent;
    }

    /**
     * @return the label of this edge's child node.
     */
    public Node<N> getChild() {
        return this.child;
    }

    /**
     * @return this edge's label.
     */
    public E getLabel() {
        return this.label;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Edge<?, ?>)) {
            return false;
        }

        Edge<?, ?> other1 = (Edge<?, ?>)other;
        return other1.getParent().equals(parent)
                && other1.getChild().equals(child)
                && other1.getLabel().equals(label);
    }

    @Override
    public int hashCode() {
        int hash = parent.hashCode() *19;
        hash += child.hashCode() * 17;
        hash += label.hashCode();
        return hash;
    }

    public int compareTo(Edge<N, E> other) {
        if (parent.equals(other.parent)) {
            if (child.equals(other.child)) {
                return label.compareTo(other.label);
            } else {
                return child.getData().compareTo(other.getChild().getData());
            }
        } else {
            return parent.getData().compareTo(other.getParent().getData());
        }
    }

    private void checkRep() {
        assert (parent != null);
        assert (child != null);
        assert (label != null);
    }
}

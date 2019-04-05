package hw5;


/**
 * Node represents an immutable node of a directed and labelled graph. Each node holds data that can be used to
 * identify the node.
 *
 * Abstract Invariant:
 *  -Each node contains non-null data.
 * @param <N> is the type of the data stored in this node.
 */
public class Node<N extends Comparable<? super N>> {
    // Rep Invariant: data != null
    // Abs Function: This object is a node that contains data or a label that is == to this.data.

    // The data or label of this node.
    private final N data;

    /**
     * @param data is the data that the new node will hold.
     * @spec.requires data != null.
     * @spec.modifies this.
     * @spec.effects creates a new Node object that contains data.
     * @throws IllegalArgumentException if data == null
     */
    public Node(N data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        this.data = data;
        checkRep();
    }

    /**
     * @return The data that this node holds.
     */
    public N getData() {
        return data;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Node<?>)) {
            return false;
        }
        Node<?> o = (Node<?>)other;
        return data.equals(o.getData());
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    // Asserts that the rep inv. has been maintained.
    private void checkRep() {
        assert (data != null);
    }

}

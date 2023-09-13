package Graph;

import java.util.ArrayList;

public class Node<T> {
    private ArrayList<Node<T>> nodes;
    private T val;

    public Node(T val) {
        this.val = val;
        nodes = new ArrayList<>();
    }

    public Node addNode(Node node) {
        nodes.add(node);
        return node;
    }

    public Node addNode(T val) {
        Node n = (new Node<T>(val));
        nodes.add(n);
        return n;
    }

    public T getValue() {
        return val;
    }

    @Override
    public String toString() {
        String f = "";
        f += val + "\t:";
        for (Node n :
                nodes) {
            f += n.val + ",\t";
        }
        return f;
    }

    public ArrayList<Node<T>> GetNodes() {
        return nodes;
    }
}

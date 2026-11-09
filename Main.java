
import java.util.ArrayList;
import java.util.List;

class BST {
    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    public void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }
        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        }
        return node;
    }

    public void delete(int value) {
        root = delete(root, value);
    }

    private Node delete(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            node.left = delete(node.left, value);
        } else if (value > node.value) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            Node successor = minNode(node.right);
            node.value = successor.value;
            node.right = delete(node.right, successor.value);
        }
        return node;
    }

    private Node minNode(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Integer> inorder() {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.value);
        inorder(node.right, result);
    }

    public List<Integer> postorder() {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    private void postorder(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        postorder(node.left, result);
        postorder(node.right, result);
        result.add(node.value);
    }

    public List<Integer> preorder() {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    private void preorder(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        result.add(node.value);
        preorder(node.left, result);
        preorder(node.right, result);
    }

    public boolean search(int value) {
        Node current = root;
        while (current != null) {
            if (value == current.value) {
                return true;
            }
            current = value < current.value ? current.left : current.right;
        }
        return false;
    }

    public List<Integer> path(int value) {
        List<Integer> result = new ArrayList<>();
        Node current = root;
        while (current != null) {
            result.add(current.value);
            if (value == current.value) {
                return result;
            }
            current = value < current.value ? current.left : current.right;
        }
        return new ArrayList<>();
    }
}

class Main {
    public static void main(String[] args) {
        BST lab5Tree = new BST();

        int[] values = {13, 22, 36, 5, 48, 17, 39, 2, 26, 40, 29, 34, 10};
        for (int value : values) {
            lab5Tree.insert(value);
        }

        lab5Tree.delete(17);

        System.out.println("Inorder (sorted): " + lab5Tree.inorder());
        System.out.println("Postorder: " + lab5Tree.postorder());
        System.out.println("Preorder: " + lab5Tree.preorder());

        System.out.println("Search for 36: " + (lab5Tree.search(36) ? "Found" : "Not found"));
        System.out.println("Search for 37: " + (lab5Tree.search(37) ? "Found" : "Not found"));

        List<Integer> pathTo2 = lab5Tree.path(2);
        System.out.println("Path from root to 2: " + (pathTo2.isEmpty() ? "Not found" : pathTo2));

        List<Integer> pathTo34 = lab5Tree.path(34);
        System.out.println("Path from root to 34: " + (pathTo34.isEmpty() ? "Not found" : pathTo34));
    }
}

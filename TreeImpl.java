import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TreeImpl<E extends Comparable<E>> implements Tree<E> {
  private Node<E> root;
  private int size;

  private static class Node<E> {
    E element;
    Node<E> left;
    Node<E> right;

    Node(E element) {
      this.element = element;
    }
  }

  @Override
  public boolean search(E e) {
    Node<E> current = root;
    while (current != null) {
      int cmp = e.compareTo(current.element);
      if (cmp == 0) {
        return true;
      }
      current = cmp < 0 ? current.left : current.right;
    }
    return false;
  }

  @Override
  public boolean insert(E e) {
    if (root == null) {
      root = new Node<>(e);
      size++;
      return true;
    }

    Node<E> parent = null;
    Node<E> current = root;
    while (current != null) {
      parent = current;
      int cmp = e.compareTo(current.element);
      if (cmp == 0) {
        return false;
      }
      current = cmp < 0 ? current.left : current.right;
    }

    if (e.compareTo(parent.element) < 0) {
      parent.left = new Node<>(e);
    } else {
      parent.right = new Node<>(e);
    }
    size++;
    return true;
  }

  @Override
  public boolean delete(E e) {
    Node<E> parent = null;
    Node<E> current = root;
    while (current != null && !current.element.equals(e)) {
      parent = current;
      current = e.compareTo(current.element) < 0 ? current.left : current.right;
    }
    if (current == null) {
      return false;
    }

    if (current.left != null && current.right != null) {
      // Replace with the largest element in the left subtree
      Node<E> repParent = current;
      Node<E> rep = current.left;
      while (rep.right != null) {
        repParent = rep;
        rep = rep.right;
      }
      current.element = rep.element;
      if (repParent == current) {
        current.left = rep.left;
      } else {
        repParent.right = rep.left;
      }
    } else {
      Node<E> child = current.left != null ? current.left : current.right;
      if (parent == null) {
        root = child;
      } else if (parent.left == current) {
        parent.left = child;
      } else {
        parent.right = child;
      }
    }
    size--;
    return true;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public void clear() {
    root = null;
    size = 0;
  }

  @Override
  public void inorder() {
    inorder(root);
  }

  public void postorder() {
    postorder(root);
  }

  @Override
  public void preorder() {
    preorder(root);
  }

  
  public Iterator<E> iterator() {
    return Collections.unmodifiableList(toList()).iterator();
  }

  
  public Object[] toArray() {
    return toList().toArray();
  }

  public <T> T[] toArray(T[] array) {
    return toList().toArray(array);
  }

  private List<E> toList() {
    List<E> list = new ArrayList<>(size);
    collectInorder(root, list);
    return list;
  }

  private void collectInorder(Node<E> node, List<E> list) {
    if (node == null) {
      return;
    }
    collectInorder(node.left, list);
    list.add(node.element);
    collectInorder(node.right, list);
  }

  private void inorder(Node<E> node) {
    if (node == null) {
      return;
    }
    inorder(node.left);
    System.out.print(node.element + " ");
    inorder(node.right);
  }

  private void postorder(Node<E> node) {
    if (node == null) {
      return;
    }
    postorder(node.left);
    postorder(node.right);
    System.out.print(node.element + " ");
  }

  private void preorder(Node<E> node) {
    if (node == null) {
      return;
    }
    System.out.print(node.element + " ");
    preorder(node.left);
    preorder(node.right);
  }

  public static void main(String[] args) {
    TreeImpl<Integer> tree = new TreeImpl<>();
    for (int value : new int[] {50, 25, 75, 10, 30, 60, 80}) {
      tree.insert(value);
    }

    System.out.println("Size: " + tree.getSize());
    System.out.println("Contains 30: " + tree.search(30));

    System.out.print("Inorder: ");
    tree.inorder();
    System.out.println();

    System.out.print("Preorder: ");
    tree.preorder();
    System.out.println();

    System.out.print("Postorder: ");
    tree.postorder();
    System.out.println();

    tree.delete(50);
    System.out.print("Inorder after deleting 50: ");
    tree.inorder();
    System.out.println();
  }
}

import java.util.ArrayList;
import java.util.Comparator;

interface Tree<E> {
  boolean search(E e);
  boolean insert(E e);
  void inorder();
  void postorder();
  void preorder();
  int getSize();
  boolean delete(E e);
}

public class BST<E> implements Tree<E> {

  protected TreeNode<E> root;
  protected int size = 0;
  protected Comparator<E> c;

  @SuppressWarnings("unchecked")
  public BST() {
    this.c = (e1, e2) -> ((Comparable<E>) e1).compareTo(e2);
  }

  public BST(Comparator<E> c) {
    this.c = c;
  }

  @SuppressWarnings("unchecked")
  public BST(E[] objects) {
    this.c = (e1, e2) -> ((Comparable<E>) e1).compareTo(e2);
    for (E object : objects)
      insert(object);
  }

  @Override
  public boolean search(E e) {
    TreeNode<E> current = root;
    while (current != null) {
      int cmp = c.compare(e, current.element);
      if (cmp < 0)
        current = current.left;
      else if (cmp > 0)
        current = current.right;
      else
        return true;
    }
    return false;
  }

  @Override
  public boolean insert(E e) {
    if (root == null) {
      root = createNewNode(e);
    } else {
      TreeNode<E> parent = null;
      TreeNode<E> current = root;
      while (current != null) {
        int cmp = c.compare(e, current.element);
        if (cmp == 0)
          return false;
        parent = current;
        current = cmp < 0 ? current.left : current.right;
      }
      if (c.compare(e, parent.element) < 0)
        parent.left = createNewNode(e);
      else
        parent.right = createNewNode(e);
    }
    size++;
    return true;
  }

  @Override
  public boolean delete(E e) {
    TreeNode<E> parent = null;
    TreeNode<E> current = root;

    while (current != null && c.compare(e, current.element) != 0) {
      parent = current;
      current = c.compare(e, current.element) < 0 ? current.left : current.right;
    }

    if (current == null)
      return false;

    if (current.left == null) {
      // Node has at most a right child
      if (parent == null)
        root = current.right;
      else if (parent.left == current)
        parent.left = current.right;
      else
        parent.right = current.right;
    } else {
      
      TreeNode<E> parentOfRightMost = current;
      TreeNode<E> rightMost = current.left;
      while (rightMost.right != null) {
        parentOfRightMost = rightMost;
        rightMost = rightMost.right;
      }
      current.element = rightMost.element;
      if (parentOfRightMost.right == rightMost)
        parentOfRightMost.right = rightMost.left;
      else
        parentOfRightMost.left = rightMost.left;
    }

    size--;
    return true;
  }

  protected TreeNode<E> createNewNode(E e) {
    return new TreeNode<>(e);
  }

  @Override
  public void inorder() {
    inorder(root);
  }

  protected void inorder(TreeNode<E> root) {
    if (root == null) return;
    inorder(root.left);
    System.out.print(root.element + " ");
    inorder(root.right);
  }

  @Override
  public void postorder() {
    postorder(root);
  }

  protected void postorder(TreeNode<E> root) {
    if (root == null) return;
    postorder(root.left);
    postorder(root.right);
    System.out.print(root.element + " ");
  }

  @Override
  public void preorder() {
    preorder(root);
  }

  protected void preorder(TreeNode<E> root) {
    if (root == null) return;
    System.out.print(root.element + " ");
    preorder(root.left);
    preorder(root.right);
  }

  public static class TreeNode<E> {
    protected E element;
    protected TreeNode<E> left;
    protected TreeNode<E> right;

    public TreeNode(E e) {
      element = e;
    }
  }

  @Override
  public int getSize() {
    return size;
  }

  public TreeNode<E> getRoot() {
    return root;
  }

  public ArrayList<TreeNode<E>> path(E e) {
    ArrayList<TreeNode<E>> list = new ArrayList<>();
    TreeNode<E> current = root;

    while (current != null) {
      list.add(current);
      int cmp = c.compare(e, current.element);
      if (cmp < 0)
        current = current.left;
      else if (cmp > 0)
        current = current.right;
      else
        break;
    }
    return list;
  }

  public static void main(String[] args) {
    BST<Integer> tree = new BST<>(new Integer[]{13, 22, 36, 5, 48, 17, 39, 2, 26, 40, 29, 34, 10});

    System.out.println("Delete 17: " + tree.delete(17));

    System.out.print("Inorder (sorted): ");
    tree.inorder();
    System.out.println();

    System.out.print("Postorder: ");
    tree.postorder();
    System.out.println();

    System.out.print("Preorder: ");
    tree.preorder();
    System.out.println();

    System.out.println("Size: " + tree.getSize());
    System.out.println("Search 36: " + tree.search(36));
    System.out.println("Search 37: " + tree.search(37));

    System.out.print("Path from root to 2: ");
    for (TreeNode<Integer> node : tree.path(2))
      System.out.print(node.element + " ");
    System.out.println();

    System.out.print("Path from root to 34: ");
    for (TreeNode<Integer> node : tree.path(34))
      System.out.print(node.element + " ");
    System.out.println();
  }
}

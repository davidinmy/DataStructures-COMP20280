package projectCode20280;

public class LinkedBinaryTree<E extends Comparable<E>> extends AbstractBinaryTree<E> {

	//nested node class
	protected static class Node<E> implements Position<E>{
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;
		
		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
		}
		
		//accessors
		public E getElement() {return element;}
		public Node<E> getParent() {return parent;}
		public Node<E> getLeft() {return left;}
		public Node<E> getRight() {return right;}
		//mutators
		public void setElement(E e) {element = e;}
		public void setParent(Node<E> parentNode) {parent = parentNode;}
		public void setLeft(Node<E> leftNode) {left = leftNode;}
		public void setRight(Node<E> rightNode) {right = rightNode; }
		
		public String toString() {
			return new StringBuilder("(").append(element).append(")").toString();
		}
	}
	
	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
			return new Node<E>(e, parent, left, right);
	}
	
	//instance variables
	  protected Node<E> root = null;     // root of the tree
	  private int size = 0;              // number of nodes in the tree

	  // constructor
	  public LinkedBinaryTree() { }      // constructs an empty binary tree

	  
	//non public utility 
	//validates position and returns it as a node
	  protected Node<E> validate(Position<E> p) throws IllegalArgumentException{
		  if (!(p instanceof Node))
		      throw new IllegalArgumentException("Not valid position type");
		    Node<E> node = (Node<E>) p;       // safe cast
		    if (node.getParent() == node)     // our convention for defunct node
		      throw new IllegalArgumentException("p is no longer in the tree");
		    return node;
	  }
	  
	// accessor methods (not already implemented in AbstractBinaryTree)
	  /**
	   * Returns the number of nodes in the tree.
	   * @return number of nodes in the tree
	   */
	  @Override
	  public int size() {
	    return size;
	  }

	  /**
	   * Returns the root Position of the tree (or null if tree is empty).
	   * @return root Position of the tree (or null if tree is empty)
	   */
	  @Override
	  public Position<E> root() {
	    return root;
	  }

	  /**
	   * Returns the Position of p's parent (or null if p is root).
	   *
	   * @param p    A valid Position within the tree
	   * @return Position of p's parent (or null if p is root)
	   * @throws IllegalArgumentException if p is not a valid Position for this tree.
	   */
	  @Override
	  public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getParent();
		}

	  /**
	   * Returns the Position of p's left child (or null if no child exists).
	   *
	   * @param p A valid Position within the tree
	   * @return the Position of the left child (or null if no child exists)
	   * @throws IllegalArgumentException if p is not a valid Position for this tree
	   */
	  @Override
	  public Position<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getLeft();
	  }

	  /**
	   * Returns the Position of p's right child (or null if no child exists).
	   *
	   * @param p A valid Position within the tree
	   * @return the Position of the right child (or null if no child exists)
	   * @throws IllegalArgumentException if p is not a valid Position for this tree
	   */
	  @Override
	  public Position<E> right(Position<E> p) throws IllegalArgumentException {
		  Node<E> node = validate(p);
		  return node.getRight();
	  }
	  
	// update methods supported by this class
	  /**
	   * Places element e at the root of an empty tree and returns its new Position.
	   *
	   * @param e   the new element
	   * @return the Position of the new element
	   * @throws IllegalStateException if the tree is not empty
	   */
	  public Position<E> addRoot(E e) throws IllegalStateException {
		if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
		root = createNode(e, null, null, null);
		size = 1;
		return root;

	  }

	  public void insert(E e){
	      //recursively add from root
	      root = addRecursive(root, e);
	      ++size;
	  }
	  
	  //recursively add Nodes to binary tree in proper position
	  private Node<E> addRecursive(Node<E> p, E e){
		if (p==null) {
			return new Node<E>(e, null, null, null);
		}
		if ((int)e < (int)p.getElement()) {
			p.left = addRecursive(p.left, e);
		}
		else if ((int)e < (int)p.getElement()) {
			p.right = addRecursive(p.right, e);
		}
		else {
			return p;
		}
		return p;
	  }

	  
	  /**
	   * Creates a new left child of Position p storing element e and returns its Position.
	   *
	   * @param p   the Position to the left of which the new element is inserted
	   * @param e   the new element
	   * @return the Position of the new element
	   * @throws IllegalArgumentException if p is not a valid Position for this tree
	   * @throws IllegalArgumentException if p already has a left child
	   */
	  public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException, IllegalAccessException {
		Node<E> parent = validate(p);
		if (parent.getLeft()!=null)
			throw new IllegalArgumentException("p already has a left child");
		Node<E> child = createNode(e, parent, null, null);
		parent.setLeft(child);
		size++;
		return child;
	  }

	  /**
	   * Creates a new right child of Position p storing element e and returns its Position.
	   *
	   * @param p   the Position to the right of which the new element is inserted
	   * @param e   the new element
	   * @return the Position of the new element
	   * @throws IllegalArgumentException if p is not a valid Position for this tree.
	   * @throws IllegalArgumentException if p already has a right child
	   */
	  public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException, IllegalAccessException {
		  Node<E> parent = validate(p);
			if (parent.getRight()!=null)
				throw new IllegalArgumentException("p already has a right child");
			Node<E> child = createNode(e, parent, null, null);
			parent.setRight(child);
			size++; 
			return child;
	  }

	  /**
	   * Replaces the element at Position p with element e and returns the replaced element.
	   *
	   * @param p   the relevant Position
	   * @param e   the new element
	   * @return the replaced element
	   * @throws IllegalArgumentException if p is not a valid Position for this tree.
	   */
	  public E set(Position<E> p, E e) throws IllegalArgumentException {
		  Node<E> node = validate(p);
		  E tempE = node.getElement();
		  node.setElement(e);
		  return tempE;
	  }

	  /**
	   * Attaches trees t1 and t2, respectively, as the left and right subtree of the
	   * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
	   *
	   * @param p   a leaf of the tree
	   * @param t1  an independent tree whose structure becomes the left child of p
	   * @param t2  an independent tree whose structure becomes the right child of p
	   * @throws IllegalArgumentException if p is not a valid Position for this tree
	   * @throws IllegalArgumentException if p is not a leaf
	   */
	  public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
		Node<E> node = validate(p);
		if (isInternal(p)) throw new IllegalArgumentException("p must be leaf"); 
		size += t1.size() + t2.size();
		if (!t1.isEmpty()) {
			t1.root.setParent(node);
			node.setLeft(t1.root);
			t1.root = null;
			t1.size = 0;
		}
		if (!t2.isEmpty()) {
			t2.root.setParent(node);
			node.setRight(t2.root);
			t2.root = null;
			t2.size = 0;
		}
	  }

	  /**
	   * Removes the node at Position p and replaces it with its child, if any.
	   *
	   * @param p   the relevant Position
	   * @return element that was removed
	   * @throws IllegalArgumentException if p is not a valid Position for this tree.
	   * @throws IllegalArgumentException if p has two children.
	   */
	  public E remove(Position<E> p) throws IllegalArgumentException {
		  Node<E> node = validate(p);
		  if(numChildren(p)==2)
			  throw new IllegalArgumentException("p has two children");
		  Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight() );
		  if (child!=null)
			  child.setParent(node.getParent());
		  if(node == root)
			  root = child;
		  else {
			  Node<E> parentNode = node.getParent();
			  if(node == parentNode.getLeft())
				  parentNode.setLeft(child);
			  else
				 parentNode.setRight(child);
		  }
		  size--;
		  E tempE = node.getElement();
		  node.setElement(null);
		  node.setLeft(null);
		  node.setRight(null);
		  node.setParent(node);
		  return tempE;		  
	  }
	  
	  public String toString() {
		  StringBuilder sb = new StringBuilder();
		  sb.append("[");
		  for(Position<E> p : positions()) {
			  sb.append(p.getElement());
			  sb.append(", ");
		  }
		  sb.append("]");
		  return sb.toString();
	  }
	
	  public void createLevelOrder(E[] arr) {
			root = createLevelOrderHelper(arr, root, 0);
		}
	  
	  private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i){
		  if (i < arr.length) {
			Node<E> n = createNode(arr[i], p, null, null);
			n.left = createLevelOrderHelper(arr, n.left, 2*i + 1);
			n.right = createLevelOrderHelper(arr, n.right, 2*i + 2);
			++size;
			return n;
		}
		  return p;
	  }
	
	public static void main(String [] args) throws IllegalArgumentException, IllegalAccessException {
		 
		/*
		LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<Integer>();
		  
		  int [] arr = {12, 25, 31, 58, 36, 42, 90, 62, 75};
		  for(int i : arr) {
			  bt.insert(i);
		  }
		  System.out.println("bt: " + bt.size() + " " + bt );
		*/
		
		  LinkedBinaryTree<Integer> bt1 = new LinkedBinaryTree<Integer>();
		  /*
		   *Direct Construction of Tree
		  Position<Integer> temp = bt1.addRoot(12);
		  Position<Integer> p1 = bt1.addLeft(temp, 25);
		  Position<Integer> p2 = bt1.addRight(temp, 31);
		  Position<Integer> p3 =  bt1.addLeft(p1, 58);
		  bt1.addRight(p1, 36);
		  Position<Integer> p5 = bt1.addLeft(p2, 42);
		  bt1.addRight(p2, 90);
		  Position<Integer> p4 = bt1.addLeft(p3, 62);
		  bt1.addRight(p3, 75);
		  */
		  
		  //Level Order Construction
		  Integer [] arr = {12, 25, 31, 58, 36, 42, 90, 62, 75};
		  bt1.createLevelOrder(arr);
		  System.out.println("bt1 inorder: " + bt1.size() + " " + bt1.inorder() );
		  System.out.println("bt1 preorder: " + bt1.size() + " " + bt1.preorder() );
		  System.out.println("bt1 preorder: " + bt1.size() + " " + bt1.postorder() );
		  System.out.println("bt1 height: " + bt1.height(bt1.root()));
		  System.out.println("bt1 depth: " + bt1.depth(bt1.root()));
		//  System.out.println("bt1 depth 62: " + bt1.depth(p4));
		//  System.out.println("bt1 depth 42: " + bt1.depth(p5));
	  }


	
	
	


}
package projectCode20280;

import java.util.Comparator;

/**
 * An implementation of a sorted map using an AVL tree.
 */

public class AVLTreeMap<K, V> extends TreeMap<K, V> {


    protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

    
	/** Constructs an empty map using the natural ordering of keys. */
	public AVLTreeMap() {
		super();
	}

	/**
	 * Constructs an empty map using the given comparator to order keys.
	 * 
	 * @param comp comparator defining the order of keys in the map
	 */
	public AVLTreeMap(Comparator<K> comp) {
		super(comp);
	}

	/** Returns the height of the given tree position. */
	protected int height(Position<Entry<K, V>> p) {
		return tree.getAux(p);
	}

	/**
	 * Recomputes the height of the given position based on its children's heights.
	 */
	protected void recomputeHeight(Position<Entry<K, V>> p) {
		tree.setAux(p, 1 + Math.max(height(left(p)), height(right(p))));

	}

	/** Returns whether a position has balance factor between -1 and 1 inclusive. */
	protected boolean isBalanced(Position<Entry<K, V>> p) {
		return Math.abs(height(left(p))-height(right(p)))<=1;
	}

	/** Returns a child of p with height no smaller than that of the other child. */
	protected Position<Entry<K, V>> tallerChild(Position<Entry<K, V>> p) {
		if(height(left(p))> height(right(p))) return left(p);
		if(height(left(p))< height(right(p))) return right(p);
		if(isRoot(p)) return left(p);
		if(p==left(parent(p))) return left(p);
		else return right(p);
	}

	/**
	 * Utility used to rebalance after an insert or removal operation. This
	 * traverses the path upward from p, performing a trinode restructuring when
	 * imbalance is found, continuing until balance is restored.
	 */
	protected void rebalance(Position<Entry<K, V>> p) {
		int oldHeight, newHeight;
		do {
			oldHeight = height(p);
			if (!isBalanced(p)) {
				p = tree.restructure(tallerChild(tallerChild(p)));
				recomputeHeight(left(p));
				recomputeHeight(right(p));
			}
			recomputeHeight(p);
			newHeight = height(p);
			p = parent(p);
		} while (oldHeight!=newHeight && p!=null);
	}

	/** Overrides the TreeMap rebalancing hook that is called after an insertion. */
	@Override
	protected void rebalanceInsert(Position<Entry<K, V>> p) {
		rebalance(p);
	}

	/** Overrides the TreeMap rebalancing hook that is called after a deletion. */
	@Override
	protected void rebalanceDelete(Position<Entry<K, V>> p) {
		if (!isRoot(p)) {
			rebalance(parent(p));
		}
	}

	/** Ensure that current tree structure is valid AVL (for debug use only). */
	private boolean sanityCheck() {
		for (Position<Entry<K, V>> p : tree.positions()) {
			if (isInternal(p)) {
				if (p.getElement() == null)
					System.out.println("VIOLATION: Internal node has null entry");
				else if (height(p) != 1 + Math.max(height(left(p)), height(right(p)))) {
					System.out.println("VIOLATION: AVL unbalanced node with key " + p.getElement().getKey());
					dump();
					return false;
				}
			}
		}
		return true;
	}

	/*
	public String toBinaryTreeString() {
		BinaryTreePrinter< Entry<K, V> > btp = new BinaryTreePrinter<>( (LinkedBinaryTree<Entry<K, V>>) this.tree);		
		return btp.print();	
	}
	*/
	
	public static void main(String [] args) {
		AVLTreeMap<Integer, Integer> avl = new AVLTreeMap<>();
	//	Integer[] arr = new Integer[] { 44, 17, 88, 8, 32, 65, 97, 28, 54, 82, 93, 21, 29, 76, 80 };
			avl.put(3, 2);
			avl.put(4, 5);
		
			AVLTreeMap<Integer, String> map = new AVLTreeMap<>();
			Integer[] arr = new Integer[] {35,26,15,24,33,4,12,1,23,21,2,5};

			for(Integer i : arr) {
				map.put(i, Integer.toString(i));
			}


		System.out.println("avl: " + map);
		
		System.out.println(avl.remove(3));

		System.out.println("avl: " + avl.entrySet());
	}
}
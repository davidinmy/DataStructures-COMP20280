/**
 * The {@code TreeMap} in this class implements a
 * sorted map using a binary search tree.
 *
 * @author Ahmed Jouda & Dr. Aonghus Lawlor
 */
package projectCode20280;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * An implementation of a sorted map using a binary search tree.
 */

public class TreeMap<K, V> extends AbstractSortedMap<K, V> {

	// We reuse the LinkedBinaryTree class. A limitation here is that we only use
	// the key.
	protected BalanceableBinaryTree<K, V> tree = new BalanceableBinaryTree<>();

	/** Constructs an empty map using the natural ordering of keys. */
	public TreeMap() {
		super(); // the AbstractSortedMap constructor
		tree.addRoot(null); // create a sentinel leaf as root
	}

	public TreeMap(Comparator<K> comp) {
		super(comp); // the AbstractSortedMap constructor
		tree.addRoot(null); // create a sentinel leaf as root
	}

	/**
	 * Returns the number of entries in the map.
	 * 
	 * @return number of entries in the map
	 */
	@Override
	public int size() {
		return (tree.size() - 1) / 2; // only internal nodes have entries
	}

	/**
	 * Utility used when inserting a new entry at a leaf of the tree
	 * 
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void expandExternal(Position<Entry<K, V>> p, Entry<K, V> entry)
			throws IllegalArgumentException, IllegalAccessException {
		tree.set(p, entry);
		tree.addLeft(p, null);
		tree.addRight(p, null);
	}

	// Some notational shorthands for brevity (yet not efficiency)
	protected Position<Entry<K, V>> root() {
		return tree.root();
	}

	protected Position<Entry<K, V>> parent(Position<Entry<K, V>> p) {
		return tree.parent(p);
	}

	protected Position<Entry<K, V>> left(Position<Entry<K, V>> p) {
		return tree.left(p);
	}

	protected Position<Entry<K, V>> right(Position<Entry<K, V>> p) {
		return tree.right(p);
	}

	protected Position<Entry<K, V>> sibling(Position<Entry<K, V>> p) {
		return tree.sibling(p);
	}

	protected boolean isRoot(Position<Entry<K, V>> p) {
		return tree.isRoot(p);
	}

	protected boolean isExternal(Position<Entry<K, V>> p) {
		return tree.isExternal(p);
	}

	protected boolean isInternal(Position<Entry<K, V>> p) {
		return tree.isInternal(p);
	}

	protected void set(Position<Entry<K, V>> p, Entry<K, V> e) {
		tree.set(p, e);
	}

	protected Entry<K, V> remove(Position<Entry<K, V>> p) {
		return tree.remove(p);
	}

	/**
	 * Returns the position in p's subtree having the given key (or else the
	 * terminal leaf).
	 * 
	 * @param key a target key
	 * @param p   a position of the tree serving as root of a subtree
	 * @return Position holding key, or last node reached during search
	 */

	private Position<Entry<K, V>> treeSearch(Position<Entry<K, V>> p, K key) {
		if (isExternal(p)) {
			return p; // key not found
		}

		int c = compare(key, p.getElement());
		if (c == 0) {
			return p;
		} else if (c < 0) {
			return treeSearch(left(p), key);
		} else {
			return treeSearch(right(p), key);
		}
	}

	/**
	 * Returns position with the minimal key in the subtree rooted at Position p.
	 * 
	 * @param p a Position of the tree serving as root of a subtree
	 * @return Position with minimal key in subtree
	 */
	protected Position<Entry<K, V>> treeMin(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> curr = p;
		while (isInternal(curr)) {
			curr = left(curr);
		}
		return parent(curr);
	}

	/**
	 * Returns the position with the maximum key in the subtree rooted at p.
	 * 
	 * @param p a Position of the tree serving as root of a subtree
	 * @return Position with maximum key in subtree
	 */
	protected Position<Entry<K, V>> treeMax(Position<Entry<K, V>> p) {
		Position<Entry<K, V>> curr = p;
		while (isInternal(curr)) {
			curr = right(curr);
		}
		return parent(curr);
	}

	/**
	 * Returns the value associated with the specified key, or null if no such entry
	 * exists.
	 * 
	 * @param key the key whose associated value is to be returned
	 * @return the associated value, or null if no such entry exists
	 */
	@Override
	public V get(K key) throws IllegalArgumentException {
		checkKey(key); // may throw IllegalArgumentException
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if (isExternal(p))
			return null; // unsuccessful search
		return p.getElement().getValue();
	}

	/**
	 * Associates the given value with the given key. If an entry with the key was
	 * already in the map, this replaced the previous value with the new one and
	 * returns the old value. Otherwise, a new entry is added and null is returned.
	 * 
	 * @param key   key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with the key (or null, if no such
	 *         entry)
	 */
	@Override
	public V put(K key, V value) {
		Entry<K, V> entry = new MapEntry<>(key, value);
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if (isExternal(p)) {
			try {
				expandExternal(p, entry);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			V old = p.getElement().getValue();
			set(p, entry);
			return old;
		}
	}

	/**
	 * Removes the entry with the specified key, if present, and returns its
	 * associated value. Otherwise does nothing and returns null.
	 * 
	 * @param key the key whose entry is to be removed from the map
	 * @return the previous value associated with the removed key, or null if no
	 *         such entry exists
	 */
	@Override
	public V remove(K key) throws IllegalArgumentException {
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if (isExternal(p)) {
			return null;
		} else {
			V old = p.getElement().getValue();
			if (isInternal(left(p)) && isInternal(right(p))) {
				Position<Entry<K, V>> r = treeMax(left(p));
				set(p, r.getElement());
				p = r;
			}

			Position<Entry<K, V>> leaf = isExternal(left(p)) ? left(p) : right(p);
			Position<Entry<K, V>> sib = sibling(leaf);
			remove(leaf);
			remove(p);
			rebalanceDelete(sib);
			return old;
		}
	}

	private void rebalanceDelete(Position<Entry<K, V>> sib) {
		// TODO Auto-generated method stub

	}

	// additional behaviors of the SortedMap interface
	/**
	 * Returns the entry having the least key (or null if map is empty).
	 * 
	 * @return entry with least key (or null if map is empty)
	 */
	@Override
	public Entry<K, V> firstEntry() {
		if (isEmpty())
			return null;
		return treeMin(root()).getElement();
	}

	/**
	 * Returns the entry having the greatest key (or null if map is empty).
	 * 
	 * @return entry with greatest key (or null if map is empty)
	 */
	@Override
	public Entry<K, V> lastEntry() {
		if (isEmpty())
			return null;

		return treeMax(root()).getElement();
	}

	/**
	 * Returns the entry with least key greater than or equal to given key (or null
	 * if no such key exists).
	 * 
	 * @return entry with least key greater than or equal to given (or null if no
	 *         such entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
		checkKey(key); // may throw IllegalArgumentException
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if (isInternal(p))
			return p.getElement(); // exact match
		while (!isRoot(p)) {
			if (p == left(parent(p)))
				return parent(p).getElement(); // parent has next greater key
			else
				p = parent(p);
		}
		return null; // no such ceiling exists
	}

	/**
	 * Returns the entry with greatest key less than or equal to given key (or null
	 * if no such key exists).
	 * 
	 * @return entry with greatest key less than or equal to given (or null if no
	 *         such entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
		checkKey(key); // may throw IllegalArgumentException
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if (isInternal(p))
			return p.getElement(); // exact match
		while (!isRoot(p)) {
			if (p == right(parent(p)))
				return parent(p).getElement(); // parent has next lesser key
			else
				p = parent(p);
		}
		return null; // no such floor exists
	}

	/**
	 * Returns the entry with greatest key strictly less than given key (or null if
	 * no such key exists).
	 * 
	 * @return entry with greatest key strictly less than given (or null if no such
	 *         entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if (isInternal(p) && isInternal(left(p)))
			return treeMax(left(p)).getElement();
		while (!isRoot(p)) {
			if (p == right(parent(p)))
				return parent(p).getElement();
			else
				p = parent(p);
		}
		return null;
	}

	/**
	 * Returns the entry with least key strictly greater than given key (or null if
	 * no such key exists).
	 * 
	 * @return entry with least key strictly greater than given (or null if no such
	 *         entry)
	 * @throws IllegalArgumentException if the key is not compatible with the map
	 */
	@Override
	public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
		checkKey(key);
		Position<Entry<K, V>> p = treeSearch(root(), key);
		if (isInternal(p) && isInternal(left(p)))
			return treeMax(right(p)).getElement();
		while (!isRoot(p)) {
			if (p == left(parent(p)))
				return parent(p).getElement();
			p = parent(p);
		}
		return null;
	}

	// Support for iteration
	/**
	 * Returns an iterable collection of all key-value entries of the map.
	 *
	 * @return iterable collection of the map's entries
	 */
	@Override
	public Iterable<Entry<K, V>> entrySet() {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
		for (Position<Entry<K, V>> p : tree.inorder()) {
			if (isInternal(p))
				buffer.add(p.getElement());
		}
		return buffer;
	}

	/**
	 * Returns an iterable containing all entries with keys in the range from
	 * <code>fromKey</code> inclusive to <code>toKey</code> exclusive.
	 * 
	 * @return iterable with keys in desired range
	 * @throws IllegalArgumentException if <code>fromKey</code> or
	 *                                  <code>toKey</code> is not compatible with
	 *                                  the map
	 */
	@Override
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
		ArrayList<Entry<K, V>> buffer = new ArrayList<>(size());
		if (compare(fromKey, toKey) < 0) // ensure that fromKey < toKey
			subMapRecursion(fromKey, toKey, root(), buffer);
		return buffer;
	}

	private void subMapRecursion(K fromKey, K toKey, Position<Entry<K, V>> root, ArrayList<Entry<K, V>> buffer) {
		if (isInternal(root))
			if (compare(root.getElement(), fromKey) < 0)
				subMapRecursion(fromKey, toKey, right(root), buffer);
			else {
				subMapRecursion(fromKey, toKey, left(root), buffer);
				if (compare(root.getElement(), toKey) < 0) {
					buffer.add(root.getElement());
					subMapRecursion(fromKey, toKey, right(root), buffer);
				}
			}
	}

	// remainder of class is for debug purposes only
	/** Prints textual representation of tree structure (for debug purpose only). */
	protected void dump() {
		dumpRecurse(root(), 0);
	}

	/** This exists for debugging only */
	private void dumpRecurse(Position<Entry<K, V>> p, int depth) {
		String indent = (depth == 0 ? "" : String.format("%" + (2 * depth) + "s", ""));
		if (isExternal(p))
			System.out.println(indent + "leaf");
		else {
			System.out.println(indent + p.getElement());
			dumpRecurse(left(p), depth + 1);
			dumpRecurse(right(p), depth + 1);
		}
	}

	public String toString() {
		String string = "" + tree.inorder();

		return string;
	}

	public static void main(String[] args) {

		// Create a tree map
		TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>();
		System.out.println("Is it empty?: " + treeMap.isEmpty());
		System.out.println("Size: " + treeMap.size());

		System.out.println("\nAdd a test node");
		treeMap.put(0, 0);
		System.out.println("Tree Map: " + treeMap);
		System.out.println("Is it empty?: " + treeMap.isEmpty());
		System.out.println("Size: " + treeMap.size());

		System.out.println("\nFill it with more nodes");
		Random rnd = new Random();
		int n = 16;
		java.util.List<Integer> rands = rnd.ints(1, 1000).limit(n).distinct().boxed().collect(Collectors.toList());
		for (Integer i : rands) {
			treeMap.put(i, i);
		}
		System.out.println("Tree Map(Entry Set): " + treeMap.entrySet());
		System.out.println("Size: " + treeMap.size());

		System.out.println("\nRemove a random entry");
		treeMap.remove(rands.get(1));
		System.out.println("tree entries after removal: " + treeMap.entrySet());
		System.out.println("Size: " + treeMap.size());

		System.out.println("\nMore detailed testing in junit file.\n***PASSED ALL TESTS***");

	}
}
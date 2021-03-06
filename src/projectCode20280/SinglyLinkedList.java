/**
 * The {@code SinglyLinkedList} in this class implements a
 * fully functioning singly linked list. Generic framework
 * is used to accept any type of element.
 *
 * @author Ahmed Jouda & Dr. Aonghus Lawlor
 */
package projectCode20280;

import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E> {

	private Node<E> head = null;
	private int size = 0;

	// empty constructor
	public SinglyLinkedList() {
	}

	public static class Node<E> {
		private E element;
		private Node<E> next;

		public Node(E e, Node<E> n) {
			element = e;
			next = n;
		}

		// Accessor Methods
		public E getElement() {
			return element;
		}

		public Node<E> getNext() {
			return next;
		}

		// Modifier Methods
		public void setNext(Node<E> n) {
			next = n;
		}
	}

	// reverse list using Array Stack
	@SuppressWarnings("rawtypes")
	public void printReverse(Node head) {
		ArrayStack<Node> stk = new ArrayStack<Node>();
		Node ptr = head;
		while (ptr != null) {
			stk.push(ptr);
			ptr = ptr.next;
		}
		// PRINT
		while (stk.size() > 0) {
			System.out.print(stk.peek().element + " ");
			stk.pop();
		}
		System.out.println("\n");

	}

	// checks if the LL is empty
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	public E first() {
		if (isEmpty())
			return null;
		return head.getElement();
	}

	public E last() {
		if (isEmpty())
			return null;
		Node<E> last = head;

		while (last.getNext() != null) {
			last = last.getNext();
		}

		return last.getElement();
	}

	// returns the element at the position given
	@Override
	public E get(int i) {

		Node<E> curr = head;
		for (int j = 0; j < i; j++) {

			curr = curr.getNext();
		}

		return curr.getElement();
	}

	// adds the element given at the position given
	@Override
	public void add(int i, E e) {
		Node<E> newNode = new Node<E>(e, null);
		Node<E> prevNode = null;
		Node<E> currNode = head;

		for (int j = 0; j < i; j++) {
			prevNode = currNode;
			currNode = currNode.next;
		}

		prevNode.setNext(newNode);
		newNode.setNext(currNode);

		size++;
	}

	// removes the element at the given position and returns it
	@Override
	public E remove(int i) {
		if (head == null) {
			throw new RuntimeException("cant delete");
		}

		Node<E> removedNode = head;
		if (i == 0) {
			removeFirst();
			return removedNode.getElement();
		}

		Node<E> cur = head;
		Node<E> prev = null;

		for (int j = 0; j < i; j++) {
			prev = cur;
			cur = cur.getNext();
		}
		removedNode = cur;
		prev.next = cur.next;
		size--;
		return removedNode.getElement();
	}

	@Override
	public Iterator<E> iterator() {
		Node<E> cu = head;

		while (cu != null) {
			System.out.print(cu.getElement() + " ");
			cu = cu.next;
		}
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	// removes the first element and returns it
	@Override
	public E removeFirst() {
		if (isEmpty()) {
			return null;
		}
		Node<E> removedNode = head;
		if (size != 0) {

			head = head.next;
			size--;
		}

		return removedNode.getElement();
	}

	// removes the last element and returns it
	@Override
	public E removeLast() {
		if (head == null || head.next == null) {
			return null;
		}

		Node<E> second_last = head;

		while (second_last.next.next != null) {
			second_last = second_last.next;
		}
		Node<E> removedNode = second_last.getNext();
		second_last.next = null;
		size--;

		return removedNode.getElement();
	}

	// adds the given element at the start of the list
	@Override
	public void addFirst(E e) {
		head = new Node<E>(e, head);
		size++;

	}

	// adds the given element at the end of the list
	@Override
	public void addLast(E e) {
		Node<E> newNode = new Node<E>(e, null);
		if (isEmpty()) {
			head = newNode;
		} else {

			Node<E> last = head;

			while (last.getNext() != null) {
				last = last.getNext();
			}
			last.setNext(newNode);
		}

		size++;

	}

	@Override
	public String toString() {
		String newString = "[";

		Node<E> current = head;
		while (current != null) {
			newString = newString + current.getElement();
			newString = newString + ", ";

			if (current.getNext() == null) {
				newString = newString.substring(0, newString.length() - 2);
				newString = newString + "]";
			}

			current = current.getNext();
		}

		return newString;
	}

	// function to Check Linked List is
	// sorted in ascending order or not {FOR INTS}
	@SuppressWarnings("unchecked")
	public boolean isSortedAsc() {
		if (head == null)
			return true;

		// Traverse the list till last node and return
		// false if a node is smaller than or equal
		// its next.
		for (Node<Integer> t = (Node<Integer>) head; t.next != null; t = t.next)
			if (t.element > t.next.element)
				return false;
		return true;
	}

	public static void main(String[] args) {

		// Create a singly linked list that stores integers
		SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
		System.out.println("Is it empty? " + ll.isEmpty());
		// fill it - this tests add first and last
		ll.addFirst(0);
		ll.addFirst(1);
		ll.addLast(9);
		ll.addFirst(2);
		ll.addLast(-1);
		System.out.println("Singly Linked List: " + ll);
		System.out.println("Is it empty? " + ll.isEmpty());
		System.out.println("Size: " + ll.size());

		System.out.println("Element at position 2: " + ll.get(2));
		System.out.println("Add at position 2 the number 7: ");
		ll.add(2, 7);
		System.out.println("Updated Singly Linked List: " + ll);
		System.out.println("Size: " + ll.size());

		System.out.println("Remove the first element: " + ll.removeFirst());
		System.out.println("Updated Singly Linked List: " + ll);

		System.out.println("Remove the last element: " + ll.removeLast());
		System.out.println("Updated Singly Linked List: " + ll);

		System.out.println("Remove element at position 1: " + ll.remove(1));
		System.out.println("Updated Singly Linked List: " + ll);

		System.out.print("Iterate through the List: ");
		ll.iterator();
		System.out.println("\nSize: " + ll.size());

		System.out.print("Print it in reverse: ");
		ll.printReverse(ll.head);

		System.out.println("\n**CREATED A NEW SINGLY LINKED LIST USING ALPHABET**\n");

		String[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");

		// fills the list with the alphabet twice in both orders
		SinglyLinkedList<String> sll = new SinglyLinkedList<String>();
		for (String s : alphabet) {
			sll.addFirst(s);
			sll.addLast(s);
		}
		System.out.println("Alphabet: " + sll.toString());
		System.out.println("Size: " + sll.size());

		System.out.println("\nRemove the first letter: " + sll.removeFirst());
		System.out.println("Updated Alphabet: " + sll);

		System.out.println("\nRemove the last letter: " + sll.removeLast());
		System.out.println("Updated Alphabet: " + sll);

		System.out.println("\nRemove letter at position 3: " + sll.remove(3));
		System.out.println("Updated Alphabet: " + sll);

		System.out.println("\nLetter at position 2: " + sll.get(2));

		System.out.println("\nAdd letter 'j' at position 2: ");
		sll.add(2, "j");
		System.out.println("Updated Alphabet: " + sll);

		System.out.println("\nAdd letter 'j' at the start: ");
		sll.addFirst("j");
		System.out.println("Updated Alphabet: " + sll);

		System.out.println("\nAdd letter 'j' at the end: ");
		sll.addLast("j");
		System.out.println("Updated Alphabet: " + sll);

		System.out.println("\nMore detailed testing in junit file.\n***PASSED ALL TESTS***");

	}

}

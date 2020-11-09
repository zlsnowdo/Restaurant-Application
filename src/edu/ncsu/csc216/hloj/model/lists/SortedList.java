package edu.ncsu.csc216.hloj.model.lists;

import java.util.AbstractList;

import edu.ncsu.csc216.hloj.model.Customer;
import edu.ncsu.csc216.hloj.model.MenuItem;
import edu.ncsu.csc216.hloj.model.Order;
import edu.ncsu.csc216.hloj.model.OrderItem;

/**
 * Class used to create SortedList objects to be used in several other classes
 * within Howl Lot of Java
 * 
 * @author Zachary Snowdon
 * @author Jimmy Gbruoski
 * @param <E> type of object to use generically in class
 */
public class SortedList<E> extends AbstractList<E> {
	/** Tracks the current number of nodes in the list */
	private int size;
	/** Data to be stored in SortedList */
	private ListNode list;

	/**
	 * Inner Class used to create node elements within Sorted List
	 * 
	 * @author Zachary Snowdon
	 */
	private class ListNode {
		/** Information stored in the node */
		private E data;

		/** Reference to another node in a list. */
		private ListNode next;

		/**
		 * Constructor for the ListNode object
		 * 
		 * @param data element to be stored in the node
		 * @param next another ListNode to be linked to
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}

	/**
	 * Constructor for the SortedList object that initializes the list and the size
	 * fields.
	 */
	public SortedList() {
		list = null;
		size = 0;
	}

	/**
	 * Adds a ListNode to the list with specified data. The position is determined
	 * by a sorting algorithm.
	 * 
	 * @param data element to be added to the list
	 * @return boolean whether or not element was successfully added to the list
	 *         (true if successful)
	 */
	public boolean add(E data) {
		if (data == null) {
			throw new IllegalArgumentException();
		}
		ListNode nCurrent = list;
		int index = 0;
		for (int i = 0; i < size(); i++) {
			if (data instanceof Customer) {
				if (1 <= ((Customer) data).compareTo((Customer) nCurrent.data)) {
					index++;
				}
				nCurrent = nCurrent.next;
			} else if (data instanceof MenuItem) {
				if (1 <= ((MenuItem) data).compareTo((MenuItem) nCurrent.data)) {
					index++;
				} else if (0 == ((MenuItem) data).compareTo((MenuItem) nCurrent.data)) {
					index++;
				}
				nCurrent = nCurrent.next;
			} else if (data instanceof OrderItem) {
				if (1 <= ((OrderItem) data).compareTo((OrderItem) nCurrent.data)) {
					index++;
				}
				nCurrent = nCurrent.next;
			} else if (data instanceof Order) {
				if (1 <= ((Order) data).compareTo((Order) nCurrent.data)) {
					index++;
				}
				nCurrent = nCurrent.next;
			} else {
				if (1 <= ((String) data).compareTo((String) nCurrent.data)) {
					index++;
				}
				nCurrent = nCurrent.next;
			}
		}
		if (index == 0) {
			list = new ListNode(data, list);
		} else {
			ListNode current = list;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			current.next = new ListNode(data, current.next);
		}
		size++;
		return true;
	}

	/**
	 * Removes the element from the list at a given index.
	 * 
	 * @param index position in list
	 * @return data of removed element
	 */
	public E remove(int index) {
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		E removedVariable = null;
		ListNode current = list;
		// if index is 0
		if (index == 0) {
			removedVariable = list.data;
			list = list.next;
		} else {
			// iterates through list until one index before
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			// sets removedVariable to the data of the index
			removedVariable = current.next.data;
			// sets the indexed cell to the cell after it
			current.next = current.next.next;
		}
		size--;
		return removedVariable;
	}

	/**
	 * Returns the data of a ListNode at a given index.
	 * 
	 * @param index position in list
	 * @return E element in the data field of the ListNode at the given index
	 */
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = list;
		for (int i = 0; i < this.size(); i++) {
			if (i == index) {
				return current.data;
			}
			current = current.next;
		}
		return null;
	}

	/**
	 * Method which returns number of objects in the SortedList
	 * 
	 * @return size The number of objects in the SortedList
	 */
	@Override
	public int size() {
		return size;
	}

}

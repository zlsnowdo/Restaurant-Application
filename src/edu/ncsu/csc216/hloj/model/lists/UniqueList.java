package edu.ncsu.csc216.hloj.model.lists;

import java.util.AbstractList;

/**
 * Class used to create UniqueList objects to be used in several other classes
 * within Howl Lot of Java
 * 
 * @author Jimmy Gbruoski
 * @author Zachary Snowdon
 * @param <E> type of object to use generically in class
 */
public class UniqueList<E> extends AbstractList<E> {
	/** Current number of elements in the list */
	private int size;
	/** Array to store data elements of the list */
	private E[] list;
	/** Default capacity of the list */
	private static final int INIT_CAPACITY = 10;

	/**
	 * Constructor for UniqueList object that specifies the initial length of the
	 * array for list
	 * 
	 * @param capacity initial length of the array: list
	 * @throws IllegalArgumentException if capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public UniqueList(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException();
		}
		list = (E[]) new Object[capacity];
		size = 0;
	}

	/**
	 * Constructor for UniqueList object that uses the default capacity for the
	 * initial length of the array for list
	 */
	public UniqueList() {
		this(INIT_CAPACITY);
	}

	/**
	 * Adds a data entry to the list and increases the capacity of the list if the
	 * size and capacity are equal. Nothing is added to the list if the list already
	 * contains the data.
	 * 
	 * @param data element to be added to the list
	 * @return boolean whether or not element was successfully added to the list
	 *         (true if successful)
	 * @throws IllegalArgumentException if data already exists in list
	 */
	public boolean add(E data) {
		if (size() > 0) {
			for (int i = 0; i < size(); i++) {
				if (data.equals(list[i])) {
					throw new IllegalArgumentException();
				}
			}
		}
		increaseCapacity();
		list[size] = data;
		size++;
		return true;
	}

	/**
	 * Adds a data entry to the list and increases the capacity of the list if the
	 * size and capacity are equal. This data is inserted into the list at a given
	 * index if the list does not already contain the data
	 * 
	 * @param index position in list to insert data
	 * @param data  element to be added to the list
	 * @throws IllegalArgumentException if data already exists in list
	 */
	@SuppressWarnings("unchecked")
	public void add(int index, E data) {
		if (index == size()) {
			add(data);
		} else {
			validateIndex(index);
			if (size() > 0) {
				for (int i = 0; i < size(); i++) {
					if (data.equals(list[i])) {
						throw new IllegalArgumentException();
					}
				}
			}
			increaseCapacity();
			E[] tempList = (E[]) new Object[list.length];
			int counter = 0;
			for (int i = 0; i < size(); i++) {
				if (counter == index) {
					tempList[counter] = data;
					counter++;
				}
				tempList[counter] = list[i];
				counter++;
			}
			list = tempList;
			size++;
		}
	}

	/**
	 * Removes the element from the list at a given index.
	 * 
	 * @param index position in list
	 * @return data of removed element
	 */
	@SuppressWarnings("unchecked")
	public E remove(int index) {
		validateIndex(index);
		E removed = null;
		E[] newList = (E[]) new Object[list.length];
		int counter = 0;
		for (int i = 0; i < size(); i++) {
			if (i == index) {
				removed = list[i];
			} else {
				newList[counter] = list[i];
				counter++;
			}
		}
		list = newList;
		size--;
		return removed;
	}

	/**
	 * Returns the data in the list a given index.
	 * 
	 * @param index position in list
	 * @return E element in the list at the specified index
	 */
	public E get(int index) {
		validateIndex(index);
		return list[index];
	}

	/**
	 * Gets the current size of the list.
	 * 
	 * @return size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks a proposed value for index to make sure that the value is within the
	 * specified bounds.
	 * 
	 * @param index position in the list attempting to be accessed
	 */
	private void validateIndex(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Increases the length of the array:list to allow for more elements to be
	 * added.
	 */
	@SuppressWarnings("unchecked")
	private void increaseCapacity() {
		if (size == list.length) {
			if (list.length < 1) {
				list = (E[]) new Object[1];
			} else {
				E[] biggerList = (E[]) new Object[list.length * 2];
				for (int i = 0; i < list.length; i++) {
					biggerList[i] = list[i];
				}
				list = biggerList;
			}
		}
	}
}

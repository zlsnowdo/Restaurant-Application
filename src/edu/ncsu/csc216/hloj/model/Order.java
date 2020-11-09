package edu.ncsu.csc216.hloj.model;

import edu.ncsu.csc216.hloj.model.lists.UniqueList;

/**
 * Class which creates Orders which holds OrderItems and MenuItems
 * 
 * @author Zachary Snowdon
 * @author Jimmy Gbruoski
 *
 */
public class Order {
	/** An ArrayList which holds all of the OrderItems for the Order class */
	private UniqueList<OrderItem> items;
	/** A customer object that holds the customer the order is for */
	private Customer customer;
	/** Integer which holds the identifying number for Orders */
	private int number;

	/**
	 * Constructor for Order objects to be placed by Customer objects
	 * 
	 * @param newNumber identifier for the order
	 * @param c         The customer who the order is to be added to
	 * @throws ModelException if orderNumber is less than or equal to 0
	 * meaning it is invalid
	 */
	public Order(int newNumber, Customer c) throws ModelException {
		if (newNumber <= 0) {
			throw new ModelException("Order numbers must be larger than zero");
		} else {
			this.number = newNumber;
			this.customer = c;
			items = new UniqueList<OrderItem>();
		}
	}

	/**
	 * Method which returns the OrderItem index for a certain MenuItem
	 * 
	 * @param c The MenuItem that the OrderItem index if found from
	 * @return int The index of a certain kind of item on the order. Results in -1
	 *         if the item is not on the list
	 */
	private int getOrderItemIndexForMenuItem(MenuItem c) {
		for (int i = 0; i < items.size(); i++) {
			if (c.getType().equals(getItems()[i].getMenuItem().getType())
					&& c.getName().equals(getItems()[i].getMenuItem().getName())) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Method which adds a MenuItem to the order. If item is already on the order,
	 * then its quantity is increased by 1
	 * 
	 * @param c The MenuItem that is to be added to the order
	 */
	public void addMenuItem(MenuItem c) {
		int itemIndex = getOrderItemIndexForMenuItem(c);
		if (itemIndex != -1) {
			try {
				int newQuantity = items.get(itemIndex).getQuantity() + 1;
				items.get(itemIndex).setQuantity(newQuantity);
			} catch (ModelException e) {
				itemIndex = -1;
			}
		} else {
			OrderItem newItem = new OrderItem(c);
			if (items.size() == 0) {
				items.add(newItem);
			} else {
				boolean notAdded = true;
				for (int i = 0; i < items.size(); i++) {
					if (newItem.compareTo(items.get(i)) == -1) {
						items.add(i, newItem);
						notAdded = false;
						break;
					}
				}
				if (notAdded) {
					items.add(newItem);
				}
			}
		}
	}

	/**
	 * Method which removes a MenuItem from the Order
	 * 
	 * @param c The MenuItem that is to be removed from the order
	 */
	public void removeMenuItem(MenuItem c) {
		int itemIndex = getOrderItemIndexForMenuItem(c);
		if (itemIndex != -1) {
			if (items.get(itemIndex).getQuantity() > 1) {
				try {
					items.get(itemIndex).setQuantity(items.get(itemIndex).getQuantity() - 1);
				} catch (ModelException e) {
					itemIndex = -1;
				}
			} else {
				items.remove(itemIndex);
			}
		}
	}

	/**
	 * Method which gets the identifying number for an order
	 * 
	 * @return int The number of MenuItems in an order
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Method that returns which customer the Order is for
	 * 
	 * @return Customer The customer that the order is for
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Method which returns an array of OrderItems in an order
	 * 
	 * @return OrderItem[] The list of OrderItems in an order
	 */
	public OrderItem[] getItems() {
		OrderItem[] result = new OrderItem[items.size()];
		for (int i = 0; i < items.size(); i++) {
			result[i] = items.get(i);
		}
		return result;
	}

	/**
	 * Method which returns the total cost of an order
	 * 
	 * @return double total cost of order
	 */
	public double getTotal() {
		double result = 0.00;
		if (items.size() > 0) {
			for (int i = 0; i < items.size(); i++) {
				double orderItemSum = items.get(i).getMenuItem().getPrice() * items.get(i).getQuantity();
				result += orderItemSum;
			}
		}
		return result;
	}

	/**
	 * Method that produces the String that represents an order
	 * 
	 * @return String The String equivalent of all of the OrderItems in an order
	 */
	public String toString() {
		return "#" + getNumber() + " for " + getCustomer().getFirstName() + " " + getCustomer().getLastName()
				+ " - Total: $" + getTotal();
	}

	/**
	 * Method which compares two Order objects to see if they are the same
	 * 
	 * @param c The Order object that is to be compared to the other Order object
	 * @return int Weather or not two Order objects are the same and what is
	 *         different about them
	 */
	public int compareTo(Order c) {
		if (getNumber() == c.getNumber()) {
			return 0;
		} else if (getNumber() > c.getNumber()) {
			return 1;
		} else {
			return -1;
		}
	}
}

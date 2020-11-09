package edu.ncsu.csc216.hloj.model;

/**
 * Class which creates OrderItems for customers and OrderManager
 * 
 * @author Zachary Snowdon
 * @author Jimmy Gbruoski
 *
 */
public class OrderItem {
	/** item from menu that is added to an order */
	private MenuItem menuItem;
	/** integer of how many of this item a customer ordered */
	private int quantity;

	/**
	 * Constructor for OrderItem which represents a MenuItem with a given quantity.
	 * The initial quantity of the item is 1
	 * 
	 * @param c The MenuItem that is to be placed in the OrderItem object
	 */
	public OrderItem(MenuItem c) {
		menuItem = c;
		quantity = 1;
	}

	/**
	 * Method which returns the number of items in an Order
	 * 
	 * @return int The number of items in the order
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Method which sets the number of items in the OrderItem
	 * 
	 * @param newQuantity The number of items to be set for the OrderItem
	 * @throws ModelException if quantity less than or equal to 0 meaning it is
	 * invalid
	 */
	public void setQuantity(int newQuantity) throws ModelException {
		if (newQuantity <= 0) {
			throw new ModelException("The quantity of an item in an order has to be greater than zero");
		} else {
			this.quantity = newQuantity;
		}
	}

	/**
	 * Method which gets a certain MenuItem from an OrderItem
	 * 
	 * @return MenuItem The MenuItem that was found in the OrderItem
	 */
	public MenuItem getMenuItem() {
		return menuItem;
	}

	/**
	 * Method which compares two OrderItems to see if they are the same
	 * 
	 * @param c The OrderItem that is to be compared with another MenuItem
	 * @return int based on if the items are the same based off of the
	 *         compareTo(MenuItem) method implemented in MenuIt
	 */
	public int compareTo(OrderItem c) {
		if (!menuItem.getType().equals(c.getMenuItem().getType())) {
			for (int i = 0; i < menuItem.getType().length(); i++) {
				if (menuItem.getType().charAt(i) > c.getMenuItem().getType().charAt(i)) {
					return 1;
				} else if (menuItem.getType().charAt(i) < c.getMenuItem().getType().charAt(i)) {
					return -1;
				}
				if (i == c.getMenuItem().getType().length()) {
					return 1;
				}
			}
			return -1;
		} else {
			if (quantity == c.getQuantity()) {
				return 0;
			} else if (quantity > c.getQuantity()) {
				return -1;
			} else {
				return 1;
			}
		}
	}

}

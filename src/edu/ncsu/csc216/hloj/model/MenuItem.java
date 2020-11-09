package edu.ncsu.csc216.hloj.model;

/**
 * Class which creates MenuItems for customers and MenuManager
 * 
 * @author Zachary Snowdon
 *
 */
public class MenuItem {

	/**
	 * String which holds which type of item a certain menu item is
	 */
	private String type;

	/**
	 * String which holds the name of a certain menu item
	 */
	private String name;

	/**
	 * Double which holds the price of a certain menu item
	 */
	private double price;

	/**
	 * Constructor for MenuItem object which holds food items that can be placed
	 * into orders and customers
	 * 
	 * @param type  type of item
	 * @param name  name of item
	 * @param price price of item
	 * @throws ModelException if any of the fields are empty/null
	 */
	public MenuItem(String type, String name, double price) throws ModelException {
		setType(type);
		setName(name);
		setPrice(price);
	}

	/**
	 * Method which return which type of item a specific MenuItem is
	 * 
	 * @return String What type of item the MenuItem is
	 */
	public String getType() {
		return type.trim();
	}

	/**
	 * Method which sets the MenuItem to a new item type
	 * 
	 * @param newType The string which represents what item type the MenuItem is to
	 *                be set to
	 * @throws ModelException if the newType is null or empty
	 */
	public void setType(String newType) throws ModelException {
		if (newType == null || "".equals(newType) || " ".equals(newType)) {
			throw new ModelException("The type of the menu item cannot be empty");
		} else {
			this.type = newType.trim();
		}
	}

	/**
	 * Method which gets the name of a MenuItem
	 * 
	 * @return String The name of the MenuItem
	 */
	public String getName() {
		return name.trim();
	}

	/**
	 * Method which sets the name of a MenuItem
	 * 
	 * @param newName The new name for a MenuItem that is to be set
	 * @throws ModelException if the newName is null or empty
	 */
	public void setName(String newName) throws ModelException {
		if (newName == null || "".equals(newName) || " ".equals(newName)) {
			throw new ModelException("The name of the menu item cannot be empty");
		} else {
			this.name = newName.trim();
		}
	}

	/**
	 * Method which gets the price for a certain MenuItem
	 * 
	 * @return double The price of a certain MenuItem
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Method which sets the price for a certain MenuItem
	 * 
	 * @param newPrice The new price to be set on a MenuItem
	 * @throws ModelException if the price is less than or equal to zero
	 */
	public void setPrice(double newPrice) throws ModelException {
		if (newPrice <= 0) {
			throw new ModelException("The price of the menu item must be greater than zero");
		} else {
			this.price = newPrice;
		}
	}

	/**
	 * Method which uses a MenuItems characteristics to return a string of all of
	 * the characteristics
	 * 
	 * @return String The MenuItem printed as a string
	 */
	public String toString() {
		String output = "(" + type + ") " + name + " - $" + price;
		return output;
	}

	/**
	 * Method which compares two MenuItems to see if they are the same
	 * 
	 * @param c One of the MenuItems that is to be compared to the other
	 * @return Weather or not the MenuItems are the same and what is different
	 */
	public int compareTo(MenuItem c) {
		int returnNumber = 0;
		String newType = c.getType().toLowerCase();
		String otherType = type.toLowerCase();
		if (-1 >= otherType.compareTo(newType)) {
			returnNumber = -1;
		} else if (0 == otherType.compareTo(newType)) {
			returnNumber = 0;
		} else if (1 <= otherType.compareTo(newType)) {
			returnNumber = 1;
		}
		return returnNumber;
	}

}

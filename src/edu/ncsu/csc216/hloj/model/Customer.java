package edu.ncsu.csc216.hloj.model;

/**
 * Class which creates Customer objects which hold orders
 * 
 * @author Zachary Snowdon
 *
 */
public class Customer {

	/**
	 * String which holds the customers first name
	 */
	private String firstName;

	/**
	 * String which holds the customers last name
	 */
	private String lastName;

	/**
	 * String which holds the customers unique id
	 */
	private String id;

	/**
	 * Constructor for Customer objects
	 * 
	 * @param firstName The first name of the customer
	 * @param lastName  The last name of the customer
	 * @param id        The unique id of the customer
	 * @throws ModelException When an invalid first name, last name, or id is constructed
	 */
	public Customer(String firstName, String lastName, String id) throws ModelException {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
	}

	/**
	 * Method which returns the first name of the customer
	 * 
	 * @return String The first name of the customer
	 */
	public String getFirstName() {
		return firstName.trim();
	}

	/**
	 * Method which sets the first name of a customer object
	 * 
	 * @param newFirstName The first name to be set on the customer
	 * @throws ModelException if newFirstName is empty/null
	 */
	public void setFirstName(String newFirstName) throws ModelException {
		if (newFirstName == null || "".equals(newFirstName)) {
			throw new ModelException("The first name of the customer cannot be empty");
		} else {
			this.firstName = newFirstName.trim();
		}
	}

	/**
	 * Method which returns the first name of the customer
	 * 
	 * @return String The first name of the customer
	 */
	public String getLastName() {
		return lastName.trim();
	}

	/**
	 * Method which sets the last name of a customer object
	 * 
	 * @param newLastName The last name to be set on the customer
	 * @throws ModelException if newLastName is empty or null
	 */
	public void setLastName(String newLastName) throws ModelException {
		if (newLastName == null || "".equals(newLastName)) {
			throw new ModelException("The last name of the customer cannot be empty");
		} else {
			this.lastName = newLastName.trim();
		}
	}

	/**
	 * Method which gets the unique Id of a customer
	 * 
	 * @return String The unique Id for a specific customer
	 */
	public String getId() {
		return id.trim();
	}

	/**
	 * Method which sets the unique Id for a customer
	 * 
	 * @param newId The Id that is to be set on the customer
	 * @throws ModelException if newId is empty or null
	 */
	public void setId(String newId) throws ModelException {
		if (newId == null || "".equals(newId)) {
			throw new ModelException("The id of the customer cannot be empty");
		} else {
			this.id = newId.trim();
		}
	}

	/**
	 * Method which converts the customer object to a string to be read
	 * 
	 * @return String The string equivalent of the customer object
	 */
	public String toString() {
		String output = firstName + " " + lastName + " " + "(" + id + ")";
		return output;
	}

	/**
	 * Method which compares two Customers to see if they are the same
	 * 
	 * @param c One of the Customer that is to be compared to the other
	 * @return Weather or not the Customers are the same and what is different
	 */
	public int compareTo(Customer c) {
		int returnNumber = 0;
		String customerFirst = c.getFirstName().toLowerCase().trim();
		String customerLast = c.getLastName().toLowerCase().trim();
		String otherFirst = firstName.toLowerCase();
		String otherLast = lastName.toLowerCase();
		if (-1 >= otherLast.compareTo(customerLast)) {
			returnNumber = -1;
		} else if (0 == otherLast.compareTo(customerLast)) {
			if (-1 >= otherFirst.compareTo(customerFirst)) {
				returnNumber = -1;
			} else if (0 == otherFirst.compareTo(customerFirst)) {
				returnNumber = 0;
			} else if (1 <= otherFirst.compareTo(customerFirst)) {
				returnNumber = 1;
			}
		} else if (1 <= otherLast.compareTo(customerLast)) {
			returnNumber = 1;
		}
		return returnNumber;
	}

}

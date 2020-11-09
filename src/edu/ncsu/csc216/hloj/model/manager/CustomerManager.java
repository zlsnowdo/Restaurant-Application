package edu.ncsu.csc216.hloj.model.manager;

import edu.ncsu.csc216.hloj.model.Customer;
import edu.ncsu.csc216.hloj.model.ModelException;
import edu.ncsu.csc216.hloj.model.lists.SortedList;

/**
 * Class which handles all customers in an CustomerManager
 * 
 * @author Zachary Snowdon
 *
 */
public final class CustomerManager {

	/**
	 * An ArrayList which holds all of the customers for the CustomerManager
	 */
	private SortedList<Customer> customers;

	/**
	 * Singleton instance to show CustomerManager is only used once
	 */
	public static CustomerManager instance = null;

	/**
	 * Constructor for the CustomerManager
	 */
	private CustomerManager() {
		customers = new SortedList<Customer>();
	}

	/**
	 * Method to make sure only one CustomerManager is open at a time
	 * 
	 * @return CustomerManager The CustomerManager that is open at a certain time
	 */
	public static CustomerManager getInstance() {
		if (instance == null) {
			instance = new CustomerManager();
		}
		return instance;
	}

	/**
	 * Method which adds a customer to the CustomerManager
	 * 
	 * @param c The customer to be added
	 * @throws ModelException if customer does not exist
	 */
	public void addCustomer(Customer c) throws ModelException {
		if (c == null) {
			throw new ModelException("Customer does not exist");
		} else {
			String testId = c.getId();
			for (int i = 0; i < customers.size() - 1; i++) {
				Customer testC = customers.get(i);
				String otherId = testC.getId();
				if (otherId.equals(testId)) {
					throw new ModelException("A customer with this id already exists");
				}
			}
			customers.add(c);
		}
	}

	/**
	 * Gets all the customers in the manager and creates an array of Customers
	 * 
	 * @return Customer[] An array of Customers that are in the CustomerManager
	 */
	public Customer[] getCustomers() {
		Customer[] c = new Customer[customers.size()];
		for (int i = 0; i < customers.size(); i++) {
			c[i] = customers.get(i);
		}
		return c;
	}

	/**
	 * Finds a customer in the list using an index and returns that customer
	 * 
	 * @param index The location of the requested customer in the list
	 * @return Customer The customer at the index in the list
	 */
	public Customer getCustomer(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException();
		}
		Customer returnCust = customers.get(index);
		return returnCust;
	}

	/**
	 * Filters through the list of Customer and returns the customer that matches
	 * the string parameter
	 * 
	 * @param id The string input that is supposed to match one of the customers
	 * @return Customer The customer object that matches the String parameter
	 */
	public Customer getCustomer(String id) {
		Customer[] c = getCustomers();
		Customer returnC = null;
		for (int i = 0; i < c.length; i++) {
			Customer tester = c[i];
			if (tester.getId().equals(id)) {
				returnC = tester;
			}
		}
		return returnC;
	}

	/**
	 * Method which removes a customer from the list and manager
	 * 
	 * @param c The customer to be removed from the list and manager
	 * @throws ModelException if customer does not exist
	 * @throws ModelException if customer has an open order
	 */
	public void removeCustomer(Customer c) throws ModelException {
		if (c == null) {
			throw new ModelException("Customer does not exist");
		}
		boolean customerNotFound = true;
		for (int i = 0; i < customers.size(); i++) {
			if (c.compareTo(customers.get(i)) == 0) {
				if (OrderManager.getInstance().getOrdersByCustomer(c).length > 0) {
					throw new ModelException("Cannot remove a customer with open orders");
				}
				customers.remove(i);
				customerNotFound = false;
			}
		}
		if (customerNotFound) {
			throw new ModelException("Customer does not exist.");
		}
	}

	/**
	 * Method which locates a Customer and edits the details about it
	 * 
	 * @param index The location of the Customer to be edited
	 * @param c     The new Customer that is to replace the old one
	 * @throws ModelException if customer does not exist
	 */
	public void editCustomer(int index, Customer c) throws ModelException {
		if (c == null) {
			throw new ModelException("Customer does not exist");
		} else {
			String testId = c.getId();
			for (int i = 0; i < customers.size() - 1; i++) {
				Customer testC = customers.get(i);
				String otherId = testC.getId();
				if (otherId.equals(testId)) {
					throw new ModelException("A customer with this id already exists");
				}
			}
			customers.remove(index);
			customers.add(c);
		}
	}

	/**
	 * Method which removes all customers by creating a new list
	 */
	public void removeAllCustomers() {
		customers = new SortedList<Customer>();
	}
}
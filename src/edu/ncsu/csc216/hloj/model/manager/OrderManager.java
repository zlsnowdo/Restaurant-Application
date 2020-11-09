package edu.ncsu.csc216.hloj.model.manager;

import edu.ncsu.csc216.hloj.model.Customer;
import edu.ncsu.csc216.hloj.model.ModelException;
import edu.ncsu.csc216.hloj.model.Order;
import edu.ncsu.csc216.hloj.model.lists.SortedList;

/**
 * Class which handles all orders in an OrderManager
 * 
 * @author Zachary Snowdon
 * @author Jimmy Gbruoski
 *
 */
public final class OrderManager {
	/** A SortedList which holds all of the order for the OrderManager */
	private SortedList<Order> orders;
	/** Integer which holds the last order number used */
	private int lastOrderNumber;
	/** Integer which represents the max number of orders a customer can have */
	private static final int MAX_ORDERS_PER_CUSTOMER = 3;
	/** Singleton instance to show OrderManager is only used once */
	public static OrderManager instance = null;

	/**
	 * Constructor for the OrderManager that initializes
	 */
	private OrderManager() {
		orders = new SortedList<Order>();
		lastOrderNumber = 0;
	}

	/**
	 * Method to make sure only one OrderManager is open at a time
	 * 
	 * @return OrderManager The OrderManager that is open at a certain time
	 */
	public static OrderManager getInstance() {
		if (instance == null) {
			instance = new OrderManager();
		}
		return instance;
	}

	/**
	 * Method to get the order number of the last order
	 * 
	 * @return orderNumber The order number of the last order
	 */
	public int getLastOrderNumber() {
		return lastOrderNumber;
	}

	/**
	 * Gives an order a unique order number
	 * 
	 * @param orderNumber The orderNumber to be set on the order
	 */
	public void setLastOrderNumber(int orderNumber) {
		lastOrderNumber = orderNumber;
	}

	/**
	 * Imports a customer and creates a new order for them
	 * 
	 * @param c The customer object where the order list is held in
	 * @return Order The order object which is the customers next order
	 * @throws ModelException if the customer already has the maximum amount of
	 *                        available orders.
	 */
	public Order getNextOrder(Customer c) throws ModelException {
		if (getOrdersByCustomer(c).length >= MAX_ORDERS_PER_CUSTOMER) {
			throw new ModelException("A customer cannot have more than 3 open orders");
		}
		setLastOrderNumber(lastOrderNumber + 1);
		return new Order(lastOrderNumber, c);

	}

	/**
	 * Method which places an order for a customer object
	 * 
	 * @param c The order that is to be placed for a customer
	 * @throws ModelException if number of order is greater than the last order
	 *                        number
	 * @throws ModelException if customer on order already has the maximum number of
	 *                        active orders.
	 * @throws ModelException if the order has no items on it.
	 * @throws ModelException if the order has the same order number as another
	 *                        order in the list of active orders
	 */
	public void placeOrder(Order c) throws ModelException {
		if (c.getNumber() > getLastOrderNumber()) {
			throw new ModelException("Order number is invalid");
		}
		if (getOrdersByCustomer(c.getCustomer()).length >= MAX_ORDERS_PER_CUSTOMER) {
			throw new ModelException("A customer cannot have more than 3 open orders");
		}
		if (c.getItems().length == 0) {
			throw new ModelException("Orders can only be placed if they contain at least one item");
		}
		for (int i = 0; i < orders.size(); i++) {
			if (c.getNumber() == orders.get(i).getNumber()) {
				throw new ModelException("An order with this number already exists");
			}
		}
		orders.add(c);
	}

	/**
	 * Runs through the OrderManager and places all orders in a list that is
	 * returned
	 * 
	 * @return Order[] A list of order objects
	 */
	public Order[] getOrders() {
		Order[] result = new Order[orders.size()];
		for (int i = 0; i < orders.size(); i++) {
			result[i] = orders.get(i);
		}
		return result;
	}

	/**
	 * Returns a list of orders for a specific customer
	 * 
	 * @param c A customer that the list of orders is requested for
	 * @return Order[] A list of orders a specific customer has placed
	 */
	public Order[] getOrdersByCustomer(Customer c) {
		SortedList<Order> customerOrders = new SortedList<Order>();
		for (int i = 0; i < orders.size(); i++) {
			if (c.getId().equals(orders.get(i).getCustomer().getId())) {
				customerOrders.add(orders.get(i));
			}
		}
		Order[] result = new Order[customerOrders.size()];
		for (int i = 0; i < customerOrders.size(); i++) {
			result[i] = customerOrders.get(i);
		}
		return result;
	}

	/**
	 * Returns an order in a list with a specific requested location
	 * 
	 * @param index The specific location of an order in a list
	 * @return Order The order in the specific spot in the list
	 */
	public Order getOrder(int index) {
		return orders.get(index);
	}

	/**
	 * Method which cancels and removes a specific order
	 * 
	 * @param c The order object that is to be cancelled
	 * @throws ModelException if order does not exist
	 */
	public void cancelOrder(Order c) throws ModelException {
		boolean orderNotFound = true;
		for (int i = 0; i < orders.size(); i++) {
			if (c.compareTo(orders.get(i)) == 0) {
				orders.remove(i);
				orderNotFound = false;
			}
		}
		if (orderNotFound) {
			throw new ModelException("Order does not exist");
		}
	}

	/**
	 * Method which fulfills and pushed through an order
	 * 
	 * @param c The order that is to be fulfilled
	 * @throws ModelException if order does not exist
	 * @throws ModelException if the order's associated customer has a pending order
	 *                        that was made before this one.
	 */
	public void fulfillOrder(Order c) throws ModelException {
		for (int i = 0; i < orders.size(); i++) {
			if (c.getNumber() > orders.get(i).getNumber() && c.getCustomer().equals(orders.get(i).getCustomer())) {
				throw new ModelException("Orders must be fulfilled in the order in which they were placed");
			}
		}
		boolean orderNotFound = true;
		for (int i = 0; i < orders.size(); i++) {
			if (c.compareTo(orders.get(i)) == 0) {
				orders.remove(i);
				orderNotFound = false;
			}
		}
		if (orderNotFound) {
			throw new ModelException("Order does not exist");
		}
	}

	/**
	 * Method which removes all orders from the OrderManager by creating a new list
	 * in its place
	 */
	public void removeAllOrders() {
		orders = new SortedList<Order>();
		lastOrderNumber = 0;
	}
}
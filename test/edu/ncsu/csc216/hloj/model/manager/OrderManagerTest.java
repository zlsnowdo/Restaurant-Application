package edu.ncsu.csc216.hloj.model.manager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.hloj.model.Customer;
import edu.ncsu.csc216.hloj.model.MenuItem;
import edu.ncsu.csc216.hloj.model.ModelException;
import edu.ncsu.csc216.hloj.model.Order;

/**
 * Test cases for OrderManager
 * 
 * @author Jimmy Gbruoski
 *
 */
public class OrderManagerTest {
	/** Valid customer A */
	Customer customerA = null;
	/** Valid customer B */
	Customer customerB = null;
	/** Valid customer C */
	Customer customerC = null;
	/** Instance of OrderManager */
	OrderManager testManager = null;

	/**
	 * Sets up constants for Customers and Orders to be used in test methods.
	 */
	@Before
	public void setUp() {
		testManager = OrderManager.getInstance();
		try {
			customerA = new Customer("John", "Smith", "js1");
			customerB = new Customer("John", "Doe", "jd2");
			customerC = new Customer("Gretta", "Smith", "gs4");
		} catch (ModelException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Method to generate valid order with an Order number and a customer.
	 * 
	 * @param customer person Order belongs to
	 * @return Order the valid generated Order
	 * @throws ModelException if error occurs when making the order
	 */
	private Order createValidOrder(Customer customer) throws ModelException {
		Order testOrder = null;
		testOrder = testManager.getNextOrder(customer);
		MenuItem testItemA = new MenuItem("Coffee", "Latte", 2.5);
		MenuItem testItemB = new MenuItem("Pastry", "Doughnut", 1.25);
		testOrder.addMenuItem(testItemA);
		testOrder.addMenuItem(testItemB);
		testOrder.addMenuItem(testItemA);
		testOrder.addMenuItem(testItemA);
		return testOrder;
	}

	/**
	 * Test method for getLastOrderNumber and setLastOrderNumber(int).
	 */
	@Test
	public void testLastOrderNumber() {
		testManager.removeAllOrders();
		assertEquals(0, testManager.getLastOrderNumber());
		testManager.setLastOrderNumber(24);
		assertEquals(24, testManager.getLastOrderNumber());
	}

	/**
	 * Test method for placeOrder(Order).
	 */
	@Test
	public void testPlaceOrder() {
		testManager.removeAllOrders();
		try {
			// Test placing one order
			testManager.placeOrder(createValidOrder(customerA));
			assertEquals(1, testManager.getOrders().length);
			assertEquals(customerA.getId(), testManager.getOrders()[0].getCustomer().getId());
			assertEquals(1, testManager.getOrdersByCustomer(customerA)[0].getNumber());
			assertEquals(customerA.getId(), testManager.getOrder(0).getCustomer().getId());
			// Test placing orders for different customers
			testManager.placeOrder(createValidOrder(customerB));
			testManager.placeOrder(createValidOrder(customerC));
			assertEquals(3, testManager.getOrders().length);
			assertEquals(customerA.getId(), testManager.getOrders()[0].getCustomer().getId());
			assertEquals(1, testManager.getOrdersByCustomer(customerA)[0].getNumber());
			assertEquals(customerB.getId(), testManager.getOrders()[1].getCustomer().getId());
			assertEquals(2, testManager.getOrdersByCustomer(customerB)[0].getNumber());
			assertEquals(customerC.getId(), testManager.getOrders()[2].getCustomer().getId());
			assertEquals(3, testManager.getOrdersByCustomer(customerC)[0].getNumber());
			// Test placing more orders for the same customer
			testManager.placeOrder(createValidOrder(customerA));
			testManager.placeOrder(createValidOrder(customerA));
			assertEquals(5, testManager.getOrders().length);
			assertEquals(customerA.getId(), testManager.getOrders()[0].getCustomer().getId());
			assertEquals(1, testManager.getOrdersByCustomer(customerA)[0].getNumber());
			assertEquals(customerB.getId(), testManager.getOrders()[1].getCustomer().getId());
			assertEquals(2, testManager.getOrdersByCustomer(customerB)[0].getNumber());
			assertEquals(customerC.getId(), testManager.getOrders()[2].getCustomer().getId());
			assertEquals(3, testManager.getOrdersByCustomer(customerC)[0].getNumber());
			assertEquals(customerA.getId(), testManager.getOrders()[3].getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrders()[4].getCustomer().getId());
		} catch (ModelException e) {
			fail();
		}
		// Test placing order with number that already exists
		try {
			testManager.setLastOrderNumber(0);
			testManager.placeOrder(createValidOrder(customerB));
			fail();
		} catch (ModelException e) {
			assertEquals(5, testManager.getOrders().length);
		}
		// Test placing more than 3 orders for a customer
		try {
			testManager.setLastOrderNumber(5);
			testManager.placeOrder(createValidOrder(customerA));
			fail();
		} catch (ModelException e) {
			assertEquals(5, testManager.getOrders().length);
		}
	}

	/**
	 * Test method for cancelOrder(Order).
	 */
	@Test
	public void testCancelOrder() {
		testManager.removeAllOrders();
		Order orderA = null;
		Order orderB = null;
		Order orderC = null;
		Order orderD = null;
		Order orderE = null;
		try {
			orderA = createValidOrder(customerA);
			orderB = createValidOrder(customerB);
			orderC = createValidOrder(customerC);
			orderD = createValidOrder(customerA);
			orderE = createValidOrder(customerA);
			testManager.placeOrder(orderA);
			testManager.placeOrder(orderB);
			testManager.placeOrder(orderC);
			testManager.placeOrder(orderD);
			testManager.placeOrder(orderE);
			assertEquals(5, testManager.getOrders().length);
			assertEquals(customerA.getId(), testManager.getOrder(0).getCustomer().getId());
			assertEquals(customerB.getId(), testManager.getOrder(1).getCustomer().getId());
			assertEquals(customerC.getId(), testManager.getOrder(2).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(3).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(4).getCustomer().getId());
			assertEquals(3, testManager.getOrdersByCustomer(customerA).length);
			assertEquals(1, testManager.getOrdersByCustomer(customerB).length);
			assertEquals(1, testManager.getOrdersByCustomer(customerC).length);
			// Test cancel one order
			testManager.cancelOrder(orderB);
			assertEquals(4, testManager.getOrders().length);
			assertEquals(customerA.getId(), testManager.getOrder(0).getCustomer().getId());
			assertEquals(customerC.getId(), testManager.getOrder(1).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(2).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(3).getCustomer().getId());
			assertEquals(3, testManager.getOrdersByCustomer(customerA).length);
			assertEquals(0, testManager.getOrdersByCustomer(customerB).length);
			assertEquals(1, testManager.getOrdersByCustomer(customerC).length);
			// Test cancel middle order for customer
			testManager.cancelOrder(orderD);
			assertEquals(3, testManager.getOrders().length);
			assertEquals(customerA.getId(), testManager.getOrder(0).getCustomer().getId());
			assertEquals(customerC.getId(), testManager.getOrder(1).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(2).getCustomer().getId());
			assertEquals(2, testManager.getOrdersByCustomer(customerA).length);
			assertEquals(0, testManager.getOrdersByCustomer(customerB).length);
			assertEquals(1, testManager.getOrdersByCustomer(customerC).length);
		} catch (ModelException e) {
			fail();
		}
		// Test cancel order that is no longer open
		try {
			testManager.cancelOrder(orderB);
			fail();
		} catch (ModelException e) {
			assertEquals(3, testManager.getOrders().length);
			assertEquals(customerA.getId(), testManager.getOrder(0).getCustomer().getId());
			assertEquals(customerC.getId(), testManager.getOrder(1).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(2).getCustomer().getId());
			assertEquals(2, testManager.getOrdersByCustomer(customerA).length);
			assertEquals(0, testManager.getOrdersByCustomer(customerB).length);
			assertEquals(1, testManager.getOrdersByCustomer(customerC).length);
		}
	}

	/**
	 * Test method for fulfillOrder(Order).
	 */
	@Test
	public void testFulfillOrder() {
		testManager.removeAllOrders();
		Order orderA = null;
		Order orderB = null;
		Order orderC = null;
		Order orderD = null;
		Order orderE = null;
		try {
			orderA = createValidOrder(customerA);
			orderB = createValidOrder(customerB);
			orderC = createValidOrder(customerC);
			orderD = createValidOrder(customerA);
			orderE = createValidOrder(customerA);
			testManager.placeOrder(orderA);
			testManager.placeOrder(orderB);
			testManager.placeOrder(orderC);
			testManager.placeOrder(orderD);
			testManager.placeOrder(orderE);
			assertEquals(5, testManager.getOrders().length);
			assertEquals(customerA.getId(), testManager.getOrder(0).getCustomer().getId());
			assertEquals(customerB.getId(), testManager.getOrder(1).getCustomer().getId());
			assertEquals(customerC.getId(), testManager.getOrder(2).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(3).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(4).getCustomer().getId());
			assertEquals(3, testManager.getOrdersByCustomer(customerA).length);
			assertEquals(1, testManager.getOrdersByCustomer(customerB).length);
			assertEquals(1, testManager.getOrdersByCustomer(customerC).length);
			// Test fulfill one order
			testManager.fulfillOrder(orderB);
			assertEquals(4, testManager.getOrders().length);
			assertEquals(customerA.getId(), testManager.getOrder(0).getCustomer().getId());
			assertEquals(customerC.getId(), testManager.getOrder(1).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(2).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(3).getCustomer().getId());
			assertEquals(3, testManager.getOrdersByCustomer(customerA).length);
			assertEquals(0, testManager.getOrdersByCustomer(customerB).length);
			assertEquals(1, testManager.getOrdersByCustomer(customerC).length);
			// Test fulfill first order for customer
			testManager.fulfillOrder(orderA);
			assertEquals(3, testManager.getOrders().length);
			assertEquals(customerC.getId(), testManager.getOrder(0).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(1).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(2).getCustomer().getId());
			assertEquals(2, testManager.getOrdersByCustomer(customerA).length);
			assertEquals(0, testManager.getOrdersByCustomer(customerB).length);
			assertEquals(1, testManager.getOrdersByCustomer(customerC).length);
		} catch (ModelException e) {
			fail();
		}
		// Test fulfill last order for customer
		try {
			testManager.fulfillOrder(orderE);
			fail();
		} catch (ModelException e) {
			assertEquals(3, testManager.getOrders().length);
			assertEquals(customerC.getId(), testManager.getOrder(0).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(1).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(2).getCustomer().getId());
			assertEquals(2, testManager.getOrdersByCustomer(customerA).length);
			assertEquals(0, testManager.getOrdersByCustomer(customerB).length);
			assertEquals(1, testManager.getOrdersByCustomer(customerC).length);
		}
		// Test fulfill order that is no longer open
		try {
			testManager.fulfillOrder(orderB);
			fail();
		} catch (ModelException e) {
			assertEquals(3, testManager.getOrders().length);
			assertEquals(customerC.getId(), testManager.getOrder(0).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(1).getCustomer().getId());
			assertEquals(customerA.getId(), testManager.getOrder(2).getCustomer().getId());
			assertEquals(2, testManager.getOrdersByCustomer(customerA).length);
			assertEquals(0, testManager.getOrdersByCustomer(customerB).length);
			assertEquals(1, testManager.getOrdersByCustomer(customerC).length);
		}
	}
}

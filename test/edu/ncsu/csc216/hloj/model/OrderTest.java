package edu.ncsu.csc216.hloj.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test cases for Order
 * 
 * @author Jimmy Gbruoski
 *
 */
public class OrderTest {
	/**
	 * Test method for Order(int, Customer).
	 */
	@Test
	public void testOrder() {
		Customer validCustomer = null;
		try {
			validCustomer = new Customer("John", "Smith", "js1");
		} catch (ModelException e) {
			fail();
		}
		Order testOrder = null;
		try {
			testOrder = new Order(1, validCustomer);
			assertEquals(1, testOrder.getNumber());
			assertEquals("js1", testOrder.getCustomer().getId());
			assertEquals(0, testOrder.getItems().length);
			assertEquals(0.0, testOrder.getTotal(), 0.001);
		} catch (ModelException e) {
			fail();
		}
		try {
			testOrder = new Order(0, validCustomer);
			fail();
		} catch (ModelException e) {
			assertEquals("Order numbers must be larger than zero", e.getMessage());
		}
	}

	/**
	 * Test method for addMenuItem(MenuItem).
	 */
	@Test
	public void testAddMenuItem() {
		Customer validCustomer = null;
		try {
			validCustomer = new Customer("John", "Smith", "js1");
		} catch (ModelException e) {
			fail();
		}
		Order testOrder = null;
		try {
			testOrder = new Order(1, validCustomer);
			assertEquals(1, testOrder.getNumber());
			assertEquals("js1", testOrder.getCustomer().getId());
			assertEquals(0, testOrder.getItems().length);
			assertEquals(0.0, testOrder.getTotal(), 0.001);
			MenuItem testItemA = new MenuItem("Coffee", "Latte", 2.5);
			MenuItem testItemB = new MenuItem("Pastry", "Doughnut", 1.25);
			// Add one item
			testOrder.addMenuItem(testItemA);
			assertEquals(1, testOrder.getItems().length);
			assertEquals(2.5, testOrder.getTotal(), 0.001);
			assertEquals(1, testOrder.getItems().length);
			// Add unique item
			testOrder.addMenuItem(testItemB);
			assertEquals(2, testOrder.getItems().length);
			assertEquals(3.75, testOrder.getTotal(), 0.001);
			// Add identical items
			testOrder.addMenuItem(testItemA);
			testOrder.addMenuItem(testItemA);
			testOrder.addMenuItem(testItemB);
			assertEquals(2, testOrder.getItems().length);
			assertEquals(10.0, testOrder.getTotal(), 0.001);
		} catch (ModelException e) {
			fail();
		}
	}

	/**
	 * Test method for
	 * {@link edu.ncsu.csc216.hloj.model.Order#removeMenuItem(edu.ncsu.csc216.hloj.model.MenuItem)}.
	 */
	@Test
	public void testRemoveMenuItem() {
		Customer validCustomer = null;
		try {
			validCustomer = new Customer("John", "Smith", "js1");
		} catch (ModelException e) {
			fail();
		}
		Order testOrder = null;
		try {
			testOrder = new Order(1, validCustomer);
			assertEquals(1, testOrder.getNumber());
			assertEquals("js1", testOrder.getCustomer().getId());
			assertEquals(0, testOrder.getItems().length);
			assertEquals(0.0, testOrder.getTotal(), 0.001);
			MenuItem testItemA = new MenuItem("Coffee", "Latte", 2.5);
			MenuItem testItemB = new MenuItem("Pastry", "Doughnut", 1.25);
			testOrder.addMenuItem(testItemA);
			testOrder.addMenuItem(testItemB);
			testOrder.addMenuItem(testItemA);
			testOrder.addMenuItem(testItemA);
			assertEquals(2, testOrder.getItems().length);
			assertEquals(8.75, testOrder.getTotal(), 0.001);
			// Remove item with quantity > 1
			testOrder.removeMenuItem(testItemA);
			assertEquals(2, testOrder.getItems().length);
			assertEquals(6.25, testOrder.getTotal(), 0.001);
			// Remove item with quantity == 1
			testOrder.removeMenuItem(testItemB);
			assertEquals(1, testOrder.getItems().length);
			assertEquals(5.0, testOrder.getTotal(), 0.001);
			// Remove item not on list
			testOrder.removeMenuItem(testItemB);
			assertEquals(1, testOrder.getItems().length);
			assertEquals(5.0, testOrder.getTotal(), 0.001);
		} catch (ModelException e) {
			fail();
		}
	}

}

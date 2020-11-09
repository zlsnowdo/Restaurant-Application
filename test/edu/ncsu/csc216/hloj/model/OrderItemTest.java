package edu.ncsu.csc216.hloj.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test cases for OrderItem
 * 
 * @author Jimmy Gbruoski
 *
 */
public class OrderItemTest {
	/**
	 * Test method for MenuItem().
	 */
	@Test
	public void testOrderItem() {
		MenuItem testMenu = null;
		try {
			testMenu = new MenuItem("Coffee", "Mocha", 2.5);
		} catch (ModelException e) {
			fail();
		}
		OrderItem testOrder = new OrderItem(testMenu);
		assertEquals(0, testOrder.getMenuItem().compareTo(testMenu));
		assertEquals(1, testOrder.getQuantity());
	}

	/**
	 * Test method for setQuantity(int).
	 */
	@Test
	public void testSetQuantity() {
		MenuItem testMenu = null;
		try {
			testMenu = new MenuItem("Coffee", "Mocha", 2.5);
		} catch (ModelException e) {
			fail();
		}
		OrderItem testOrder = new OrderItem(testMenu);
		try {
			testOrder.setQuantity(900);
			assertEquals(900, testOrder.getQuantity());
		} catch (ModelException e) {
			fail();
		}
		try {
			testOrder.setQuantity(0);
			fail();
		} catch (ModelException e) {
			assertEquals(900, testOrder.getQuantity());
			assertEquals("The quantity of an item in an order has to be greater than zero", e.getMessage());
		}
	}

	/**
	 * Test method for compareTo(OrderItem).
	 */
	@Test
	public void testCompareTo() {
		MenuItem testMenuA = null;
		MenuItem testMenuB = null;
		OrderItem testOrderA = null;
		OrderItem testOrderB = null;
		OrderItem testOrderC = null;
		OrderItem testOrderD = null;
		try {
			testMenuA = new MenuItem("Coffee", "Mocha", 2.5);
			testMenuB = new MenuItem("Pastry", "Bagel", 1.5);
			testOrderA = new OrderItem(testMenuA);
			testOrderA.setQuantity(2);
			testOrderB = new OrderItem(testMenuA);
			testOrderB.setQuantity(2);
			testOrderC = new OrderItem(testMenuA);
			testOrderC.setQuantity(4);
			testOrderD = new OrderItem(testMenuB);
			testOrderD.setQuantity(2);
			// Tests for comparing
			assertEquals(0, testOrderA.compareTo(testOrderB));
			assertEquals(0, testOrderB.compareTo(testOrderA));
			assertEquals(1, testOrderA.compareTo(testOrderC));
			assertEquals(-1, testOrderC.compareTo(testOrderA));
			assertEquals(-1, testOrderA.compareTo(testOrderD));
			assertEquals(1, testOrderD.compareTo(testOrderA));
		} catch (ModelException e) {
			fail();
		}
	}
}

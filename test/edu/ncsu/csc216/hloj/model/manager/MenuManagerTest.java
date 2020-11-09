package edu.ncsu.csc216.hloj.model.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.ncsu.csc216.hloj.model.MenuItem;
import edu.ncsu.csc216.hloj.model.ModelException;

/**
 * Test cases for MenuManager
 * 
 * @author Zachary Snowdon
 * @author Jimmy Gbruoski
 */
public class MenuManagerTest {
	/** Instance of CustomerManager to use in tests */
	MenuManager c = MenuManager.getInstance();

	/**
	 * Tests the CustomerManager constructor that creates a CustomerManager
	 */
	@Test
	public void testCreateMenuManager() {
		c.removeAllMenuItems();
		c = MenuManager.getInstance();
		MenuItem[] c1 = c.getMenuItems();
		assertEquals(0, c1.length);
	}

	/**
	 * Tests the addMenuItem method in MenuManager
	 */
	@Test
	public void testAddMenuItem() {
		c.removeAllMenuItems();
		try {
			MenuItem d = new MenuItem("Coffee", "Latte", 2.5);
			c.addMenuItem(d);
			MenuItem[] list = c.getMenuItems();
			assertEquals(1, list.length);
			MenuItem d1 = new MenuItem("Tea", "Green Tea", 2.5);
			c.addMenuItem(d1);
			list = c.getMenuItems();
			assertEquals(2, list.length);
			assertEquals(d, list[0]);
			MenuItem d2 = new MenuItem("Pastries", "Bagel", 2.5);
			c.addMenuItem(d2);
			list = c.getMenuItems();
			assertEquals(3, list.length);
			assertEquals(d, list[0]);
			assertEquals(d2, list[1]);
			assertEquals(d1, list[2]);
		} catch (ModelException e) {
			fail();
		}

	}

	/**
	 * Method that tests the GetMenuItem by index method in MenuManager
	 */
	@Test
	public void testGetMenuItem() {
		c.removeAllMenuItems();
		try {
			MenuItem d = new MenuItem("Coffee", "Latte", 2.5);
			c.addMenuItem(d);
			MenuItem d1 = new MenuItem("Tea", "Green Tea", 2.5);
			c.addMenuItem(d1);
			MenuItem d2 = new MenuItem("Pastries", "Bagel", 2.5);
			c.addMenuItem(d2);
			assertEquals(d, c.getMenuItem(0));
			assertEquals(d2, c.getMenuItem(1));
			assertEquals(d1, c.getMenuItem(2));
		} catch (ModelException e) {
			fail();
		}
	}

	/**
	 * Method which tests the remove functionality in MenuManager
	 */
	@Test
	public void testRemoveMenuItem() {
		c.removeAllMenuItems();
		OrderManager.getInstance().removeAllOrders();
		try {
			MenuItem d = new MenuItem("Coffee", "Latte", 2.5);
			c.addMenuItem(d);
			MenuItem d1 = new MenuItem("Tea", "Green Tea", 2.5);
			c.addMenuItem(d1);
			MenuItem d2 = new MenuItem("Pastries", "Bagel", 2.5);
			c.addMenuItem(d2);
			c.removeMenuItem(1);
			assertEquals(d, c.getMenuItem(0));
			assertEquals(d1, c.getMenuItem(1));
			c.removeMenuItem(0);
			assertEquals(d1, c.getMenuItem(0));
			c.addMenuItem(d);
			c.removeAllMenuItems();
			MenuItem[] t = c.getMenuItems();
			assertEquals(0, t.length);
		} catch (ModelException e) {
			fail();
		}
	}

	/**
	 * Method which tests the editMenuItem feature in MenuManager
	 */
	@Test
	public void testEditMenuItem() {
		c.removeAllMenuItems();
		try {
			MenuItem d = new MenuItem("Coffee", "Latte", 2.5);
			c.addMenuItem(d);
			MenuItem d1 = new MenuItem("Tea", "Green Tea", 2.5);
			c.addMenuItem(d1);
			MenuItem d2 = new MenuItem("Pastries", "Bagel", 2.5);
			c.addMenuItem(d2);
			MenuItem d3 = new MenuItem("Tea", "Hot tea", 2.5);
			c.editMenuItem(1, d3);
		} catch (ModelException e) {
			fail();
		}
		
		c.removeAllMenuItems();
		try {
			MenuItem d = new MenuItem("Coffee", "Latte", 2.5);
			c.addMenuItem(d);
			d = null;
			c.editMenuItem(0, d);
			fail();
		} catch (ModelException e) {
			e.getMessage();
		}
	}

	/**
	 * Method that tests throwing correct errors when necessary
	 */
	@Test
	public void testInvalidParameters() {
		c.removeAllMenuItems();
		try {
			MenuItem d = new MenuItem("Coffee", "Latte", 2.5);
			c.addMenuItem(d);
			c.getMenuItem(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			c.removeAllMenuItems();
		} catch (ModelException e) {
			e.getMessage();
		}
	}

}

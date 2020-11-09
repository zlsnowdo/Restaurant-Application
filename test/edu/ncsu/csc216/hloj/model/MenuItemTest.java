/**
 * 
 */
package edu.ncsu.csc216.hloj.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test cases for MenuItem
 * 
 * @author Zachary Snowdon
 * @author Jimmy Gbruoski
 *
 */
public class MenuItemTest {

	/**
	 * Test case for creating a MenuItem object with the MenuItem() constructor
	 */
	@Test
	public void testCreateMenuItem() {
		try {
			MenuItem c = new MenuItem("Coffee", "Capuccino", 2.5);
			assertEquals("Coffee", c.getType());
			assertEquals("Capuccino", c.getName());
			assertEquals(2.5, c.getPrice(), 2.5);
		} catch (ModelException e) {
			fail();
		}
	}

	/**
	 * Test case for the 3 set methods in Customer.java
	 */
	@Test
	public void testSetters() {
		try {
			MenuItem c = new MenuItem("Coffee", "Capuccino", 2.5);
			c.setType("Pastries");
			c.setName("Bagel");
			c.setPrice(3.0);
			assertEquals("Pastries", c.getType());
			assertEquals("Bagel", c.getName());
			assertEquals(3.0, c.getPrice(), 3.0);
		} catch (ModelException e) {
			fail();
		}
	}

	/**
	 * Test case for invalid parameters for the setters
	 */
	@Test
	public void testInvalidSetter() {
		try {
			MenuItem c = new MenuItem("Coffee", "Capuccino", 2.5);
			c.setType("");
			fail();
		} catch (ModelException e) {
			e.getMessage();
		}

		try {
			MenuItem c = new MenuItem("Coffee", "Capuccino", 2.5);
			c.setName(null);
			fail();
		} catch (ModelException e) {
			e.getMessage();
		}

		try {
			MenuItem c = new MenuItem("Coffee", "Capuccino", 2.5);
			c.setPrice(-1);
			fail();
		} catch (ModelException e) {
			e.getMessage();
		}

	}

	/**
	 * Test case for the toString method in MenuItem
	 */
	@Test
	public void testToString() {
		try {
			MenuItem c = new MenuItem("Coffee", "Capuccino", 2.5);
			assertEquals("(Coffee) Capuccino - $2.5", c.toString());
		} catch (ModelException e) {
			fail();
		}
	}

	/**
	 * Test case for the compareTo method in MenuItem
	 */
	@Test
	public void testCompareTo() {
		try {
			MenuItem c = new MenuItem("Coffee", "Capuccino", 2.5);
			MenuItem tester = new MenuItem("Pastries", "Latte", 2.5);
			assertEquals(-1, c.compareTo(tester));
			assertEquals(1, tester.compareTo(c));
			tester = new MenuItem("Coffee", "Latte", 2.5);
			assertEquals(0, tester.compareTo(c));
			assertEquals(0, c.compareTo(tester));
			tester = new MenuItem("coffee", "Capuccino", 2.5);
			assertEquals(0, c.compareTo(tester));
		} catch (ModelException e) {
			fail();
		}

	}

}

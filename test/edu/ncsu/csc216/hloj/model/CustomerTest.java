package edu.ncsu.csc216.hloj.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test cases for Customer
 * 
 * @author Zachary Snowdon
 * @author Jimmy Gbruoski
 */
public class CustomerTest {

	/**
	 * Test case for creating a customer object with the Customer() constructor
	 */
	@Test
	public void testCreateCustomer() {
		try {
			Customer c = new Customer("Zachary", "Snowdon", "R5");
			assertEquals("Zachary", c.getFirstName());
			assertEquals("Snowdon", c.getLastName());
			assertEquals("R5", c.getId());
		} catch (ModelException e) {
			fail();
		}

		try {
			@SuppressWarnings("unused")
			Customer c = new Customer("Zachary", null, "R5");
			fail();
		} catch (ModelException e) {
			e.getMessage();
		}
	}

	/**
	 * Test case for the 3 set methods in Customer.java
	 */
	@Test
	public void testSetters() {
		try {
			Customer c = new Customer("Zachary", "Snowdon", "R5");
			c.setFirstName("Jimmy");
			c.setLastName("Gbruoski");
			c.setId("R6");
			assertEquals("Jimmy", c.getFirstName());
			assertEquals("Gbruoski", c.getLastName());
			assertEquals("R6", c.getId());
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
			Customer c = new Customer("Zachary", "Snowdon", "R5");
			c.setFirstName("");
			fail();
		} catch (ModelException e) {
			e.getMessage();
		}

		try {
			Customer c = new Customer("Zachary", "Snowdon", "R5");
			c.setLastName(null);
			fail();
		} catch (ModelException e) {
			e.getMessage();
		}

		try {
			Customer c = new Customer("Zachary", "Snowdon", "R5");
			c.setId("");
			fail();
		} catch (ModelException e) {
			e.getMessage();
		}
	}

	/**
	 * Test case for the toString method in Customer
	 */
	@Test
	public void testToString() {
		Customer c;
		try {
			c = new Customer("Zachary", "Snowdon", "R5");
			assertEquals("Zachary Snowdon (R5)", c.toString());
		} catch (ModelException e) {
			fail();
		}
	}

	/**
	 * Test case for the compareTo method in Customer
	 */
	@Test
	public void testCompareTo() {
		try {
			Customer c = new Customer("Zachary", "Snowdon", "R5");
			Customer tester = new Customer("Jimmy", "Gbruoski", "R6");
			assertEquals(1, c.compareTo(tester));
			assertEquals(-1, tester.compareTo(c));
			tester = new Customer("Zachary", "Snowdon", "R6");
			assertEquals(0, c.compareTo(tester));
			tester.setFirstName("Nick");
			assertEquals(-1, tester.compareTo(c));
			assertEquals(1, c.compareTo(tester));
			tester = new Customer("zachary", "snowdon", "R7");
			assertEquals(0, c.compareTo(tester));
		} catch (ModelException e) {
			fail();
		}
	}

}

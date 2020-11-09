package edu.ncsu.csc216.hloj.model.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.ncsu.csc216.hloj.model.Customer;
import edu.ncsu.csc216.hloj.model.ModelException;

/**
 * Test cases for CustomerManager
 * 
 * @author Zachary Snowdon
 * @author Jimmy Gbruoski
 *
 */
public class CustomerManagerTest {
	/** Instance of CustomerManager to be used for testing */
	CustomerManager c = CustomerManager.getInstance();

	/**
	 * Tests the CustomerManager constructor that creates a CustomerManager
	 */
	@Test
	public void testCreateCustomerManager() {
		c.removeAllCustomers();
		Customer[] c1 = c.getCustomers();
		assertEquals(0, c1.length);
	}

	/**
	 * Tests the add() functionality in that adds a Customer to the Customers list
	 * in CustomerManager
	 */
	@Test
	public void testAddCustomer() {
		c.removeAllCustomers();
		try {
			Customer c1 = new Customer("Zachary", "Snowdon", "F5");
			c.addCustomer(c1);
			Customer[] customers = c.getCustomers();
			assertEquals(1, customers.length);
			c1 = new Customer("Jimmy", "Gbruoski", "F6");
			c.addCustomer(c1);
			customers = c.getCustomers();
			assertEquals(2, customers.length);
		} catch (ModelException e) {
			fail();
		}
		c.removeAllCustomers();
		try {
			Customer c1 = null;
			c.addCustomer(c1);
			fail();
		} catch (ModelException e) {
			c.removeAllCustomers();
		}

	}

	/**
	 * Method which tests the getCustomer() method in CustomerManager
	 */
	@Test
	public void testGetCustomer() {
		c.removeAllCustomers();
		try {
			Customer c1 = new Customer("Jimmy", "Gbruoski", "F6");
			c.addCustomer(c1);
			assertEquals(c1, c.getCustomer(0));
			Customer c2 = new Customer("Zachary", "Snowdon", "F7");
			c.addCustomer(c2);
			assertEquals(c1, c.getCustomer(0));
			assertEquals(c2, c.getCustomer(1));
			Customer c3 = new Customer("Nick", "Harris", "F8");
			c.addCustomer(c3);
			assertEquals(c1, c.getCustomer(0));
			assertEquals(c3, c.getCustomer(1));
			assertEquals(c2, c.getCustomer(2));
		} catch (ModelException e) {
			fail();
		}
	}

	/**
	 * Method which tests the getCustomer(String id) in CustomerManager
	 */
	@Test
	public void testGetCustomerById() {
		c.removeAllCustomers();
		try {
			Customer c1 = new Customer("Jimmy", "Gbruoski", "F6");
			c.addCustomer(c1);
			Customer c2 = new Customer("Zachary", "Snowdon", "F7");
			c.addCustomer(c2);
			Customer c3 = new Customer("Nick", "Harris", "F8");
			c.addCustomer(c3);
			Customer results = c.getCustomer("F7");
			assertEquals(results, c2);
			results = c.getCustomer("F6");
			assertEquals(results, c1);
			results = c.getCustomer("F8");
			assertEquals(results, c3);
		} catch (ModelException e) {
			fail();
		}
	}

	/**
	 * Method which tests the removeCustomer() method in CustomerManager
	 */
	@Test
	public void testRemoveCustomer() {
		c.removeAllCustomers();
		try {
			Customer c1 = new Customer("Jimmy", "Gbruoski", "F6");
			c.addCustomer(c1);
			Customer c2 = new Customer("Zachary", "Snowdon", "F7");
			c.addCustomer(c2);
			Customer c3 = new Customer("Nick", "Harris", "F8");
			c.addCustomer(c3);
			c.removeCustomer(c2);
			Customer[] customers = c.getCustomers();
			assertEquals(2, customers.length);
			assertEquals(c1, c.getCustomer(0));
			assertEquals(c3, c.getCustomer(1));
			c.addCustomer(c2);
			customers = c.getCustomers();
			c.removeAllCustomers();
			customers = c.getCustomers();
			assertEquals(0, customers.length);
		} catch (ModelException e) {
			fail();
		}

	}

	/**
	 * Method that tests the edit customer method
	 */
	@Test
	public void testEditCustomer() {
		c.removeAllCustomers();
		try {
			Customer c1 = new Customer("Jimmy", "Gbruoski", "F6");
			c.addCustomer(c1);
			Customer c2 = new Customer("Zachary", "Snowdon", "F7");
			c.addCustomer(c2);
			Customer c3 = new Customer("Nick", "Harris", "F8");
			c.addCustomer(c3);
			c3 = new Customer("Jackson", "Fall", "F9");
			c.editCustomer(2, c3);
			assertEquals(c3, c.getCustomer(0));
		} catch (ModelException e) {
			fail();
		}
	}

}

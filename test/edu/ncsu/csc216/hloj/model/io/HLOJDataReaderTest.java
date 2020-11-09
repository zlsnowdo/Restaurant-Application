package edu.ncsu.csc216.hloj.model.io;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.fail;

import org.junit.Test;

import edu.ncsu.csc216.hloj.model.Customer;
import edu.ncsu.csc216.hloj.model.MenuItem;
import edu.ncsu.csc216.hloj.model.ModelException;
import edu.ncsu.csc216.hloj.model.Order;
import edu.ncsu.csc216.hloj.model.manager.CustomerManager;
import edu.ncsu.csc216.hloj.model.manager.MenuManager;
import edu.ncsu.csc216.hloj.model.manager.OrderManager;

/**
 * Test cases for HLOJDataReader
 * 
 * @author Zachary Snowdon
 *
 */
public class HLOJDataReaderTest {

	/**
	 * Test method for readData(String) with a valid file
	 */
	@Test
	public void testReadData() {
		try {
			HLOJDataReader.readData("test-files/data1.txt");
			CustomerManager c = CustomerManager.getInstance();
			OrderManager o = OrderManager.getInstance();
			MenuManager m = MenuManager.getInstance();
			Customer[] cList = c.getCustomers();
			Customer c1 = new Customer("Sarah", "Heckman", "sesmith5");
			Customer c2 = new Customer("James", "Tetterton", "jctetter");
			Customer c3 = new Customer("Ignacio X.", "Dominguez", "ignacioxd");
			assertEquals(3, cList.length);
			assertEquals(0, c3.compareTo(cList[0]));
			assertEquals(0, c1.compareTo(cList[1]));
			assertEquals(0, c2.compareTo(cList[2]));
			MenuItem[] mList = m.getMenuItems();
			assertEquals(3, mList.length);
			MenuItem m1 = new MenuItem("Tea", "Chai Latte", 3);
			assertEquals(0, m1.compareTo(m.getMenuItem(2)));
			m1 = new MenuItem("Coffee", "Large Coffee", 2.5);
			assertEquals(0, m1.compareTo(m.getMenuItem(0)));
			m1 = new MenuItem("Pastries", "Donut", 2.0);
			assertEquals(0, m1.compareTo(m.getMenuItem(1)));
			Order[] oList = o.getOrders();
			assertEquals(3, oList.length);

			c.removeAllCustomers();
			m.removeAllMenuItems();
			o.removeAllOrders();
			o = null;
		} catch (ModelException e) {
			e.getMessage();
		}

		try {
			HLOJDataReader.readData("test-files/data10.txt");
			CustomerManager c = CustomerManager.getInstance();
			OrderManager o = OrderManager.getInstance();
			MenuManager m = MenuManager.getInstance();
			Customer[] cList = c.getCustomers();
			Customer c1 = new Customer("Sarah", "Heckman", "sesmith5");
			Customer c2 = new Customer("James", "Tetterton", "jctetter");
			assertEquals(2, cList.length);
			assertEquals(0, c1.compareTo(cList[0]));
			assertEquals(0, c2.compareTo(cList[1]));
			MenuItem[] mList = m.getMenuItems();
			assertEquals(3, mList.length);
			MenuItem m1 = new MenuItem("Tea", "Chai Latte", 3);
			assertEquals(0, m1.compareTo(m.getMenuItem(2)));
			m1 = new MenuItem("Coffee", "Large Coffee", 2.5);
			assertEquals(0, m1.compareTo(m.getMenuItem(0)));
			m1 = new MenuItem("Pastries", "Donut", 2.0);
			assertEquals(0, m1.compareTo(m.getMenuItem(1)));
			Order[] oList = o.getOrders();
			assertEquals(2, oList.length);

			c.removeAllCustomers();
			m.removeAllMenuItems();
			o.removeAllOrders();
		} catch (ModelException e) {
			e.getMessage();
		}

	}

	/**
	 * Tests an invalid input file in the HLOJDataReader
	 * 
	 * @throws ModelException for when a ModelException is caught
	 */
	@Test
	public void testInvalidFile() throws ModelException {
		try {
			HLOJDataReader.readData("test-files/data4.txt");
			fail();
		} catch (IllegalArgumentException e) {
			CustomerManager c = CustomerManager.getInstance();
			MenuManager m = MenuManager.getInstance();
			OrderManager o = OrderManager.getInstance();
			c.removeAllCustomers();
			m.removeAllMenuItems();
			o.removeAllOrders();
		}

	}

}

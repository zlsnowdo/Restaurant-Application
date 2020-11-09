package edu.ncsu.csc216.hloj.model.io;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.hloj.model.Customer;
import edu.ncsu.csc216.hloj.model.MenuItem;
import edu.ncsu.csc216.hloj.model.ModelException;
import edu.ncsu.csc216.hloj.model.Order;
import edu.ncsu.csc216.hloj.model.manager.CustomerManager;
import edu.ncsu.csc216.hloj.model.manager.MenuManager;
import edu.ncsu.csc216.hloj.model.manager.OrderManager;

/**
 * Test cases for HLOJDataWriter
 * 
 * @author Jimmy Gbruoski
 * @author Zachary Snowdon
 *
 */
public class HLOJDataWriterTest {

	/**
	 * Test method for writeData(String).
	 */
	@Test
	public void testWriteData() {
		CustomerManager c = CustomerManager.getInstance();
		MenuManager m = MenuManager.getInstance();
		OrderManager o = OrderManager.getInstance();
		c.removeAllCustomers();
		m.removeAllMenuItems();
		o.removeAllOrders();
		try {
			c.addCustomer(new Customer("John", "Smith", "js1"));
			c.addCustomer(new Customer("John", "Doe", "jd4"));
			m.addMenuItem(new MenuItem("Coffee", "Latte", 2.5));
			m.addMenuItem(new MenuItem("Pastry", "Doughnut", 1.25));
			Order orderA = o.getNextOrder(new Customer("John", "Smith", "js1"));
			orderA.addMenuItem(new MenuItem("Coffee", "Latte", 2.5));
			orderA.addMenuItem(new MenuItem("Pastry", "Doughnut", 1.25));
			orderA.addMenuItem(new MenuItem("Coffee", "Latte", 2.5));
			Order orderB = o.getNextOrder(new Customer("John", "Doe", "jd4"));
			orderB.addMenuItem(new MenuItem("Coffee", "Latte", 2.5));
			orderB.addMenuItem(new MenuItem("Pastry", "Doughnut", 1.25));
			orderB.addMenuItem(new MenuItem("Coffee", "Latte", 2.5));
			orderB.addMenuItem(new MenuItem("Coffee", "Latte", 2.5));
			o.placeOrder(orderA);
			o.placeOrder(orderB);
		} catch (ModelException e) {
			assertEquals(2, o.getLastOrderNumber());
		}
		HLOJDataWriter.writeData("test-files/testWrittenFile.txt");
	}

}

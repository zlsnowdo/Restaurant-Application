package edu.ncsu.csc216.hloj.model.io;

import java.io.IOException;
import java.io.PrintStream;

import edu.ncsu.csc216.hloj.model.manager.CustomerManager;
import edu.ncsu.csc216.hloj.model.manager.MenuManager;
import edu.ncsu.csc216.hloj.model.manager.OrderManager;

/**
 * Class used to write data from Howl of Java to a text file to be loaded again
 * at a different time/instance
 * 
 * @author Jimmy Gbruoski
 * 
 */
public class HLOJDataWriter {
	/**
	 * Exports system data from Howl Lot of Java to a text file with a given name.
	 * The last order number is displayed in the first line, then customers are
	 * displayed with '#' symbols on their lines, then menu items are displayed with
	 * '*' symbols on their lines, then orders are displayed with '-' symbols on
	 * their lines.
	 * 
	 * @param fileName name of file to be written to or created
	 */
	public static void writeData(String fileName) {
		// Instances of the manager classes to use
		CustomerManager c = CustomerManager.getInstance();
		MenuManager m = MenuManager.getInstance();
		OrderManager o = OrderManager.getInstance();
		// Initialize PrintStream to write file
		PrintStream fileWriter;
		try {
			fileWriter = new PrintStream(fileName);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file.");
		}

		fileWriter.println(String.valueOf(o.getLastOrderNumber()));
		// Print each customer
		for (int i = 0; i < c.getCustomers().length; i++) {
			fileWriter.println("# " + c.getCustomer(i).getFirstName() + "," + c.getCustomer(i).getLastName() + ","
					+ c.getCustomer(i).getId());
		}
		// Print each menu item
		for (int i = 0; i < m.getMenuItems().length; i++) {
			fileWriter.println("* MI" + String.valueOf(i) + "," + m.getMenuItem(i).getType() + ","
					+ m.getMenuItem(i).getName() + "," + String.valueOf(m.getMenuItem(i).getPrice()));
		}
		// Print each (open) order
		for (int i = 0; i < o.getOrders().length; i++) {
			String orderItems = "";
			// Add each item of the order
			for (int j = 0; j < o.getOrder(i).getItems().length; j++) {
				// Add item multiple times according to its quantity
				for (int k = 0; k < o.getOrder(i).getItems()[j].getQuantity(); k++) {
					orderItems = orderItems + ",MI";
					int itemIndex = -1;
					// Find index of item in menu
					for (int x = 0; x < m.getMenuItems().length; x++) {
						if (o.getOrder(i).getItems()[j].getMenuItem().getType().equals(m.getMenuItem(x).getType())
								&& o.getOrder(i).getItems()[j].getMenuItem().getName()
										.equals(m.getMenuItem(x).getName())) {
							itemIndex = x;
							break;
						}
					}
					orderItems = orderItems + String.valueOf(itemIndex);
				}
			}
			fileWriter.println("- " + String.valueOf(o.getOrder(i).getNumber()) + ","
					+ o.getOrder(i).getCustomer().getId() + orderItems);
		}
		fileWriter.close();
	}
}

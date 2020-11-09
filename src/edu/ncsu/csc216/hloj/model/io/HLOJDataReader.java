package edu.ncsu.csc216.hloj.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc216.hloj.model.Customer;
import edu.ncsu.csc216.hloj.model.MenuItem;
import edu.ncsu.csc216.hloj.model.ModelException;
import edu.ncsu.csc216.hloj.model.Order;
import edu.ncsu.csc216.hloj.model.lists.UniqueList;
import edu.ncsu.csc216.hloj.model.manager.CustomerManager;
import edu.ncsu.csc216.hloj.model.manager.MenuManager;
import edu.ncsu.csc216.hloj.model.manager.OrderManager;

/**
 * Class to read saved data for Howl Lot of Java from a formatted text file
 * 
 * @author Zachary Snowdon
 * 
 */
public class HLOJDataReader {

	/**
	 * One of the parallel lists that holds all of the menuitemIDs
	 */
	private static UniqueList<String> menuitemIDs = new UniqueList<String>(10);

	/**
	 * One of the parallel lists that holds all of the MenuItems used
	 */
	public static UniqueList<MenuItem> menuitems = new UniqueList<MenuItem>(10);

	/**
	 * Imports data for Howl Lot of Java from a text file with a given name.
	 * 
	 * @param fileName name of file to read from
	 */
	public static void readData(String fileName) {
		menuitemIDs = new UniqueList<String>(10);
		menuitems = new UniqueList<MenuItem>(10);
		Scanner fileReader;
		CustomerManager c = CustomerManager.getInstance();
		MenuManager m = MenuManager.getInstance();
		OrderManager o = OrderManager.getInstance();
		Customer c1;
		MenuItem m1;
		try {
			fileReader = new Scanner(new FileInputStream(fileName));
			String readTest = null;
			int firstLineRead = 0;
			while (fileReader.hasNextLine()) {
				if (firstLineRead == 0) {
					readTest = fileReader.nextLine();
					firstLineRead++;
				} else if (firstLineRead != 0) {
					String addLine = fileReader.nextLine().trim();
					String individ = addLine.substring(0, 1);
					if ("*".equals(individ) || "-".equals(individ) || "#".equals(individ)) {
						readTest = readTest + "\n" + addLine;
					}
				}
			}
			fileReader.close();
			fileReader = new Scanner(readTest.trim());
			int lastOrder = 0;
			if (fileReader.hasNextInt()) {
				lastOrder = fileReader.nextInt();
			} else {
				c.removeAllCustomers();
				m.removeAllMenuItems();
				o.removeAllOrders();
				throw new IllegalArgumentException("Unable to load file");
			}
			o.setLastOrderNumber(lastOrder);
			String fullFile = null;
			fileReader.nextLine();
			while (fileReader.hasNextLine()) {
				String addLine = fileReader.nextLine();
				if (fullFile == null) {
					fullFile = addLine;
				} else if (fullFile != null) {
					fullFile = fullFile + "\n" + addLine;
				}
			}
			Scanner projectRead = new Scanner(fullFile);
			boolean gotCustomers = false;
			String newLine = null;
			while (!gotCustomers) {
				newLine = projectRead.nextLine();
				String oneChar = newLine.substring(0, 1);
				if ("#".equals(oneChar)) {
					String cust = newLine.substring(2, newLine.length());
					Scanner readCust = new Scanner(cust);
					readCust.useDelimiter(",");
					String fName = null;
					String lName = null;
					String id = null;
					boolean addCust = true;
					if (readCust.hasNext()) {
						fName = readCust.next().trim();
						if (fName.length() == 0) {
							addCust = false;
						}
					} else {
						addCust = false;
					}
					if (readCust.hasNext()) {
						lName = readCust.next().trim();
						if (lName.length() == 0) {
							addCust = false;
						}
					} else {
						addCust = false;
					}
					if (readCust.hasNext()) {
						id = readCust.next().trim();
						if (id.length() == 0) {
							addCust = false;
						}
					} else {
						addCust = false;
					}
					if (readCust.hasNext()) {
						addCust = false;
					}
					if (addCust) {
						c1 = new Customer(fName, lName, id);
						c.addCustomer(c1);
					}
					readCust.close();
				} else {
					gotCustomers = true;
				}
			}

			boolean gotMenu = false;
			int lineOne = 0;
			String useLine = null;
			while (!gotMenu) {
				if (lineOne == 0) {
					useLine = newLine;
					lineOne++;
				} else if (lineOne != 0) {
					useLine = projectRead.nextLine();
				}
				String oneChar = useLine.substring(0, 1);
				if ("*".equals(oneChar)) {
					useLine = useLine.substring(2, useLine.length()).trim();
					Scanner readMen = new Scanner(useLine);
					readMen.useDelimiter(",");
					String uniqueId = null;
					String type = null;
					String name = null;
					String price = null;
					boolean addMen = true;
					if (readMen.hasNext()) {
						uniqueId = readMen.next().trim();
					} else {
						addMen = false;
					}
					if (readMen.hasNext()) {
						type = readMen.next().trim();
					} else {
						addMen = false;
					}
					if (readMen.hasNext()) {
						name = readMen.next().trim();
					} else {
						addMen = false;
					}
					if (readMen.hasNext()) {
						price = readMen.next().trim();
					} else {
						addMen = false;
					}
					if (readMen.hasNext()) {
						addMen = false;
					}
					if (addMen) {
						boolean secondAdd = true;
						boolean thirdAdd = true;
						try {
							menuitemIDs.add(uniqueId);
						} catch (IllegalArgumentException e) {
							secondAdd = false;
						}
						if (secondAdd) {
							double newP = 0;
							try {
								newP = Double.parseDouble(price);
							} catch (NumberFormatException e) {
								thirdAdd = false;
							}
							if (thirdAdd) {
								m1 = new MenuItem(type, name, newP);
								m.addMenuItem(m1);
								menuitems.add(m1);
							}
						}
					}
					readMen.close();
				} else {
					gotMenu = true;
				}
			}

			int lineOn = 0;
			boolean gotOrder = false;
			String useOne = null;
			Order o1;
			int firstOrder = 0;
			while (!gotOrder) {
				if (lineOn == 0) {
					useOne = useLine;
					lineOn++;
				} else if (lineOn != 0 && projectRead.hasNext()) {
					useOne = projectRead.nextLine();
				} else {
					gotOrder = true;
				}
				String oneChar = useOne.substring(0, 1);
				if ("-".equals(oneChar)) {
					Scanner readOrder = new Scanner(useOne.substring(2, useOne.length()).trim());
					readOrder.useDelimiter(",");
					boolean addOrder = true;
					String orderNumber = null;
					String custId = null;
					String firstMen = null;
					if (readOrder.hasNextInt()) {
						orderNumber = readOrder.next().trim();
						int testNum = Integer.parseInt(orderNumber);
						if (firstOrder == 0) {
							if (testNum != o.getLastOrderNumber()) {
								addOrder = false;
							}
							firstOrder++;
						}
					} else {
						addOrder = false;
					}
					if (readOrder.hasNext()) {
						custId = readOrder.next().trim();
					} else {
						addOrder = false;
					}
					if (readOrder.hasNext()) {
						firstMen = readOrder.next().trim();
						if (addOrder) {
							int oNumber = Integer.parseInt(orderNumber);
							c1 = c.getCustomer(custId);
							if (c1 != null) {
								o1 = new Order(oNumber, c1);
								boolean rightOrd = false;
								boolean placeOrd = true;
								for (int i = 0; i < menuitemIDs.size(); i++) {
									String testID = menuitemIDs.get(i);
									if (testID.equals(firstMen)) {
										MenuItem toAdd = null;
										try {
											toAdd = menuitems.get(i);
										} catch (IndexOutOfBoundsException e) {
											placeOrd = false;
										}
										o1.addMenuItem(toAdd);
										rightOrd = true;
									}
								}
								if (!rightOrd) {
									placeOrd = false;
								}
								while (readOrder.hasNext()) {
									rightOrd = false;
									String itemId = readOrder.next().trim();
									for (int i = 0; i < menuitemIDs.size(); i++) {
										String testID = menuitemIDs.get(i);
										if (testID.equals(itemId)) {
											MenuItem toAdd = null;
											try {
												toAdd = menuitems.get(i);
												o1.addMenuItem(toAdd);
											} catch (IndexOutOfBoundsException e) {
												placeOrd = false;
											} catch (NullPointerException e) {
												placeOrd = false;
											}
											rightOrd = true;
										}
									}
									if (!rightOrd) {
										placeOrd = false;
									}
								}
								if (placeOrd) {
									o.placeOrder(o1);
								}
							} else {
								addOrder = false;
							}

						}
					} else {
						addOrder = false;
					}
				} else {
					gotOrder = true;
				}
			}

			projectRead.close();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File " + fileName + " does not exist");
		} catch (ModelException e) {
			e.getMessage();
		}

	}

}
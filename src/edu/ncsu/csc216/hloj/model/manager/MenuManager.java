package edu.ncsu.csc216.hloj.model.manager;

import edu.ncsu.csc216.hloj.model.MenuItem;
import edu.ncsu.csc216.hloj.model.ModelException;
import edu.ncsu.csc216.hloj.model.lists.SortedList;

/**
 * Class which manages all of the items on the menu
 * 
 * @author Zachary Snowdon
 * @author Jimmy Gbruoski
 */
public final class MenuManager {

	/**
	 * Singleton instance to show MenuManager is only used once
	 */
	public static MenuManager instance = null;

	/**
	 * An ArrayList of MenuItems that is the available menu for customers
	 */
	private SortedList<MenuItem> menu;

	/**
	 * Constructor for the MenuManager
	 */
	private MenuManager() {
		menu = new SortedList<MenuItem>();
	}

	/**
	 * Method to make sure only one MenuManager is open at a time
	 * 
	 * @return MenuManager The MenuManager that is open at a certain time
	 */
	public static MenuManager getInstance() {
		if (instance == null) {
			instance = new MenuManager();
		}
		return instance;
	}

	/**
	 * Method that adds a menu item to the menu manager
	 * 
	 * @param c The menu item that is to be added to the MenuManager
	 */
	public void addMenuItem(MenuItem c) {
		menu.add(c);
	}

	/**
	 * Method which returns an array of MenuItems in the MenuManager
	 * 
	 * @return MenuItem[] An array of MenuItems in the MenuManager
	 */
	public MenuItem[] getMenuItems() {
		MenuItem[] menuList = new MenuItem[menu.size()];
		for (int i = 0; i < menu.size(); i++) {
			menuList[i] = menu.get(i);
		}
		return menuList;
	}

	/**
	 * Method which returns a MenuItem at a specific location in an array
	 * 
	 * @param index The location of the MenuItem requested
	 * @return MenuItem The MenuItem at the index in the list
	 */
	public MenuItem getMenuItem(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException();
		}
		MenuItem returnCust = menu.get(index);
		return returnCust;
	}

	/**
	 * Method which removes a MenuItem at a specific location in a list
	 * 
	 * @param index The location of the MenuItem to be removed
	 * @throws ModelException if the item is a part of an open order
	 */
	public void removeMenuItem(int index) throws ModelException {
		if (index < 0) {
			throw new ModelException("Cannot delete a menu item that is part of an open order");
		}
		for (int i = 0; i < OrderManager.getInstance().getOrders().length; i++) {
			for (int j = 0; j < OrderManager.getInstance().getOrders()[i].getItems().length; j++) {
				if (OrderManager.getInstance().getOrders()[i].getItems()[j].getMenuItem().getType()
						.equals(menu.get(index).getType())
						&& OrderManager.getInstance().getOrders()[i].getItems()[j].getMenuItem().getName()
								.equals(menu.get(index).getName())) {
					throw new ModelException("Cannot delete a menu item that is part of an open order");
				}
			}
		}
		menu.remove(index);
	}

	/**
	 * Method which locates a MenuItem and edits the details about it
	 * 
	 * @param index The location of the MenuItem to be edited
	 * @param c     The new MenuItem that is to replace the old one
	 * @throws ModelException if Menu Item does not exist
	 */
	public void editMenuItem(int index, MenuItem c) throws ModelException {
		if (c == null) {
			throw new ModelException("Menu Item does not exist");
		} else {
			menu.remove(index);
			menu.add(c);
		}
	}

	/**
	 * Method which removes all MenuItems by creating a new list
	 */
	public void removeAllMenuItems() {
		menu = new SortedList<MenuItem>();
	}
}
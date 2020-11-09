package edu.ncsu.csc216.hloj.model.lists;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.hloj.model.Customer;
import edu.ncsu.csc216.hloj.model.MenuItem;
import edu.ncsu.csc216.hloj.model.ModelException;

/**
 * Test cases for SortedList
 * 
 * @author Zachary Snowdon
 *
 */
public class SortedListTest {

	/**
	 * Test method for SortedList()
	 */
	@Test
	public void testSortedList() {
		SortedList<String> lists = new SortedList<String>();
		assertEquals(0, lists.size());
	}

	/**
	 * Test method for add method in SortedList
	 */
	@Test
	public void testAddCustomer() {
		try {
			SortedList<Customer> lists = new SortedList<Customer>();
			Customer zach = new Customer("Zachary", "Snowdon", "R5");
			lists.add(zach);
			assertEquals(1, lists.size());
			assertEquals(zach, lists.get(0));
			Customer connor = new Customer("Connor", "Brady", "R6");
			lists.add(connor);
			assertEquals(2, lists.size());
			assertEquals(connor, lists.get(0));
			Customer jack = new Customer("Jack", "Harris", "R7");
			lists.add(jack);
			assertEquals(3, lists.size());
			assertEquals(jack, lists.get(1));
			Customer john = new Customer("John", "Apple", "R8");
			lists.add(john);
			assertEquals(4, lists.size());
			assertEquals(john, lists.get(0));
			Customer ethan = new Customer("Ethan", "Sharer", "R9");
			lists.add(ethan);
			assertEquals(5, lists.size());
			assertEquals(ethan, lists.get(3));
			Customer nick = new Customer("Nick", "Lost", "R10");
			lists.add(nick);
			Customer will = new Customer("Will", "All", "R11");
			lists.add(will);
			assertEquals(7, lists.size());
			assertEquals(will, lists.get(0));
			assertEquals(john, lists.get(1));
			assertEquals(connor, lists.get(2));
			assertEquals(jack, lists.get(3));
			assertEquals(nick, lists.get(4));
			assertEquals(ethan, lists.get(5));
			assertEquals(zach, lists.get(6));
		} catch (ModelException e) {
			fail();
		}
	}

	/**
	 * Test method for add method in SortedList
	 */
	@Test
	public void testAddMenuItem() {
		try {
			SortedList<MenuItem> lists = new SortedList<MenuItem>();
			MenuItem c = new MenuItem("Coffee", "Latte", 2.5);
			lists.add(c);
			assertEquals(1, lists.size());
			assertEquals(c, lists.get(0));
			MenuItem newC = new MenuItem("Tea", "Green Tea", 2.5);
			lists.add(newC);
			assertEquals(2, lists.size());
			assertEquals(newC, lists.get(1));
			MenuItem p = new MenuItem("Pastries", "Bagel", 2.5);
			lists.add(p);
			assertEquals(3, lists.size());
			assertEquals(p, lists.get(1));
			p = new MenuItem("Coffee", "Americano", 2.5);
			lists.add(p);
			assertEquals(p, lists.get(1));
		} catch (ModelException e) {
			fail();
		}
	}

	/**
	 * Test method for the remove() method in SortedList
	 */
	@Test
	public void testRemove() {
		try {
			SortedList<Customer> lists = new SortedList<Customer>();
			Customer zach = new Customer("Zachary", "Snowdon", "R5");
			lists.add(zach);
			Customer connor = new Customer("Connor", "Brady", "R6");
			lists.add(connor);
			Customer jack = new Customer("Jack", "Harris", "R7");
			lists.add(jack);
			Customer john = new Customer("John", "Apple", "R8");
			lists.add(john);
			Customer ethan = new Customer("Ethan", "Sharer", "R9");
			lists.add(ethan);
			Customer nick = new Customer("Nick", "Lost", "R10");
			lists.add(nick);
			Customer will = new Customer("Will", "All", "R11");
			lists.add(will);
			assertEquals(7, lists.size());
			lists.remove(0);
			assertEquals(6, lists.size());
			assertEquals(john, lists.get(0));
			lists.remove(2);
			assertEquals(5, lists.size());
			assertEquals(nick, lists.get(2));
		} catch (ModelException e) {
			fail();
		}
	}

}

package edu.ncsu.csc216.hloj.model.lists;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test cases for UniqueList
 * 
 * @author Jimmy Gbruoski
 * @author Zachary Snowdon
 *
 */
public class UniqueListTest {

	/**
	 * Test method for add(E)
	 */
	@Test
	public void testAdd() {
		UniqueList<String> testList = new UniqueList<String>(2);
		assertEquals(0, testList.size());
		// Add one item
		assertTrue(testList.add("Chris"));
		assertEquals(1, testList.size());
		assertEquals("Chris", testList.get(0));
		// Add multiple items
		assertTrue(testList.add("Elena"));
		assertTrue(testList.add("Zoe"));
		assertEquals(3, testList.size());
		assertEquals("Chris", testList.get(0));
		assertEquals("Elena", testList.get(1));
		assertEquals("Zoe", testList.get(2));
		// Add at front of list
		testList.add(0, "Anne");
		assertEquals(4, testList.size());
		assertEquals("Anne", testList.get(0));
		assertEquals("Chris", testList.get(1));
		assertEquals("Elena", testList.get(2));
		assertEquals("Zoe", testList.get(3));
		// Add item already on list
		try {
			assertFalse(testList.add("Zoe"));
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, testList.size());
		}
	}

	/**
	 * Test method for remove(int)
	 */
	@Test
	public void testRemove() {
		UniqueList<String> testList = new UniqueList<String>();
		assertTrue(testList.add("Chris"));
		assertTrue(testList.add("Elena"));
		assertTrue(testList.add("Zoe"));
		assertTrue(testList.add("John"));
		assertTrue(testList.add("Zach"));
		assertEquals(5, testList.size());
		String removed = null;
		// Remove item from middle
		removed = testList.remove(3);
		assertEquals(4, testList.size());
		assertEquals("Chris", testList.get(0));
		assertEquals("Elena", testList.get(1));
		assertEquals("Zoe", testList.get(2));
		assertEquals("Zach", testList.get(3));
		assertEquals("John", removed);
		// Remove item from front
		removed = testList.remove(0);
		assertEquals(3, testList.size());
		assertEquals("Elena", testList.get(0));
		assertEquals("Zoe", testList.get(1));
		assertEquals("Zach", testList.get(2));
		assertEquals("Chris", removed);
		// Remove item from back
		removed = testList.remove(testList.size() - 1);
		assertEquals(2, testList.size());
		assertEquals("Elena", testList.get(0));
		assertEquals("Zoe", testList.get(1));
		assertEquals("Zach", removed);
		// Remove item from outside bounds
		try {
			removed = testList.remove(testList.size());
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(2, testList.size());
			assertEquals("Elena", testList.get(0));
			assertEquals("Zoe", testList.get(1));
			assertEquals("Zach", removed);
		}
	}
}

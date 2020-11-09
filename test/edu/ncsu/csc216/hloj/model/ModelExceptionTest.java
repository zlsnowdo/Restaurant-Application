package edu.ncsu.csc216.hloj.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test cases for ModelException
 * 
 * @author Jimmy Gbruoski
 * @author Zachary Snowdon
 * 
 */
public class ModelExceptionTest {

	/**
	 * Test method for ModelException(String)
	 */
	@Test
	public void testModelExceptionWithString() {
		ModelException c = new ModelException("Invalid model");
		assertEquals("Invalid model", c.getMessage());
	}

	/**
	 * Test method for ModelException()
	 */
	@Test
	public void testModelExceptionNoString() {
		ModelException c = new ModelException();
		assertEquals("Model Exception", c.getMessage());
	}

}

package edu.ncsu.csc216.hloj.model;

/**
 * Custom exception to be thrown when the guidelines of business interactions
 * are broken in HLOJ
 * 
 * @author Zachary Snowdon
 * @author Jimmy Gbruoski
 */
public class ModelException extends Exception {
	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	/** Default Exception Message */
	private static final String DEFAULT_MESSAGE = "Model Exception";

	/**
	 * Constructor for ModelException that displays a custom message.
	 * 
	 * @param message displayed when exception is thrown
	 */
	public ModelException(String message) {
		super(message);
	}

	/**
	 * Constructor for ModelException that uses the default message.
	 */
	public ModelException() {
		this(DEFAULT_MESSAGE);
	}
}

/**
 * 
 */
package edu.ncsu.csc216.hloj;

import java.awt.EventQueue;

import edu.ncsu.csc216.hloj.view.MainWindow;

/**
 * Main class for the Howl Lot of Java application containing the main method
 * @author Ignacioxd
 *
 */
public class HLOJ {

	/**
	 * Entry point for the Howl Lot of Java application
	 * @param args input parameters are ignored
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
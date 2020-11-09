package edu.ncsu.csc216.hloj.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.ncsu.csc216.hloj.model.Customer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

/**
 * User interface for customer input.
 * @author Ignacioxd
 */
public class CustomerInputWindow extends JDialog {

	/**	Serial version id for serialization */	
	private static final long serialVersionUID = 2L;

	/** Title for edit button */
	private static final String EDIT_TITLE = "Edit Customer";
	/** Title for add button */
	private static final String ADD_TITLE = "Add Customer";
	/** Panel for content */
	private final JPanel contentPanel = new JPanel();
	/** Handler for window input */
	private CustomerInputWindowHandler handler;
	/** Text field for first name */
	private JTextField txtFirstName;
	/** Text field for last name */
	private JTextField txtLastName;
	/** Text field for id */
	private JTextField txtID;
	
	/**
	 * Constructor for customer input window.
	 * @param owner owning window
	 */
	public CustomerInputWindow(Window owner) {
		this(owner, null);
	}

	/**
	 * Create the dialog.
	 * @param owner owning window
	 * @param customer Customer to work with
	 */
	public CustomerInputWindow(Window owner, Customer customer) {
		super(owner);
		setTitle(ADD_TITLE);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 253, 156);
		setLocationRelativeTo(owner);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gblContentPanel = new GridBagLayout();
		gblContentPanel.columnWidths = new int[]{0, 0, 0};
		gblContentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gblContentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gblContentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gblContentPanel);
		{
			JLabel lblFirstName = new JLabel("First Name");
			GridBagConstraints gbcLblFirstName = new GridBagConstraints();
			gbcLblFirstName.insets = new Insets(0, 0, 5, 5);
			gbcLblFirstName.anchor = GridBagConstraints.EAST;
			gbcLblFirstName.gridx = 0;
			gbcLblFirstName.gridy = 0;
			contentPanel.add(lblFirstName, gbcLblFirstName);
		}
		{
			txtFirstName = new JTextField();
			GridBagConstraints gbcTxtFirstName = new GridBagConstraints();
			gbcTxtFirstName.insets = new Insets(0, 0, 5, 0);
			gbcTxtFirstName.fill = GridBagConstraints.HORIZONTAL;
			gbcTxtFirstName.gridx = 1;
			gbcTxtFirstName.gridy = 0;
			contentPanel.add(txtFirstName, gbcTxtFirstName);
			txtFirstName.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			GridBagConstraints gbcLblLastName = new GridBagConstraints();
			gbcLblLastName.anchor = GridBagConstraints.EAST;
			gbcLblLastName.insets = new Insets(0, 0, 5, 5);
			gbcLblLastName.gridx = 0;
			gbcLblLastName.gridy = 1;
			contentPanel.add(lblLastName, gbcLblLastName);
		}
		{
			txtLastName = new JTextField();
			GridBagConstraints gbcTxtLastName = new GridBagConstraints();
			gbcTxtLastName.insets = new Insets(0, 0, 5, 0);
			gbcTxtLastName.fill = GridBagConstraints.HORIZONTAL;
			gbcTxtLastName.gridx = 1;
			gbcTxtLastName.gridy = 1;
			contentPanel.add(txtLastName, gbcTxtLastName);
			txtLastName.setColumns(10);
		}
		{
			JLabel lblID = new JLabel("ID");
			GridBagConstraints gbcLblID = new GridBagConstraints();
			gbcLblID.anchor = GridBagConstraints.EAST;
			gbcLblID.insets = new Insets(0, 0, 0, 5);
			gbcLblID.gridx = 0;
			gbcLblID.gridy = 2;
			contentPanel.add(lblID, gbcLblID);
		}
		{
			txtID = new JTextField();
			GridBagConstraints gbcTxtID = new GridBagConstraints();
			gbcTxtID.fill = GridBagConstraints.HORIZONTAL;
			gbcTxtID.gridx = 1;
			gbcTxtID.gridy = 2;
			contentPanel.add(txtID, gbcTxtID);
			txtID.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(handler != null) {
							handler.onOK();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(handler != null) {
							handler.onCancel();
						}
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		if(customer != null) {
			setTitle(EDIT_TITLE);
			txtFirstName.setText(customer.getFirstName());
			txtLastName.setText(customer.getLastName());
			txtID.setText(customer.getId());
		}
	}

	/** 
	 * Returns the first name in the text field.
	 * @return first name
	 */
	public String getFirstName() {
		return txtFirstName.getText();
	}
	
	/** 
	 * Returns the last name in the text field
	 * @return last name
	 */
	public String getLastName() {
		return txtLastName.getText();
	}
	
	/**
	 * Returns the id in the text field
	 * @return id
	 */
	public String getID() {
		return txtID.getText();
	}
	
	/**
	 * Sets the handler to the given handler.
	 * @param handler handers for customer input
	 */
	public void setHandler(CustomerInputWindowHandler handler) {
		this.handler = handler;
	}
	
	/**
	 * Interface to handle customer events
	 * @author Ignacioxd
	 *
	 */
	public interface CustomerInputWindowHandler {
		/**
		 * If the user clicks ok, do this.
		 */
		void onOK();
		/**
		 * If the user clicks cancel, do this.
		 */
		void onCancel();
	}

}
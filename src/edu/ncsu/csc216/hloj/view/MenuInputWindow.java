package edu.ncsu.csc216.hloj.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.ncsu.csc216.hloj.model.MenuItem;
import edu.ncsu.csc216.hloj.model.ModelException;
import edu.ncsu.csc216.hloj.model.manager.MenuManager;

/**
 * Manages the menu of the Howl Lot of Java app
 * @author Ignacioxd
 *
 */
public class MenuInputWindow extends JDialog {

	/** Unique id for serialization */
	private static final long serialVersionUID = 7640631383415327225L;
	/** Add item text label **/
	private static final String ADD_ITEM_LABEL_TEXT = "Add Menu Item";
	/** edit item text label **/
	private static final String EDIT_ITEM_LABEL_TEXT = "Edit Menu Item";


	/** Add item button label **/
	private static final String ADD_ITEM_BUTTON_TEXT = "Add Item";
	/** Add item button label **/
	private static final String EDIT_ITEM_BUTTON_TEXT = "Edit Item";

	/** Main window panel **/
	private final JPanel contentPanel = new JPanel();

	/** Stores a reference to the event handler for this window **/
	MenuInputWindowHandler handler;
	/** List to hold menu items **/
	private JList<String> lstMenu;

	/** Label to hold the title of the action being performed on menu items **/
	private JLabel lblInputAction;
	/** Holds the name of a menu item when adding or editing **/
	private JTextField txtName;
	/** Holds the price of a menu item when adding or editing **/
	private JTextField txtPrice;
	/** Holds the type of a menu item when adding or editing **/
	private JTextField txtType;
	/** Button to save a menu item when adding or editing **/
	private JButton btnSaveInput;

	/**
	 * Create the dialog.
	 * @param owner parent window for this dialog.
	 */
	public MenuInputWindow(Window owner) {
		super(owner);
		setTitle("Manage Menu");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 661, 518);
		setLocationRelativeTo(owner);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 10, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblMenu = new JLabel("Menu Items");
				panel.add(lblMenu, BorderLayout.NORTH);
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					lstMenu = new JList<String>();
					lstMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					lstMenu.addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							menuSelectionChanged();
						}
					});
					scrollPane.setViewportView(lstMenu);
				}
			}
			{
				JPanel pnlMenuActions = new JPanel();
				panel.add(pnlMenuActions, BorderLayout.SOUTH);
				{
					JButton btnAddMenuItem = new JButton("Add Menu Item");
					btnAddMenuItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							lstMenu.clearSelection();
							txtType.requestFocusInWindow();
						}
					});
					pnlMenuActions.add(btnAddMenuItem);
				}
				{
					JButton btnRemoveMenuItem = new JButton("Remove Menu Item");
					btnRemoveMenuItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							removeMenuItem();
						}
					});
					pnlMenuActions.add(btnRemoveMenuItem);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			GridBagLayout gblPanel = new GridBagLayout();
			gblPanel.columnWeights = new double[]{0.0, 1.0};
			gblPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
			panel.setLayout(gblPanel);
			{
				lblInputAction = new JLabel(ADD_ITEM_LABEL_TEXT);
				GridBagConstraints gbcLblInputAction = new GridBagConstraints();
				gbcLblInputAction.gridwidth = 2;
				gbcLblInputAction.insets = new Insets(0, 0, 5, 0);
				gbcLblInputAction.gridx = 0;
				gbcLblInputAction.gridy = 0;
				panel.add(lblInputAction, gbcLblInputAction);
			}
			{
				JLabel lblType = new JLabel("Type");
				GridBagConstraints gbcLblType = new GridBagConstraints();
				gbcLblType.insets = new Insets(0, 0, 5, 5);
				gbcLblType.anchor = GridBagConstraints.EAST;
				gbcLblType.gridx = 0;
				gbcLblType.gridy = 1;
				panel.add(lblType, gbcLblType);
			}
			{
				txtType = new JTextField();
				GridBagConstraints gbcTxtType = new GridBagConstraints();
				gbcTxtType.insets = new Insets(0, 0, 5, 0);
				gbcTxtType.fill = GridBagConstraints.HORIZONTAL;
				gbcTxtType.gridx = 1;
				gbcTxtType.gridy = 1;
				panel.add(txtType, gbcTxtType);
				txtType.setColumns(10);
			}
			{
				JLabel lblName = new JLabel("Name");
				GridBagConstraints gbcLblName = new GridBagConstraints();
				gbcLblName.anchor = GridBagConstraints.EAST;
				gbcLblName.insets = new Insets(0, 0, 5, 5);
				gbcLblName.gridx = 0;
				gbcLblName.gridy = 2;
				panel.add(lblName, gbcLblName);
			}
			{
				txtName = new JTextField();
				GridBagConstraints gbcTxtName = new GridBagConstraints();
				gbcTxtName.insets = new Insets(0, 0, 5, 0);
				gbcTxtName.fill = GridBagConstraints.HORIZONTAL;
				gbcTxtName.gridx = 1;
				gbcTxtName.gridy = 2;
				panel.add(txtName, gbcTxtName);
				txtName.setColumns(10);
			}
			{
				JLabel lblPrice = new JLabel("Price");
				GridBagConstraints gbcLblPrice = new GridBagConstraints();
				gbcLblPrice.anchor = GridBagConstraints.EAST;
				gbcLblPrice.insets = new Insets(0, 0, 5, 5);
				gbcLblPrice.gridx = 0;
				gbcLblPrice.gridy = 3;
				panel.add(lblPrice, gbcLblPrice);
			}
			{
				txtPrice = new JTextField();
				GridBagConstraints gbcTxtPrice = new GridBagConstraints();
				gbcTxtPrice.insets = new Insets(0, 0, 5, 0);
				gbcTxtPrice.fill = GridBagConstraints.HORIZONTAL;
				gbcTxtPrice.gridx = 1;
				gbcTxtPrice.gridy = 3;
				panel.add(txtPrice, gbcTxtPrice);
				txtPrice.setColumns(10);
			}
			{
				btnSaveInput = new JButton(ADD_ITEM_BUTTON_TEXT);
				btnSaveInput.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveInputMenuItem();
					}
				});
				GridBagConstraints gbcBtnSaveInput = new GridBagConstraints();
				gbcBtnSaveInput.gridwidth = 2;
				gbcBtnSaveInput.gridx = 0;
				gbcBtnSaveInput.gridy = 4;
				panel.add(btnSaveInput, gbcBtnSaveInput);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnClose = new JButton("Close");
				btnClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(handler != null) {
							handler.onClose();
						}
					}
				});
				buttonPane.add(btnClose);
				getRootPane().setDefaultButton(btnClose);
			}
		}
		refreshMenu();
	}

	/**
	 * Sets the event handler for this window
	 * @param handler the handler to set
	 */
	public void setHandler(MenuInputWindowHandler handler) {
		this.handler = handler;
	}

	private void refreshMenu() {
		lstMenu.clearSelection();
		DefaultListModel<String> dlmMenu = new DefaultListModel<String>();
		for(MenuItem menuItem : MenuManager.getInstance().getMenuItems()) {
			dlmMenu.addElement(String.valueOf(menuItem));
		}
		lstMenu.setModel(dlmMenu);
	}

	private void menuSelectionChanged() {
		if(lstMenu.isSelectionEmpty()) {
			lblInputAction.setText(ADD_ITEM_LABEL_TEXT);
			btnSaveInput.setText(ADD_ITEM_BUTTON_TEXT);
			txtType.setText("");
			txtName.setText("");
			txtPrice.setText("");
		}
		else {
			lblInputAction.setText(EDIT_ITEM_LABEL_TEXT);
			btnSaveInput.setText(EDIT_ITEM_BUTTON_TEXT);
			MenuItem item = MenuManager.getInstance().getMenuItem(lstMenu.getSelectedIndex());

			txtType.setText(item.getType());
			txtName.setText(item.getName());
			txtPrice.setText(String.valueOf(item.getPrice()));
		}


	}

	private void saveInputMenuItem() {
		try {
			MenuItem newMenuItem = new MenuItem(txtType.getText(), txtName.getText(), Double.parseDouble(txtPrice.getText()));
			if(lstMenu.isSelectionEmpty()) { //Adding a new menu item
				MenuManager.getInstance().addMenuItem(newMenuItem);
			}
			else {//Editing an existing menu item
				MenuManager.getInstance().editMenuItem(lstMenu.getSelectedIndex(), newMenuItem);
			}
			refreshMenu();
		}
		catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "The value provided for the price is not a valid number.");
			return;
		}
		catch(ModelException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			return;
		}

		lblInputAction.setText(ADD_ITEM_LABEL_TEXT);
		btnSaveInput.setText(ADD_ITEM_BUTTON_TEXT);
		txtType.setText("");
		txtName.setText("");
		txtPrice.setText("");
	}



	private void removeMenuItem() {
		if(lstMenu.isSelectionEmpty()) {
			return;
		}
		try {
			MenuManager.getInstance().removeMenuItem(lstMenu.getSelectedIndex());
			refreshMenu();
		} catch (ModelException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

	}
	
	/**
	 * Interface to handle menu events
	 * @author Ignacioxd
	 *
	 */
	public interface MenuInputWindowHandler {
		/**
		 * Called when the window is closed
		 */
		void onClose();
	}

}
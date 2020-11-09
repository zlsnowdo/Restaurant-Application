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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.ncsu.csc216.hloj.model.MenuItem;
import edu.ncsu.csc216.hloj.model.Order;
import edu.ncsu.csc216.hloj.model.OrderItem;
import edu.ncsu.csc216.hloj.model.manager.MenuManager;

/**
 * User interface for order input.
 * @author Ignacioxd
 */
public class OrderInputWindow extends JDialog {

	/** Unique id for serialization */
	private static final long serialVersionUID = 5212567383007681456L;
	/** Common String for order total */
	private static final String TOTAL_TEXT = "Order Total: $";
	/** Handler for order input */
	private OrderInputWindowHandler handler;
	/** Current order */
	private Order order;
	/** Panel for UI components */
	private final JPanel contentPanel = new JPanel();
	/** List to hold menu items */
	private JList<String> lstMenu;
	/** List to hold orders  */
	private JList<String> lstOrder;
	/** Label for the order total information */
	private JLabel lblTotal;

	/**
	 * Create the dialog.
	 * @param owner owning window
	 * @param order Order to work with
	 */
	public OrderInputWindow(Window owner, Order order) {
		setModal(true);
		setTitle("New Order #" + order.getNumber());
		setBounds(100, 100, 600, 503);
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
					scrollPane.setViewportView(lstMenu);
				}
			}
			{
				JPanel pnlMenuActions = new JPanel();
				panel.add(pnlMenuActions, BorderLayout.SOUTH);
				{
					JButton btnAddMenuItem = new JButton("Add to Order");
					btnAddMenuItem.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							addMenuItem();
						}
					});
					pnlMenuActions.add(btnAddMenuItem);
				}
				{
					JButton btnRemoveMenuItem = new JButton("Remove from Order");
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
			gblPanel.columnWeights = new double[]{1.0};
			gblPanel.rowWeights = new double[]{0.0, 2, 0.0};
			panel.setLayout(gblPanel);
			{
				JLabel lblCustomer = new JLabel("New order for " + order.getCustomer());
				GridBagConstraints gbcLblCustomer = new GridBagConstraints();
				gbcLblCustomer.anchor = GridBagConstraints.WEST;
				gbcLblCustomer.insets = new Insets(0, 0, 5, 0);
				gbcLblCustomer.gridx = 0;
				gbcLblCustomer.gridy = 0;
				panel.add(lblCustomer, gbcLblCustomer);
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				GridBagConstraints gbcScrollPane = new GridBagConstraints();
				gbcScrollPane.gridheight = 1;
				gbcScrollPane.insets = new Insets(0, 0, 5, 0);
				gbcScrollPane.fill = GridBagConstraints.BOTH;
				gbcScrollPane.gridx = 0;
				gbcScrollPane.gridy = 1;
				panel.add(scrollPane, gbcScrollPane);
				{
					lstOrder = new JList<String>();
					lstOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPane.setViewportView(lstOrder);
				}
			}
			{
				lblTotal = new JLabel("Order Total:");
				lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
				GridBagConstraints gbcLblTotal = new GridBagConstraints();
				gbcLblTotal.anchor = GridBagConstraints.EAST;
				gbcLblTotal.insets = new Insets(0, 0, 5, 0);
				gbcLblTotal.gridx = 0;
				gbcLblTotal.gridy = 2;
				panel.add(lblTotal, gbcLblTotal);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnPlaceOrder = new JButton("Place Order");
				btnPlaceOrder.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(handler != null) {
							handler.onPlaceOrder(order);
						}
					}
				});
				btnPlaceOrder.setActionCommand("OK");
				buttonPane.add(btnPlaceOrder);
				getRootPane().setDefaultButton(btnPlaceOrder);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(handler != null) {
							handler.onCancel();
						}
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
		getMenu();
		this.order = order;
	}

	/**
	 * Sets the handler to the given input handler.
	 * @param handler the handler to set
	 */
	public void setHandler(OrderInputWindowHandler handler) {
		this.handler = handler;
	}

	/**
	 * Gets the menu item information for display.
	 */
	private void getMenu() {
		lstMenu.clearSelection();
		DefaultListModel<String> dlmMenu = new DefaultListModel<String>();
		for(MenuItem menuItem : MenuManager.getInstance().getMenuItems()) {
			dlmMenu.addElement(String.valueOf(menuItem));
		}
		lstMenu.setModel(dlmMenu);
	}

	/**
	 * Refreshes the order list for display.
	 */
	private void refreshOrder() {
		lstOrder.clearSelection();
		DefaultListModel<String> dlmOrder = new DefaultListModel<String>();
		for(OrderItem orderItem : order.getItems()) {
			MenuItem item = orderItem.getMenuItem();
			dlmOrder.addElement(String.valueOf(item));
			String row = "                    x" + orderItem.getQuantity();
			row += " = $" + orderItem.getQuantity() * item.getPrice();

			dlmOrder.addElement(row);
		}
		lstOrder.setModel(dlmOrder);
		lblTotal.setText(TOTAL_TEXT + order.getTotal());
	}

	/**
	 * Returns the selected menu item.
	 * @return the selected menu item
	 */
	private MenuItem getSelectedMenuItem() {
		if(lstMenu.isSelectionEmpty()) {
			return null;
		}
		return MenuManager.getInstance().getMenuItem(lstMenu.getSelectedIndex());
	}

	/**
	 * Adds the selected menu item to the order.
	 */
	private void addMenuItem() {
		if(lstMenu.isSelectionEmpty()) {
			return;
		}
		MenuItem item = getSelectedMenuItem();
		order.addMenuItem(item);
		refreshOrder();
	}

	/** 
	 * Removes the selected menu item from the order.
	 */
	private void removeMenuItem() {
		if(lstMenu.isSelectionEmpty()) {
			return;
		}
		MenuItem item = getSelectedMenuItem();
		order.removeMenuItem(item);
		refreshOrder();
	}

	/**
	 * Interface for handling customer input.
	 */
	public interface OrderInputWindowHandler {
		/**
		 * Place the user's order.
		 * @param order the order to place.
		 */
		void onPlaceOrder(Order order);
		/**
		 * Cancel the user's order.
		 */
		void onCancel();
	}

}
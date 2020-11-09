package edu.ncsu.csc216.hloj.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

/**
 * Window to display details of an order
 * @author Ignacioxd
 *
 */
public class OrderDetailsWindow extends JDialog {

	/** Unique id for serialization */
	private static final long serialVersionUID = 4766033558388122826L;
	/** Constant text to prepend to total label */
	private static final String TOTAL_TEXT = "Order Total: $";

	/** Holds a reference to the event handler for this window */
	private OrderDetailsWindowHandler handler;
	/** Holds the order to display*/
	private Order order;


	/** Main layout panel for this window */
	private final JPanel contentPanel = new JPanel();
	/** Displays the menu items in the order */
	private JList<String> lstOrder;
	/** Holds the total cost of the order */
	private JLabel lblTotal;

	/**
	 * Creates a new instance of this window
	 * @param owner Parent window for this window
	 * @param order Order to display
	 */
	public OrderDetailsWindow(Window owner, Order order) {
		setModal(true);
		setTitle("Details for Order #" + order.getNumber());
		setBounds(100, 100, 368, 503);
		setLocationRelativeTo(owner);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			GridBagLayout gblPanel = new GridBagLayout();
			gblPanel.columnWeights = new double[]{1.0};
			gblPanel.rowWeights = new double[]{0.0, 2, 0.0};
			panel.setLayout(gblPanel);
			{
				JLabel lblCustomer = new JLabel("Order #" + order.getNumber() + " for " + order.getCustomer());
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
				JButton btnFulfill = new JButton("Fulfill Order");
				btnFulfill.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(handler != null) {
							handler.onFulfill(order);
						}
					}
				});
				btnFulfill.setActionCommand("OK");
				buttonPane.add(btnFulfill);
				getRootPane().setDefaultButton(btnFulfill);
			}
			{
				JButton btnClose = new JButton("Close");
				btnClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(handler != null) {
							handler.onClose();
						}
					}
				});
				btnClose.setActionCommand("Cancel");
				buttonPane.add(btnClose);
			}
		}
		this.order = order;
		refreshOrder();
	}

	/**
	 * Sets the event handler for this window
	 * @param handler event handler to set
	 */
	public void setHandler(OrderDetailsWindowHandler handler) {
		this.handler = handler;
	}

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
	 * Interface to handle order events
	 * @author Ignacioxd
	 *
	 */
	public interface OrderDetailsWindowHandler {
		/**
		 * Called when the order is fulfilled
		 * @param order order to be fulfilled
		 */
		void onFulfill(Order order);
		/**
		 * Called when the window is closed
		 */
		void onClose();
	}

}
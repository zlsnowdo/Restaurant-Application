package edu.ncsu.csc216.hloj.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.ncsu.csc216.hloj.model.Customer;
import edu.ncsu.csc216.hloj.model.ModelException;
import edu.ncsu.csc216.hloj.model.Order;
import edu.ncsu.csc216.hloj.model.io.HLOJDataReader;
import edu.ncsu.csc216.hloj.model.io.HLOJDataWriter;
import edu.ncsu.csc216.hloj.model.manager.CustomerManager;
import edu.ncsu.csc216.hloj.model.manager.MenuManager;
import edu.ncsu.csc216.hloj.model.manager.OrderManager;

/**
 * Main GUI window for HowlLotOfJava
 * @author Ignacioxd
 */
public class MainWindow extends JFrame {

	/** Unique id for serialization */
	private static final long serialVersionUID = 1L;
	/** Panel for main content */
	private JPanel contentPane;
	/** JList to contain customers */
	private JList<String> lstCustomers;
	/** JList to contain orders */
	private JList<String> lstOrders;
	/** Current filename */
	private String currentDataFilename;
	/** List of Customers */
	private Customer[] customers;
	/** List of Orders */
	private Order[] orders;



	/**
	 * Create the frame.
	 */
	@SuppressWarnings("deprecation")
	public MainWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitApp();
			}
		});
		setResizable(false);
		setTitle("Howl Lot of Java");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 825, 769);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		JMenuItem mnuLoad = new JMenuItem("Load From File...");
		mnuLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
		});
		mnuLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnNewMenu.add(mnuLoad);

		JMenuItem mnuSave = new JMenuItem("Save To File...");
		mnuSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
		mnuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnNewMenu.add(mnuSave);

		JSeparator mnuSep1 = new JSeparator();
		mnNewMenu.add(mnuSep1);

		JMenuItem mnuClearAll = new JMenuItem("Clear All Data");
		mnuClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAllData();
			}
		});
		mnNewMenu.add(mnuClearAll);

		JSeparator mnuSep2 = new JSeparator();
		mnNewMenu.add(mnuSep2);

		JMenuItem mnuExit = new JMenuItem("Exit");
		mnuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitApp();
			}
		});
		mnuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mnNewMenu.add(mnuExit);


		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JButton btnManageMenu = new JButton("Manage Menu");
		btnManageMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageMenu();
			}
		});
		panel.add(btnManageMenu);

		JButton btnAllOrders = new JButton("Show All Orders");
		btnAllOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lstCustomers.clearSelection();
			}
		});
		panel.add(btnAllOrders);

		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		panel1.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel pnlCustomers = new JPanel();
		panel1.add(pnlCustomers);
		pnlCustomers.setLayout(new BorderLayout(0, 0));

		JPanel pnlCustomerActions = new JPanel();
		pnlCustomers.add(pnlCustomerActions, BorderLayout.SOUTH);

		JButton btnCustomerAdd = new JButton("Add Customer");
		btnCustomerAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCustomer();
			}
		});
		pnlCustomerActions.add(btnCustomerAdd);

		JButton btnCustomerEdit = new JButton("Edit Customer");
		btnCustomerEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCustomer();
			}
		});
		pnlCustomerActions.add(btnCustomerEdit);

		JButton btnCustomerRemove = new JButton("Remove Customer");
		btnCustomerRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeCustomer();
			}
		});
		pnlCustomerActions.add(btnCustomerRemove);

		JLabel lblCustomers = new JLabel("Customers");
		pnlCustomers.add(lblCustomers, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		pnlCustomers.add(scrollPane, BorderLayout.CENTER);

		lstCustomers = new JList<String>();
		lstCustomers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				refreshOrders();
			}
		});
		lblCustomers.setLabelFor(lstCustomers);
		scrollPane.setViewportView(lstCustomers);

		JPanel pnlOrders = new JPanel();
		panel1.add(pnlOrders);
		pnlOrders.setLayout(new BorderLayout(0, 0));

		JPanel pnlOrderActions = new JPanel();
		pnlOrders.add(pnlOrderActions, BorderLayout.SOUTH);

		JButton btnNewOrder = new JButton("New Order");
		btnNewOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newOrder();
			}
		});
		pnlOrderActions.add(btnNewOrder);

		JButton btnOrderDetails = new JButton("View Order Details");
		btnOrderDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewOrder();
			}
		});
		pnlOrderActions.add(btnOrderDetails);

		JButton btnCancelOrder = new JButton("Cancel Order");
		btnCancelOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelOrder();
			}
		});
		pnlOrderActions.add(btnCancelOrder);

		JLabel lblOrders = new JLabel("Orders");
		pnlOrders.add(lblOrders, BorderLayout.NORTH);

		JScrollPane scrollPane1 = new JScrollPane();
		pnlOrders.add(scrollPane1, BorderLayout.CENTER);

		lstOrders = new JList<String>();
		scrollPane1.setViewportView(lstOrders);
		refreshCustomers();
		refreshOrders();
	}

	/**
	 * Refreshes the customers.
	 */
	private void refreshCustomers() {
		lstCustomers.clearSelection();
		customers = CustomerManager.getInstance().getCustomers();
		DefaultListModel<String> dlmCustomers = new DefaultListModel<String>();
		for(Customer customer : customers) {
			dlmCustomers.addElement(String.valueOf(customer));
		}
		lstCustomers.setModel(dlmCustomers);
	}

	/**
	 * Refreshes the orders.
	 */
	private void refreshOrders() {
		lstOrders.clearSelection();
		DefaultListModel<String> dlmOrders = new DefaultListModel<String>();
		if(lstCustomers.isSelectionEmpty()) {
			orders = OrderManager.getInstance().getOrders();
		}
		else {
			orders = OrderManager.getInstance().getOrdersByCustomer(getSelectedCustomer());
		}
		for(Order order : orders) {
			dlmOrders.addElement(String.valueOf(order));
		}
		lstOrders.setModel(dlmOrders);
	}

	/**
	 * Manages the list of menu items.
	 */
	private void manageMenu() {
		try {
			MenuInputWindow dialog = new MenuInputWindow(this);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setHandler(new MenuInputWindow.MenuInputWindowHandler() {

				@Override
				public void onClose() {
					dialog.dispose();
					refreshOrders();
				}
			});
			dialog.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Returns the selected customer
	 * @return the selected customer
	 */
	private Customer getSelectedCustomer() {
		if(lstCustomers.isSelectionEmpty()) {
			return null;
		}
		return customers[lstCustomers.getSelectedIndex()];
	}

	/**
	 * Returns the selected order
	 * @return the selected order
	 */
	private Order getSelectedOrder() {
		if(lstOrders.isSelectionEmpty()) {
			return null;
		}
		return orders[lstOrders.getSelectedIndex()];
	}

	/**
	 * Adds a new customer.
	 */
	private void addCustomer() {
		try {
			CustomerInputWindow dialog = new CustomerInputWindow(this);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setHandler(new CustomerInputWindow.CustomerInputWindowHandler() {

				@Override
				public void onOK() { //The user wants to add the customer based on the input provided
					try {
						Customer customer = new Customer(dialog.getFirstName(), dialog.getLastName(), dialog.getID());
						CustomerManager.getInstance().addCustomer(customer);
						dialog.dispose();
					} catch (ModelException e) {
						JOptionPane.showMessageDialog(dialog, e.getMessage());
					}

				}

				@Override
				public void onCancel() {
					dialog.dispose();
				}
			});
			dialog.setVisible(true);
			refreshCustomers();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Edits the customer.
	 */
	private void editCustomer() {
		Customer customer = getSelectedCustomer();
		if(customer == null) {
			return;
		}

		try {
			CustomerInputWindow dialog = new CustomerInputWindow(this, customer);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setHandler(new CustomerInputWindow.CustomerInputWindowHandler() {

				@Override
				public void onOK() {
					try {
						Customer newCustomer = new Customer(dialog.getFirstName(), dialog.getLastName(), dialog.getID());
						CustomerManager.getInstance().editCustomer(lstCustomers.getSelectedIndex(), newCustomer);
						refreshCustomers();
						dialog.dispose();
					} catch (ModelException e) {
						JOptionPane.showMessageDialog(dialog, e.getMessage());
					}

				}

				@Override
				public void onCancel() {
					dialog.dispose();
				}
			});
			dialog.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Removes the customer
	 */
	private void removeCustomer() {
		Customer customer = getSelectedCustomer();
		if(customer == null) {
			return;
		}
		try {
			CustomerManager.getInstance().removeCustomer(customer);
			refreshCustomers();
		} catch (ModelException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	/**
	 * Creates a new order.
	 */
	private void newOrder() {
		Customer customer = getSelectedCustomer();
		if(customer == null) {
			JOptionPane.showMessageDialog(this, "You need to select a customer first.");
			return;
		}
		try {
			Order order = OrderManager.getInstance().getNextOrder(customer);
			OrderInputWindow dialog = new OrderInputWindow(this, order);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setHandler(new OrderInputWindow.OrderInputWindowHandler() {
				@Override
				public void onPlaceOrder(Order order) {
					try {
						OrderManager.getInstance().placeOrder(order);
						refreshOrders();
						dialog.dispose();
					} catch (ModelException e) {
						JOptionPane.showMessageDialog(dialog, e.getMessage());
					}
				}

				@Override
				public void onCancel() {
					dialog.dispose();

				}
			});
			dialog.setVisible(true);

		} catch (ModelException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Cancels the selected order.
	 */
	private void cancelOrder() {
		Order order = getSelectedOrder();
		if(order == null) {
			return;
		}
		try {
			OrderManager.getInstance().cancelOrder(order);
			refreshOrders();
		} catch (ModelException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}


	/**
	 * Views the selected order.
	 */
	protected void viewOrder() {
		Order order = getSelectedOrder();
		if(order == null) {
			JOptionPane.showMessageDialog(this, "You need to select an order first.");
			return;
		}
		try {
			OrderDetailsWindow dialog = new OrderDetailsWindow(this, order);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setHandler(new OrderDetailsWindow.OrderDetailsWindowHandler() {

				@Override
				public void onFulfill(Order order) {
					try {
						OrderManager.getInstance().fulfillOrder(order);
						refreshOrders();
						dialog.dispose();
					} catch (ModelException e) {
						JOptionPane.showMessageDialog(dialog, e.getMessage());
					}
				}

				@Override
				public void onClose() {
					dialog.dispose();

				}
			});
			dialog.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Asks the user if they want to save.
	 */
	private void askToSave() {
        int result = JOptionPane.showConfirmDialog(this, "Do you want to save the current state to a file?", "Save State?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
        	saveFile();
        }
	}

	/**
	 * Loads data from a file.
	 */
	private void loadFile() {
		askToSave();
		try {
			JFileChooser chooser = new JFileChooser("./");
			FileNameExtensionFilter filterExt = new FileNameExtensionFilter("HLOJ data files (txt)", "txt");
			chooser.setFileFilter(filterExt);
			chooser.setMultiSelectionEnabled(false);
			int resultAction = chooser.showOpenDialog(this);
			if (resultAction == JFileChooser.APPROVE_OPTION) {
				String filename = chooser.getSelectedFile().getAbsolutePath();
				HLOJDataReader.readData(filename);
				currentDataFilename = filename;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error opening file.", "Opening Error", JOptionPane.ERROR_MESSAGE);
		}
		finally {
			refreshCustomers();
			refreshOrders();
		}
	}

	/**
	 * Saves the current state to a file.
	 */
	private void saveFile() {
		try {
			JFileChooser chooser = new JFileChooser("./");
			FileNameExtensionFilter filterExt = new FileNameExtensionFilter("HLOJ data files (txt)", "txt");
			chooser.setFileFilter(filterExt);
			chooser.setMultiSelectionEnabled(false);
			if (currentDataFilename != null)
				chooser.setSelectedFile(new File(currentDataFilename));
			int resultAction = chooser.showSaveDialog(this);
			if (resultAction == JFileChooser.APPROVE_OPTION) {
				if (!chooser.getSelectedFile().getName().endsWith(".txt")) {
					throw new IllegalArgumentException();
				}
				currentDataFilename = chooser.getSelectedFile().getAbsolutePath();
				HLOJDataWriter.writeData(currentDataFilename);
			}
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(this, "File not saved.", "Saving Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	/**
	 * Clears all data in the system.
	 */
	private void clearAllData() {
		askToSave();
		OrderManager.getInstance().removeAllOrders();
		CustomerManager.getInstance().removeAllCustomers();
		MenuManager.getInstance().removeAllMenuItems();
		refreshCustomers();
		refreshOrders();
	}

	/**
	 * Prompts the user to save the state and then closes the application.
	 */
	private void exitApp() {
		askToSave();
		System.exit(NORMAL);
	}
}
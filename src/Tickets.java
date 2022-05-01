import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Tickets extends JFrame implements ActionListener {

	// class level member objects
	Dao dao = new Dao(); // for CRUD operations
	Boolean chkIfAdmin = null;

	// Main menu object items
	private JMenu mnuFile = new JMenu("File");
	private JMenu mnuAdmin = new JMenu("Admin");
	private JMenu mnuTickets = new JMenu("Tickets");
	
	JScrollPane sp;

	// Sub menu item objects for all Main menu item objects
	JMenuItem mnuItemExit;
	JMenuItem mnuItemUpdate;
	JMenuItem mnuItemDelete;
	JMenuItem mnuItemUpdateUsr;
	JMenuItem mnuItemDeleteUsr;
	JMenuItem mnuItemOpenUsr;
	JMenuItem mnuItemViewUsr;
	JMenuItem mnuItemOpenTicket;
	JMenuItem mnuItemViewTicket;

	public Tickets(Boolean isAdmin) {

		chkIfAdmin = isAdmin;
		createMenu();
		prepareGUI();

	}

	private void createMenu() {

		/* Initialize sub menu items **************************************/

		// initialize sub menu item for File main menu
		mnuItemExit = new JMenuItem("Exit");
		// add to File main menu item
		mnuFile.add(mnuItemExit);

		// initialize first sub menu items for Admin main menu
		mnuItemUpdate = new JMenuItem("Update Ticket");
		// add to Admin main menu item
		mnuAdmin.add(mnuItemUpdate);

		// initialize second sub menu items for Admin main menu
		mnuItemDelete = new JMenuItem("Delete Ticket");
		// add to Admin main menu item
		mnuAdmin.add(mnuItemDelete);
		
		// initialize first sub menu item for Tickets main menu
		mnuItemOpenTicket = new JMenuItem("Open Ticket");
		// add to Ticket Main menu item
		mnuAdmin.add(mnuItemOpenTicket);

		// initialize second sub menu item for Tickets main menu
		mnuItemViewTicket = new JMenuItem("View Ticket");
		// add to Ticket Main menu item
		mnuTickets.add(mnuItemViewTicket);

		
		// initialize third sub menu items for Admin main menu
		mnuItemUpdateUsr = new JMenuItem("Update User");
		// add to Admin main menu item
		mnuAdmin.add(mnuItemUpdateUsr);

		// initialize fourth sub menu items for Admin main menu
		mnuItemDeleteUsr = new JMenuItem("Delete User");
		// add to Admin main menu item
		mnuAdmin.add(mnuItemDeleteUsr);

		// initialize first sub menu item for Tickets main menu
		mnuItemOpenUsr = new JMenuItem("Open User");
		// add to Ticket Main menu item
		mnuAdmin.add(mnuItemOpenUsr);

		// initialize second sub menu item for Tickets main menu
		mnuItemViewUsr = new JMenuItem("View User");
		// add to Ticket Main menu item
		mnuAdmin.add(mnuItemViewUsr);

		// initialize any more desired sub menu items below

		/* Add action listeners for each desired menu item *************/
		mnuItemExit.addActionListener(this);
		mnuItemUpdate.addActionListener(this);
		mnuItemDelete.addActionListener(this);
		mnuItemOpenTicket.addActionListener(this);
		mnuItemViewTicket.addActionListener(this);
		mnuItemUpdateUsr.addActionListener(this);
		mnuItemDeleteUsr.addActionListener(this);
		mnuItemOpenUsr.addActionListener(this);
		mnuItemViewUsr.addActionListener(this);

		 /*
		  * continue implementing any other desired sub menu items (like 
		  * for update and delete sub menus for example) with similar 
		  * syntax & logic as shown above
		 */


	}

	private void prepareGUI() {

		// create JMenu bar
		JMenuBar bar = new JMenuBar();
		bar.add(mnuFile); // add main menu items in order, to JMenuBar
		if(chkIfAdmin) {
			bar.add(mnuAdmin);
		}
		bar.add(mnuTickets);
		// add menu bar components to frame
		setJMenuBar(bar);

		addWindowListener(new WindowAdapter() {
			// define a window close operation
			public void windowClosing(WindowEvent wE) {
				System.exit(0);
			}
		});
		// set frame options
		setSize(400, 400);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// implement actions for sub menu items
		if (e.getSource() == mnuItemExit) {
			System.exit(0);
		} else if (e.getSource() == mnuItemOpenTicket) {

			// get ticket information
			String ticketName = JOptionPane.showInputDialog(null, "Enter your name");
			String ticketDesc = null;
			if(ticketName != null) {
				ticketDesc = JOptionPane.showInputDialog(null, "Enter a ticket description");
			}
			String days = "hey";
			int day = -1;
			if(ticketName != null && ticketDesc != null) {
				while(days != null || day < 0) {
					days = JOptionPane.showInputDialog(null, "Enter the ticket duration in days");
					try {
						day = Integer.parseInt(days);
					}
					catch(Exception e1) {
						days = "hey";
						day = -1;
						System.out.println("Day is not an integer");
					}
				}
				
			}
			
			int id = 0;
			
			// insert ticket information to database
			if(ticketName != null && ticketDesc != null && days != null) {
				id = dao.insertRecords(ticketName, ticketDesc, days);
			}

			// display results if successful or not to console / dialog box
			if (id != 0) {
				System.out.println("Ticket ID : " + id + " created successfully!!!");
				JOptionPane.showMessageDialog(null, "Ticket id: " + id + " created");
			} else
				System.out.println("Ticket cannot be created!!!");
		}

		else if (e.getSource() == mnuItemViewTicket) {

			// retrieve all tickets details for viewing in JTable
			try {
				// Use JTable built in functionality to build a table model and
				// display the table model off your result set!!!
				JTable jt = new JTable(ticketsJTable.buildTableModel(dao.readRecords()));
				jt.setBounds(30, 40, 200, 400);
				try {
					remove(sp);
					repaint();
				} catch(Exception e1) {
				}
				sp = new JScrollPane(jt);
				add(sp);
				
				setVisible(true); // refreshes or repaints frame on screen

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		else if (e.getSource() == mnuItemUpdate) {
			// get ticket information
			int id1 = 0;
			String[] options = new String[] {"ID", "(issuer, description)"};
			int response = JOptionPane.showOptionDialog(null, "Select Row by Id or (issuer, description)",
		               "Click a button",
		               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if(response == 0) {
				String ticket_id = null;
				int id = -1;
				while(ticket_id == null || id <= 0) {
					try {
						ticket_id = JOptionPane.showInputDialog(null, "Enter the ticket id");
						id = Integer.parseInt(ticket_id);
					} catch(Exception e1) {
						ticket_id = null;
						id = -1;
						System.out.println("Id is not an integer");
					}
				}
				String ticketName = JOptionPane.showInputDialog(null, "Edit the name");
				String ticketDesc = JOptionPane.showInputDialog(null, "Edit the ticket description");
				id1 = dao.updateRecords(id, ticketName, ticketDesc);
			}
			else if(response == 1) {
				String ticket_issuer = JOptionPane.showInputDialog(null, "Enter the name");
				String ticket_description = JOptionPane.showInputDialog(null, "Enter the ticket description");
				String ticketName = JOptionPane.showInputDialog(null, "Edit the name");
				String ticketDesc = JOptionPane.showInputDialog(null, "Edit the ticket description");
				id1 = dao.updateRecords(ticket_issuer, ticket_description, ticketName, ticketDesc); 
			}
			if (id1 != 0) {
				System.out.println("Updated successfully!!!");
				JOptionPane.showMessageDialog(null, "Updated");
			} else
				System.out.println("Ticket cannot be updated!!!");
//			String ticketName = JOptionPane.showInputDialog(null, "Enter your name");
//			String ticketDesc = JOptionPane.showInputDialog(null, "Enter a ticket description");
		}
		
		else if (e.getSource() == mnuItemDelete) {
			// get ticket information
			int id1 = 0;
			String[] options = new String[] {"ID", "(issuer, description)"};
			int response = JOptionPane.showOptionDialog(null, "Select Row by Id or (issuer, description)",
		               "Click a button",
		               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if(response == 0) {
				String ticket_id = null;
				int id = -1;
				while(ticket_id == null || id <= 0) {
					try {
						ticket_id = JOptionPane.showInputDialog(null, "Enter the ticket id");
						id = Integer.parseInt(ticket_id);
					} catch(Exception e1) {
						ticket_id = null;
						id = -1;
						System.out.println("Id is not an integer");
					}
				}
				id1 = dao.deleteRecords(id);
			}
			else if(response == 1) {
				String ticket_issuer = JOptionPane.showInputDialog(null, "Enter the name");
				String ticket_description = JOptionPane.showInputDialog(null, "Enter the ticket description");
				id1 = dao.deleteRecords(ticket_issuer, ticket_description); 
			}
			if (id1 != 0) {
				System.out.println("Deleted successfully!!!");
				JOptionPane.showMessageDialog(null, "Deleted");
			} else
				System.out.println("Ticket cannot be deleted!!!");
//			String ticketName = JOptionPane.showInputDialog(null, "Enter your name");
//			String ticketDesc = JOptionPane.showInputDialog(null, "Enter a ticket description");
		}
		else if (e.getSource() == mnuItemOpenUsr) {

			// get user information
			String uname = JOptionPane.showInputDialog(null, "Enter your name");
			String upass = null;
			if(uname != null) {
				upass = JOptionPane.showInputDialog(null, "Enter a password");
			}

			// insert user information to database
			int id = 0;
			if(uname != null && upass != null) {
				id = dao.insertUsrs(uname, upass);
			}

			// display results if successful or not to console / dialog box
			if (id != 0) {
				System.out.println("User ID : " + id + " created successfully!!!");
				JOptionPane.showMessageDialog(null, "User id: " + id + " created");
			} else
				System.out.println("User cannot be created!!!");
		}

		else if (e.getSource() == mnuItemViewUsr) {

			// retrieve all user details for viewing in JTable
			try {
				// Use JTable built in functionality to build a table model and
				// display the table model off your result set!!!
				JTable jt = new JTable(ticketsJTable.buildTableModel(dao.readUsrs()));
				jt.setBounds(30, 40, 200, 400);
				try {
					remove(sp);
					repaint();
				} catch(Exception e1) {
				}
				sp = new JScrollPane(jt);
				add(sp);
				setVisible(true); // refreshes or repaints frame on screen

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		else if (e.getSource() == mnuItemUpdateUsr) {
			// get ticket information
			int id1 = 0;
			String[] options = new String[] {"ID", "(Name, password)"};
			int response = JOptionPane.showOptionDialog(null, "Select Row by Id or (Name, password)",
		               "Click a button",
		               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if(response == 0) {
				String uid = null;
				int id = -1;
				while(uid == null || id <= 0) {
					try {
						uid = JOptionPane.showInputDialog(null, "Enter the user id");
						id = Integer.parseInt(uid);
					} catch(Exception e1) {
						uid = null;
						id = -1;
						System.out.println("Id is not an integer");
					}
				}
				String name = JOptionPane.showInputDialog(null, "Edit the User name");
				String password = JOptionPane.showInputDialog(null, "Edit the User password");
				String adminlevel = null;
				int adminlevel1 = -1;
				while(adminlevel == null || adminlevel1 < 0 || adminlevel1 > 1) {
					try {
						adminlevel = JOptionPane.showInputDialog(null, "Edit the User authority");
						adminlevel1 = Integer.parseInt(adminlevel);
					} catch(Exception e1) {
						adminlevel = null;
						adminlevel1 = -1;
						System.out.println("Admin level can only be 0 or 1");
					}
				}
				id1 = dao.updateUsrs(id, name, password, adminlevel);
			}
			else if(response == 1) {
				String uname = JOptionPane.showInputDialog(null, "Enter the User name");
				String upass = JOptionPane.showInputDialog(null, "Enter the User password");
				String name = JOptionPane.showInputDialog(null, "Edit the User name");
				String password = JOptionPane.showInputDialog(null, "Edit the User password");
				String adminlevel = null;
				int adminlevel1 = -1;
				while(adminlevel == null || adminlevel1 < 0 || adminlevel1 > 1) {
					try {
						adminlevel = JOptionPane.showInputDialog(null, "Edit the User authority");
						adminlevel1 = Integer.parseInt(adminlevel);
					} catch(Exception e1) {
						adminlevel = null;
						adminlevel1 = -1;
						System.out.println("Admin level can only be 0 or 1");
					}
				}
				id1 = dao.updateUsrs(uname, upass, name, password, adminlevel); 
			}
			if (id1 != 0) {
				System.out.println("Updated successfully!!!");
				JOptionPane.showMessageDialog(null, "Updated");
			} else
				System.out.println("User cannot be updated!!!");
//			String ticketName = JOptionPane.showInputDialog(null, "Enter your name");
//			String ticketDesc = JOptionPane.showInputDialog(null, "Enter a ticket description");
		}
		
		else if (e.getSource() == mnuItemDeleteUsr) {
			// get ticket information
			int id1 = 0;
			String[] options = new String[] {"ID", "(Name, password)"};
			int response = JOptionPane.showOptionDialog(null, "Select Row by Id or (Name, password)",
		               "Click a button",
		               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			if(response == 0) {
				String uid = null;
				int id = -1;
				while(uid == null || id <= 0) {
					try {
						uid = JOptionPane.showInputDialog(null, "Enter the ticket id");
						id = Integer.parseInt(uid);
					} catch(Exception e1) {
						uid = null;
						id = -1;
						System.out.println("Id is not an integer");
					}
				}
				id1 = dao.deleteUsrs(id);
			}
			else if(response == 1) {
				String uname = JOptionPane.showInputDialog(null, "Enter the name");
				String upass = JOptionPane.showInputDialog(null, "Enter the ticket description");
				id1 = dao.deleteUsrs(uname, upass); 
			}
			if (id1 != 0) {
				System.out.println("Deleted successfully!!!");
				JOptionPane.showMessageDialog(null, "Deleted");
			} else
				System.out.println("User cannot be deleted!!!");
//			String ticketName = JOptionPane.showInputDialog(null, "Enter your name");
//			String ticketDesc = JOptionPane.showInputDialog(null, "Enter a ticket description");
		}
		/*
		 * continue implementing any other desired sub menu items (like for update and
		 * delete sub menus for example) with similar syntax & logic as shown above
		 */

	}

}
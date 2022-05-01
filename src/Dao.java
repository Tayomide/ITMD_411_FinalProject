import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dao {
	// instance fields
	static Connection connect = null;
	Statement statement = null;

	// constructor
	public Dao() {
	  
	}

	public Connection getConnection() {
		// Setup the connection with the DB
		try {
			connect = DriverManager
					.getConnection("jdbc:mysql://www.papademas.net:3307/tickets?autoReconnect=true&useSSL=false"
							+ "&user=fp411&password=411");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connect;
	}

	// CRUD implementation

	public void createTables() {
		// variables for SQL Query table creations
		final String createTicketsTable = "CREATE TABLE jpapa2_tickets(ticket_id INT AUTO_INCREMENT PRIMARY KEY, ticket_issuer VARCHAR(30), ticket_description VARCHAR(200), start_date DATE, end_date DATE)";
		final String createUsersTable = "CREATE TABLE jpapa2_users(uid INT AUTO_INCREMENT PRIMARY KEY, uname VARCHAR(30), upass VARCHAR(30), admin int)";

		try {

			// execute queries to create tables

			statement = getConnection().createStatement();

			statement.executeUpdate(createTicketsTable);
			statement.executeUpdate(createUsersTable);
			System.out.println("Created tables in given database...");

			// end create table
			// close connection/statement object
			statement.close();
			connect.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// add users to user table
		addUsers();
	}

	public void addUsers() {
		// add list of users from userlist.csv file to users table

		// variables for SQL Query inserts
		String sql;

		Statement statement;
		BufferedReader br;
		List<List<String>> array = new ArrayList<>(); // list to hold (rows & cols)

		// read data from file
		try {
			br = new BufferedReader(new FileReader(new File("./userlist.csv")));

			String line;
			while ((line = br.readLine()) != null) {
				array.add(Arrays.asList(line.split(",")));
			}
		} catch (Exception e) {
			System.out.println("There was a problem loading the file");
		}

		try {

			// Setup the connection with the DB

			statement = getConnection().createStatement();

			// create loop to grab each array index containing a list of values
			// and PASS (insert) that data into your User table
			for (List<String> rowData : array) {

				sql = "insert into jpapa2_users(uname,upass,admin) " + "values('" + rowData.get(0) + "'," + " '"
						+ rowData.get(1) + "','" + rowData.get(2) + "');";
				statement.executeUpdate(sql);
			}
			System.out.println("Inserts completed in the given database...");

			// close statement object
			statement.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public int insertRecords(String ticketName, String ticketDesc, String days) {
		int id = 0;
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate("Insert into jpapa2_tickets" + "(ticket_issuer, ticket_description, start_date, end_date) values(" + " '"
					+ ticketName + "','" + ticketDesc + "', NOW(), DATE_SUB(NOW(), INTERVAL -" + days + " DAY))", Statement.RETURN_GENERATED_KEYS);

			// retrieve ticket id number newly auto generated upon record insertion
			ResultSet resultSet = null;
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				// retrieve first field in table
				id = resultSet.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;

	}

	public ResultSet readRecords() {

		ResultSet results = null;
		try {
			statement = connect.createStatement();
			results = statement.executeQuery("SELECT * FROM jpapa2_tickets");
			//connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return results;
	}
	// continue coding for updateRecords implementation
	public int updateRecords(String ticket_issuer, String ticket_description, String ticketName, String ticketDesc) {
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate("UPDATE jpapa2_tickets SET ticket_issuer = '" + ticketName + "', ticket_description = '" + ticketDesc +
					"' WHERE ticket_issuer = '" + ticket_issuer + "' AND ticket_description = '" + ticket_description + "'");
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateRecords(int id, String ticketName, String ticketDesc) {
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate("UPDATE jpapa2_tickets SET ticket_issuer = '" + ticketName + "', ticket_description = '" + ticketDesc +
					"' WHERE ticket_id = " + id);
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	// continue coding for deleteRecords implementation
	
	public int deleteRecords(String ticket_issuer, String ticket_description) {
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate("DELETE FROM jpapa2_tickets WHERE ticket_issuer = '" + ticket_issuer + "' AND ticket_description = '" + ticket_description + "'");
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int deleteRecords(int id) {
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate("DELETE FROM jpapa2_tickets WHERE ticket_id = " + id);
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public ResultSet readUsrs() {

		ResultSet results = null;
		try {
			statement = connect.createStatement();
			results = statement.executeQuery("SELECT * FROM jpapa2_users");
			//connect.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return results;
	}
	
	public int insertUsrs(String uname, String upass) {
		int id = 0;
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate("Insert into jpapa2_users" + "(uname, upass, admin) values(" + " '"
					+ uname + "','" + upass + "',0)", Statement.RETURN_GENERATED_KEYS);

			// retrieve ticket id number newly auto generated upon record insertion
			ResultSet resultSet = null;
			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				// retrieve first field in table
				id = resultSet.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;

	}
	
	public int updateUsrs(String uname, String upass, String name, String password, String adminlevel) {
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate("UPDATE jpapa2_users SET uname = '" + name + "', upass = '" + password + "', admin = " + adminlevel +
					" WHERE uname = '" + uname + "' AND upass = '" + upass + "'");
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int updateUsrs(int id, String name, String password, String adminlevel) {
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate("UPDATE jpapa2_users SET uname = '" + name + "', upass = '" + password + "', admin = " + adminlevel +
					" WHERE uid = " + id);
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	// continue coding for deleteRecords implementation
	
	public int deleteUsrs(String uname, String upass) {
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate("DELETE FROM jpapa2_users WHERE uname = '" + uname + "' AND upass = '" + upass + "'");
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int deleteUsrs(int id) {
		try {
			statement = getConnection().createStatement();
			statement.executeUpdate("DELETE FROM jpapa2_users WHERE uid = " + id);
			return 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}

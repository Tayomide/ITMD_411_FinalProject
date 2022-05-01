import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Login extends JFrame{
	
	public Login() {
		
		setSize(400, 210);
		setLayout(new GridLayout(4, 2));
		setLocationRelativeTo(null); // centers window
		
		//DEFINE ELEMENTS
		JLabel lblUsername;
		JLabel lblPassword;
		JLabel lblStatus;
		
		JTextField txtUname;
		JPasswordField txtPassword;
		
		JButton btn;
		JButton btnExit;
		
		// INITIALIZE ELEMENTS
		lblUsername = new JLabel("Username", JLabel.LEFT);
		lblPassword = new JLabel("Password", JLabel.LEFT);
		lblStatus = new JLabel(" ", JLabel.CENTER);
		
		txtUname = new JTextField(10);
		txtPassword = new JPasswordField();
		
		btn = new JButton("Submit");
		btnExit = new JButton("Exit");
		
		// constraints

		lblStatus.setToolTipText("Contact help desk to unlock password");
		lblUsername.setHorizontalAlignment(JLabel.CENTER);
		lblPassword.setHorizontalAlignment(JLabel.CENTER);
 
		// ADD OBJECTS TO FRAME
		add(lblUsername);  // 1st row filler
		add(txtUname);
		add(lblPassword);  // 2nd row
		add(txtPassword);
		add(btn);          // 3rd row
		add(btnExit);
		add(lblStatus);    // 4th row
		
		setVisible(true); // SHOW THE FRAME
	}
	
	public static void main(String[] args) {
		
		new Login();
	}
}

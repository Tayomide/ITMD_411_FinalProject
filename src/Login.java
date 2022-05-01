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
		
		setVisible(true); // SHOW THE FRAME
	}
	
	public static void main(String[] args) {
		
		new Login();
	}
}

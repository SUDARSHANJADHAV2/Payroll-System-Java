import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JLabel label1, label2, label3;
    JTextField textField;
    JPasswordField passField;
    JButton button1, button2;

    Login() {
        super("Login Page");
        setLayout(new BorderLayout());
        passField = new JPasswordField(10);
        textField = new JTextField(10);
        label3 = new JLabel(new ImageIcon(ClassLoader.getSystemResource("icon\\defaultpic.png")));

        button1 = new JButton("Submit", new ImageIcon(ClassLoader.getSystemResource("icon\\login.png")));
        button2 = new JButton("Cancel", new ImageIcon(ClassLoader.getSystemResource("icon\\Cancel.png")));

        button1.addActionListener(this);
        button2.addActionListener(this);

        JPanel p1, p2, p3, p4;
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();

        add(label3, BorderLayout.WEST);

        p2.add(new JLabel("User Name  "));
        p2.add(textField);
        p2.add(new JLabel("Password "));
        p2.add(passField);
        add(p2, BorderLayout.CENTER);

        p4.add(button1);
        p4.add(button2);

        add(p4, BorderLayout.SOUTH);

        setSize(400, 250);
        setLocation(600, 400);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == button1) { // Submit button
            authenticateUser();
        } else if (ae.getSource() == button2) { // Cancel button
            System.exit(0);
        }
    }

    /**
     * Authenticate user with secure database query
     */
    private void authenticateUser() {
        String username = textField.getText().trim();
        char[] passwordChars = passField.getPassword();
        String password = new String(passwordChars);

        // Clear password from memory for security
        java.util.Arrays.fill(passwordChars, ' ');

        // Input validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password.",
                    "Login Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Conn conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = new Conn();

            // Check if login table exists, create if not
            DatabaseChecker.checkDatabase();
            
            // Use PreparedStatement to prevent SQL injection
            String query = "SELECT username, role FROM login WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                JOptionPane.showMessageDialog(this,
                        "Welcome, " + username + " (" + role + ")!",
                        "Login Successful",
                        JOptionPane.INFORMATION_MESSAGE);

                // Store current user info for session management
                UserSession.setCurrentUser(username, role);

                new Project().setVisible(true);
                this.dispose(); // Use dispose() instead of setVisible(false)
            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password.\nPlease try again.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);

                // Clear fields on failed login
                textField.setText("");
                passField.setText("");
                textField.requestFocus();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Database error: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            System.err.println("Login authentication error: " + e.getMessage());
        } finally {
            // Ensure resources are properly closed
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }

        // Clear password from memory
        password = null;
    }

    public static void main(String[] args) {
        new Login();
    }
}

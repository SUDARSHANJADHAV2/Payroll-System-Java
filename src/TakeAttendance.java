import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TakeAttendance extends JFrame implements ActionListener {
    JLabel label1, label2, label3, label4, label5, label6, label7;
    JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    JButton button1, button2;
    Choice choice1, choice2, choice3;

    TakeAttendance() {

        setLayout(new GridLayout(4, 2, 50, 50));
        choice1 = new Choice();
        try {
            Conn connection = new Conn();
            ResultSet rs = connection.s.executeQuery("select * from employee");
            while (rs.next()) {
                choice1.add(rs.getString("emp_id"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading employees: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        add(new JLabel("Select Empno"));
        add(choice1);

        label1 = new JLabel("First Half");
        choice2 = new Choice();
        choice2.add("Present");
        choice2.add("Absent");
        choice2.add("Leave");

        add(label1);
        add(choice2);

        label2 = new JLabel("Second Half");
        choice3 = new Choice();
        choice3.add("Present");
        choice3.add("Absent");
        choice3.add("Leave");

        add(label2);
        add(choice3);

        button1 = new JButton("Submit");
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.WHITE);

        button2 = new JButton("Cancel");
        button2.setBackground(Color.BLACK);
        button2.setForeground(Color.WHITE);

        add(button1);
        add(button2);

        button1.addActionListener(this);
        button2.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);

        setVisible(true);
        setSize(400, 450);
        setLocation(600, 200);

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == button1) {
            markAttendance();
        } else if (ae.getSource() == button2) {
            this.dispose();
        }
    }

    private void markAttendance() {
        String selectItem1 = choice2.getSelectedItem();
        String selectedItem2 = choice3.getSelectedItem();
        String empId = choice1.getSelectedItem();
        
        // Input validation
        if (empId == null || empId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an employee.", 
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Conn connection = null;
        PreparedStatement pstmt = null;
        
        try {
            connection = new Conn();
            
            // Use PreparedStatement to prevent SQL injection
            String qry = "INSERT INTO attendance (emp_id, date, first_half, second_half) VALUES (?, CURRENT_DATE, ?, ?)";
            pstmt = connection.prepareStatement(qry);
            pstmt.setString(1, empId);
            pstmt.setString(2, selectItem1);
            pstmt.setString(3, selectedItem2);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Attendance confirmed successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to mark attendance. Please try again.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 23505) { // Unique constraint violation
                JOptionPane.showMessageDialog(this, "Attendance already marked for this employee today.", 
                    "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
            System.err.println("Error marking attendance: " + e.getMessage());
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(this, "Unexpected error: " + ee.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ee.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

    public static void main(String s[]) {
        new TakeAttendance();
    }
}

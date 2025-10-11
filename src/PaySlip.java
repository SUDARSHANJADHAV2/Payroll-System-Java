import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.Calendar;

public class PaySlip extends JFrame implements ActionListener {

    Choice choice;
    JTextArea textArea;
    JButton button;

    PaySlip() {

        setSize(800, 700);
        setLocation(400, 150);
        choice = new Choice();
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("select * from salary");
            while (rs.next()) {
                choice.add(rs.getString("emp_id"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading salary data: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Select Id"));
        panel.add(choice);
        add(panel, "North");

        textArea = new JTextArea(30, 80);
        JScrollPane jsp = new JScrollPane(textArea);

        Font f1 = new Font("arial", Font.BOLD, 20);
        textArea.setFont(f1);

        button = new JButton("Generate Pay Slip");

        add(button, "South");
        add(jsp, "Center");
        button.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        generatePaySlip();
    }

    private void generatePaySlip() {
        String empId = choice.getSelectedItem();
        
        if (empId == null || empId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an employee.", 
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Conn c = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            c = new Conn();

            // Get employee information using PreparedStatement
            String empQuery = "SELECT emp_id, name FROM employee WHERE emp_id = ?";
            pstmt = c.prepareStatement(empQuery);
            pstmt.setString(1, empId);
            rs = pstmt.executeQuery();
            
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Employee not found.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String name = rs.getString("name");
            rs.close();
            pstmt.close();

            // Get salary information using PreparedStatement
            String salQuery = "SELECT * FROM salary WHERE emp_id = ?";
            pstmt = c.prepareStatement(salQuery);
            pstmt.setString(1, empId);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Salary information not found for this employee.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double gross = 0;
            double net = 0;

            java.util.Date d1 = new java.util.Date();
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based
            int year = cal.get(Calendar.YEAR);

            textArea.setText(
                    " ----------------   PAY SLIP FOR THE MONTH OF " + month + " , " + year + "  ------------------------");
            textArea.append("\n");

            textArea.append("\n     Employee ID: " + empId);
            textArea.append("\n     Employee Name: " + name);

            textArea.append("\n----------------------------------------------------------------");
            textArea.append("\n");

            double hra = rs.getDouble("hra");
            textArea.append("\n                  HRA         : " + hra);
            double da = rs.getDouble("da");
            textArea.append("\n                  DA          : " + da);
            double med = rs.getDouble("med");
            textArea.append("\n                  MED         : " + med);
            double pf = rs.getDouble("pf");
            textArea.append("\n                  PF          : " + pf);
            double basic = rs.getDouble("basic_salary");
            textArea.append("\n                  BASIC SALARY : " + basic);
            
            gross = hra + da + med + basic;
            net = gross - pf;

            textArea.append("\n-------------------------------------------------------");
            textArea.append("\n");

            textArea.append("\n       GROSS SALARY: " + gross);
            textArea.append("\n       NET SALARY: " + net);
            textArea.append("\n       Tax: 2.1% of gross " + (gross * 2.1 / 100));
            textArea.append("\n -------------------------------------------------");
            textArea.append("\n");
            textArea.append("\n");
            textArea.append("\n");
            textArea.append("   (  Signature  )      ");

        } catch (Exception ee) {
            JOptionPane.showMessageDialog(this, "Error generating payslip: " + ee.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            ee.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (c != null) c.close();
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new PaySlip().setVisible(true);
    }
}

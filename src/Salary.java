import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Salary extends JFrame implements ActionListener {
    JLabel label1, label2, label3, label4, label5, label6, label7;
    JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    JButton button1, button2;
    Choice choice;

    Salary() {

        super("Set Salary");

        setLayout(new GridLayout(8, 2, 20, 20));
        choice = new Choice();

        try {
            Conn connection = new Conn();
            ResultSet rs = connection.s.executeQuery("select * from employee");

            while (rs.next()) {
                choice.add(rs.getString("emp_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new JLabel("Select Empno"));
        add(choice);

        label1 = new JLabel("HRA");
        textField1 = new JTextField(15);
        add(label1);
        add(textField1);

        label3 = new JLabel("DA");
        textField3 = new JTextField(15);
        add(label3);
        add(textField3);

        label4 = new JLabel("MED");
        textField4 = new JTextField(15);
        add(label4);
        add(textField4);

        label5 = new JLabel("PF");
        textField5 = new JTextField(15);
        add(label5);
        add(textField5);

        label6 = new JLabel("Basic Salary");
        textField6 = new JTextField(15);
        add(label6);
        add(textField6);

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

        setSize(450, 550);
        setLocation(500, 200);
        setVisible(true);

        getContentPane().setBackground(Color.WHITE);

    }

    public void actionPerformed(ActionEvent ae) {
        // Input validation
        if (textField1.getText().trim().isEmpty() || textField3.getText().trim().isEmpty() ||
            textField4.getText().trim().isEmpty() || textField5.getText().trim().isEmpty() ||
            textField6.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all salary fields.", 
                "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String hra = textField1.getText().trim();
            String empId = choice.getSelectedItem();
            String da = textField3.getText().trim();
            String med = textField4.getText().trim();
            String pf = textField5.getText().trim();
            String basic = textField6.getText().trim();

            // Validate numeric inputs
            Double.parseDouble(hra);
            Double.parseDouble(da);
            Double.parseDouble(med);
            Double.parseDouble(pf);
            Double.parseDouble(basic);

            Conn connection1 = new Conn();
            String qry = "INSERT INTO salary (emp_id, basic_salary, hra, da, med, pf, advance) VALUES (?, ?, ?, ?, ?, ?, 0.00)";
            PreparedStatement pstmt = connection1.prepareStatement(qry);
            pstmt.setString(1, empId);
            pstmt.setString(2, basic);
            pstmt.setString(3, hra);
            pstmt.setString(4, da);
            pstmt.setString(5, med);
            pstmt.setString(6, pf);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Salary updated successfully!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update salary.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            pstmt.close();
            connection1.close();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for salary components.", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(this, "Database error: " + ee.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            ee.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Salary();
    }
}

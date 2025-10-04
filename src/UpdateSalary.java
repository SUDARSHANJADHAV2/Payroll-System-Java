import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateSalary extends JFrame implements ActionListener, ItemListener {
    JLabel l1, l2, l3, l4, l5, l6;
    JTextField t1, t2, t3, t4, t5, t6;
    JButton button1, button2;
    Choice choice;

    UpdateSalary() {

        setLayout(null);
        choice = new Choice();
        try {
            Conn connection = new Conn();
            ResultSet rs = connection.s.executeQuery("select * from salary");
            while (rs.next()) {
                choice.add(rs.getString("emp_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel emp = new JLabel("Select Empno");
        emp.setBounds(20, 20, 100, 20);
        add(emp);

        choice.setBounds(120, 20, 200, 20);
        add(choice);

        l1 = new JLabel("Hra");
        t1 = new JTextField(15);

        l1.setBounds(20, 60, 100, 20);
        t1.setBounds(120, 60, 200, 20);
        add(l1);
        add(t1);

        l2 = new JLabel("Da");
        t2 = new JTextField(15);

        l2.setBounds(20, 100, 100, 20);
        t2.setBounds(120, 100, 200, 20);
        add(l2);
        add(t2);

        l3 = new JLabel("Med");
        t3 = new JTextField(15);

        l3.setBounds(20, 140, 100, 20);
        t3.setBounds(120, 140, 200, 20);
        add(l3);
        add(t3);

        l4 = new JLabel("Pf");
        t4 = new JTextField(15);

        l4.setBounds(20, 180, 100, 20);
        t4.setBounds(120, 180, 200, 20);
        add(l4);
        add(t4);

        l5 = new JLabel("basic_salary");
        t5 = new JTextField(15);

        l5.setBounds(20, 220, 100, 20);
        t5.setBounds(120, 220, 200, 20);
        add(l5);
        add(t5);

        button1 = new JButton("Update");
        button1.setBackground(Color.BLACK);
        button1.setForeground(Color.WHITE);

        button2 = new JButton("Delete");
        button2.setBackground(Color.BLACK);
        button2.setForeground(Color.WHITE);

        button1.setBounds(40, 280, 100, 20);
        button2.setBounds(200, 280, 100, 20);
        add(button1);
        add(button2);

        button1.addActionListener(this);
        button2.addActionListener(this);
        choice.addItemListener(this);

        getContentPane().setBackground(Color.WHITE);

        setVisible(true);
        setSize(400, 450);
        setLocation(600, 200);
    }

    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == button1) {
            // Input validation
            if (t1.getText().trim().isEmpty() || t2.getText().trim().isEmpty() ||
                t3.getText().trim().isEmpty() || t4.getText().trim().isEmpty() ||
                t5.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all salary fields.", 
                    "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String hra = t1.getText().trim();
                String empId = choice.getSelectedItem();
                String da = t2.getText().trim();
                String med = t3.getText().trim();
                String pf = t4.getText().trim();
                String basic = t5.getText().trim();

                // Validate numeric inputs
                Double.parseDouble(hra);
                Double.parseDouble(da);
                Double.parseDouble(med);
                Double.parseDouble(pf);
                Double.parseDouble(basic);

                Conn c1 = new Conn();
                String qry = "UPDATE salary SET hra=?, da=?, med=?, pf=?, basic_salary=? WHERE emp_id=?";
                PreparedStatement pstmt = c1.prepareStatement(qry);
                pstmt.setString(1, hra);
                pstmt.setString(2, da);
                pstmt.setString(3, med);
                pstmt.setString(4, pf);
                pstmt.setString(5, basic);
                pstmt.setString(6, empId);
                
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Salary Updated Successfully!");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "No salary record found for the selected employee.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
                pstmt.close();
                c1.close();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for salary components.", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ee) {
                JOptionPane.showMessageDialog(this, "Database error: " + ee.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
                ee.printStackTrace();
            }
        }

        if (ae.getSource() == button2) {
            int option = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this salary record?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                try {
                    Conn connection = new Conn();
                    String qry = "DELETE FROM salary WHERE emp_id=?";
                    PreparedStatement pstmt = connection.prepareStatement(qry);
                    pstmt.setString(1, choice.getSelectedItem());
                    
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Salary Deleted Successfully!");
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "No salary record found for the selected employee.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    pstmt.close();
                    connection.close();
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(this, "Database error: " + ee.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                    ee.printStackTrace();
                }
            }
        }
    }

    public void itemStateChanged(ItemEvent ie) {
        try {
            Conn connection = new Conn();
            String qry = "SELECT * FROM salary WHERE emp_id=?";
            PreparedStatement pstmt = connection.prepareStatement(qry);
            pstmt.setString(1, choice.getSelectedItem());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                t1.setText(rs.getString("hra"));
                t2.setText(rs.getString("da"));
                t3.setText(rs.getString("med"));
                t4.setText(rs.getString("pf"));
                t5.setText(rs.getString("basic_salary"));
            }
            rs.close();
            pstmt.close();
            connection.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new UpdateSalary();
    }

}

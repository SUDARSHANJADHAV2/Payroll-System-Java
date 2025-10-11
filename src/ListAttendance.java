import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ListAttendance extends JFrame implements ActionListener {

    JTable j1;
    JButton b1;
    String h[] = { "Emp id", "Date Time", "First Half", "Second Half" };
    String d[][] = new String[15][4];
    int i = 0;
    int j = 0;

    ListAttendance() {
        super("View Employees Attendance");
        setSize(800, 300);
        setLocation(450, 150);

        try {
            String q = "select emp_id, date, first_half, second_half from attendance";
            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery(q);
            while (rs.next()) {
                d[i][j++] = rs.getString("emp_id");
                d[i][j++] = rs.getString("date");
                d[i][j++] = rs.getString("first_half");
                d[i][j++] = rs.getString("second_half");
                i++;
                j = 0;
            }
            rs.close();
            c1.close();

            // Create table with actual data
            j1 = new JTable(d, h);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading attendance data: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            // Create empty table if there's an error
            j1 = new JTable(new String[0][4], h);
        }

        b1 = new JButton("Print");
        add(b1, "South");
        JScrollPane s1 = new JScrollPane(j1);
        add(s1);

        b1.addActionListener(this);

    }

    public void actionPerformed(ActionEvent ae) {
        try {
            j1.print();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        new ListAttendance().setVisible(true);
    }
}

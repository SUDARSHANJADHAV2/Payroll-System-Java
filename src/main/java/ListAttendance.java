import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListAttendance extends JFrame implements ActionListener {

    JTable j1;
    JButton b1;
    String[] h = {"Emp id", "Date Time", "First Half", "Second Half"};

    ListAttendance() {
        super("View Employees Attendance");
        setSize(800, 300);
        setLocation(450, 150);

        List<String[]> data = new ArrayList<>();
        try (Connection conn = Conn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT emp_id, date, first_half, second_half FROM attendance")) {

            while (rs.next()) {
                String[] row = new String[4];
                row[0] = rs.getString("emp_id");
                row[1] = rs.getString("date");
                row[2] = rs.getString("first_half");
                row[3] = rs.getString("second_half");
                data.add(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading attendance data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        String[][] d = new String[data.size()][4];
        for (int i = 0; i < data.size(); i++) {
            d[i] = data.get(i);
        }

        j1 = new JTable(d, h);

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
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ListAttendance().setVisible(true);
    }
}
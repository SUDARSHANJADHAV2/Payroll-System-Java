import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListEmployee extends JFrame implements ActionListener {

    JTable j1;
    JButton b1;
    String[] h = {"Emp id", "Name", "Gender", "Address", "State", "City", "Email id", "Phone"};

    ListEmployee() {
        super("View Employees");
        setSize(1000, 400);
        setLocation(450, 200);

        List<String[]> data = new ArrayList<>();
        try (Connection conn = Conn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM employee")) {

            while (rs.next()) {
                String[] row = new String[8];
                row[0] = rs.getString("emp_id");
                row[1] = rs.getString("name");
                row[2] = rs.getString("gender");
                row[3] = rs.getString("address");
                row[4] = rs.getString("state");
                row[5] = rs.getString("city");
                row[6] = rs.getString("email");
                row[7] = rs.getString("phone");
                data.add(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        String[][] d = new String[data.size()][8];
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

    public static void main(String[] s) {
        new ListEmployee().setVisible(true);
    }
}
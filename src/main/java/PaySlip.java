import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaySlip extends JFrame {
    private final Choice choice;
    private final JTextArea textArea;

    public PaySlip() {
        super("Generate Pay Slip");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Employee ID:"));
        choice = new Choice();
        loadEmployeeIds();
        topPanel.add(choice);
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(this::generatePayslip);
        topPanel.add(generateButton);
        add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    private void loadEmployeeIds() {
        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT emp_id FROM employee");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                choice.add(rs.getString("emp_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generatePayslip(ActionEvent e) {
        String empId = choice.getSelectedItem();
        String sql = "SELECT e.name, s.* FROM employee e, salary s WHERE e.emp_id = s.emp_id AND e.emp_id = ?";

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    StringBuilder sb = new StringBuilder();
                    LocalDate now = LocalDate.now();
                    sb.append("\t\tPAYSLIP FOR ").append(now.getMonth()).append(", ").append(now.getYear()).append("\n");
                    sb.append("------------------------------------------------------------\n");
                    sb.append("Employee ID:\t").append(rs.getString("emp_id")).append("\n");
                    sb.append("Employee Name:\t").append(rs.getString("name")).append("\n");
                    sb.append("------------------------------------------------------------\n");
                    double basic = rs.getDouble("basic_salary");
                    double hra = rs.getDouble("hra");
                    double da = rs.getDouble("da");
                    double med = rs.getDouble("med");
                    double pf = rs.getDouble("pf");
                    double gross = basic + hra + da + med;
                    double net = gross - pf;
                    sb.append(String.format("Basic Salary:\t%,.2f\n", basic));
                    sb.append(String.format("HRA:\t\t%,.2f\n", hra));
                    sb.append(String.format("DA:\t\t%,.2f\n", da));
                    sb.append(String.format("Medical:\t\t%,.2f\n", med));
                    sb.append("------------------------------------------------------------\n");
                    sb.append(String.format("Gross Salary:\t%,.2f\n", gross));
                    sb.append(String.format("PF Deduction:\t%,.2f\n", pf));
                    sb.append("------------------------------------------------------------\n");
                    sb.append(String.format("Net Salary:\t\t%,.2f\n", net));
                    sb.append("------------------------------------------------------------\n");
                    textArea.setText(sb.toString());
                } else {
                    JOptionPane.showMessageDialog(this, "No salary details found for employee " + empId, "Not Found", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaySlip().setVisible(true));
    }
}
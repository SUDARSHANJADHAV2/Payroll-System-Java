import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Salary extends JFrame {
    private final Choice choice;
    private final JTextField hraField, daField, medField, pfField, basicField;

    public Salary() {
        super("Set Salary");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Emp ID:"), gbc);
        choice = new Choice();
        loadEmployeeIds();
        gbc.gridx = 1;
        panel.add(choice, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("HRA:"), gbc);
        hraField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(hraField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("DA:"), gbc);
        daField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(daField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Medical:"), gbc);
        medField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(medField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("PF:"), gbc);
        pfField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(pfField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Basic Salary:"), gbc);
        basicField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(basicField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(buttonPanel, gbc);

        add(panel);

        submitButton.addActionListener(this::setSalary);
        cancelButton.addActionListener(e -> dispose());
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

    private void setSalary(ActionEvent e) {
        String empId = choice.getSelectedItem();
        String hra = hraField.getText();
        String da = daField.getText();
        String med = medField.getText();
        String pf = pfField.getText();
        String basic = basicField.getText();

        String sql = "INSERT INTO salary(emp_id, hra, da, med, pf, basic_salary) VALUES(?,?,?,?,?,?)";

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, empId);
            ps.setString(2, hra);
            ps.setString(3, da);
            ps.setString(4, med);
            ps.setString(5, pf);
            ps.setString(6, basic);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Salary set successfully.");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error setting salary: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Salary().setVisible(true));
    }
}
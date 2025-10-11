import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateSalary extends JFrame {
    private final JComboBox<String> empIdComboBox;
    private final JTextField hraField, daField, medField, pfField, basicField;

    public UpdateSalary() {
        super("Update Salary");
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
        empIdComboBox = new JComboBox<>();
        loadEmployeeIds();
        empIdComboBox.addItemListener(this::loadSalaryData);
        gbc.gridx = 1;
        panel.add(empIdComboBox, gbc);

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
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(buttonPanel, gbc);

        add(panel);

        updateButton.addActionListener(this::updateSalary);
        deleteButton.addActionListener(this::deleteSalary);
    }

    private void loadEmployeeIds() {
        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT emp_id FROM employee");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                empIdComboBox.addItem(rs.getString("emp_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadSalaryData(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String empId = (String) empIdComboBox.getSelectedItem();
            String sql = "SELECT * FROM salary WHERE emp_id = ?";
            try (Connection conn = Conn.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, empId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        hraField.setText(rs.getString("hra"));
                        daField.setText(rs.getString("da"));
                        medField.setText(rs.getString("med"));
                        pfField.setText(rs.getString("pf"));
                        basicField.setText(rs.getString("basic_salary"));
                    } else {
                        hraField.setText("");
                        daField.setText("");
                        medField.setText("");
                        pfField.setText("");
                        basicField.setText("");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateSalary(ActionEvent e) {
        String empId = (String) empIdComboBox.getSelectedItem();
        String hra = hraField.getText();
        String da = daField.getText();
        String med = medField.getText();
        String pf = pfField.getText();
        String basic = basicField.getText();

        String sql = "UPDATE salary SET hra = ?, da = ?, med = ?, pf = ?, basic_salary = ? WHERE emp_id = ?";

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hra);
            ps.setString(2, da);
            ps.setString(3, med);
            ps.setString(4, pf);
            ps.setString(5, basic);
            ps.setString(6, empId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Salary updated successfully.");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating salary: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSalary(ActionEvent e) {
        String empId = (String) empIdComboBox.getSelectedItem();
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the salary for employee " + empId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM salary WHERE emp_id = ?";
            try (Connection conn = Conn.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, empId);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Salary deleted successfully.");
                dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting salary: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdateSalary().setVisible(true));
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateEmployee extends JFrame {
    private final JComboBox<String> empIdComboBox;
    private final JTextField nameField, addressField, stateField, cityField, emailField, phoneField;
    private final JComboBox<String> genderComboBox;

    public UpdateEmployee() {
        super("Update Employee");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Employee ID:"), gbc);
        empIdComboBox = new JComboBox<>();
        loadEmployeeIds();
        empIdComboBox.addItemListener(this::loadEmployeeData);
        gbc.gridx = 1;
        panel.add(empIdComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Name:"), gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Gender:"), gbc);
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        gbc.gridx = 1;
        panel.add(genderComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Address:"), gbc);
        addressField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("State:"), gbc);
        stateField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(stateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("City:"), gbc);
        cityField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(cityField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Phone:"), gbc);
        phoneField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

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

        updateButton.addActionListener(this::updateEmployee);
        deleteButton.addActionListener(this::deleteEmployee);
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

    private void loadEmployeeData(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String empId = (String) empIdComboBox.getSelectedItem();
            String sql = "SELECT * FROM employee WHERE emp_id = ?";
            try (Connection conn = Conn.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, empId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        nameField.setText(rs.getString("name"));
                        genderComboBox.setSelectedItem(rs.getString("gender"));
                        addressField.setText(rs.getString("address"));
                        stateField.setText(rs.getString("state"));
                        cityField.setText(rs.getString("city"));
                        emailField.setText(rs.getString("email"));
                        phoneField.setText(rs.getString("phone"));
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateEmployee(ActionEvent e) {
        String empId = (String) empIdComboBox.getSelectedItem();
        String name = nameField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String address = addressField.getText();
        String state = stateField.getText();
        String city = cityField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        String sql = "UPDATE employee SET name = ?, gender = ?, address = ?, state = ?, city = ?, email = ?, phone = ? WHERE emp_id = ?";

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, gender);
            ps.setString(3, address);
            ps.setString(4, state);
            ps.setString(5, city);
            ps.setString(6, email);
            ps.setString(7, phone);
            ps.setString(8, empId);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee updated successfully.");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee(ActionEvent e) {
        String empId = (String) empIdComboBox.getSelectedItem();
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete employee " + empId + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            String deleteEmployeeSql = "DELETE FROM employee WHERE emp_id = ?";
            String deleteLoginSql = "DELETE FROM login WHERE username = ?";
            try (Connection conn = Conn.getConnection()) {
                conn.setAutoCommit(false);
                try (PreparedStatement empPs = conn.prepareStatement(deleteEmployeeSql);
                     PreparedStatement loginPs = conn.prepareStatement(deleteLoginSql)) {
                    empPs.setString(1, empId);
                    empPs.executeUpdate();

                    loginPs.setString(1, empId);
                    loginPs.executeUpdate();

                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
                    dispose();
                } catch (SQLException ex) {
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, "Error deleting employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database connection error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdateEmployee().setVisible(true));
    }
}
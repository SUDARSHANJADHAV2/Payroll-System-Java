import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class NewEmployee extends JFrame {
    private final JTextField nameField, addressField, stateField, cityField, emailField, phoneField;
    private final JPasswordField passwordField;
    private final JComboBox<String> genderComboBox;

    public NewEmployee() {
        super("Add New Employee");
        setSize(400, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
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

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

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

        submitButton.addActionListener(this::addEmployee);
        cancelButton.addActionListener(e -> dispose());
    }

    private void addEmployee(ActionEvent e) {
        String name = nameField.getText();
        String gender = (String) genderComboBox.getSelectedItem();
        String address = addressField.getText();
        String state = stateField.getText();
        String city = cityField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = new String(passwordField.getPassword());
        String empId = generateEmployeeId();

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String employeeSql = "INSERT INTO employee(emp_id, name, gender, address, state, city, email, phone) VALUES(?,?,?,?,?,?,?,?)";
        String loginSql = "INSERT INTO login(username, password, role) VALUES(?,?,?)";

        try (Connection conn = Conn.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement empPs = conn.prepareStatement(employeeSql);
                 PreparedStatement loginPs = conn.prepareStatement(loginSql)) {

                empPs.setString(1, empId);
                empPs.setString(2, name);
                empPs.setString(3, gender);
                empPs.setString(4, address);
                empPs.setString(5, state);
                empPs.setString(6, city);
                empPs.setString(7, email);
                empPs.setString(8, phone);
                empPs.executeUpdate();

                loginPs.setString(1, empId);
                loginPs.setString(2, hashedPassword);
                loginPs.setString(3, "employee"); // Default role
                loginPs.executeUpdate();

                conn.commit();
                JOptionPane.showMessageDialog(this, "Employee added successfully. Employee ID: " + empId);
                dispose();
            } catch (SQLException ex) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Error adding employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database connection error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateEmployeeId() {
        try {
            return DatabaseUtils.getNextEmployeeId();
        } catch (Exception e) {
            return "EMP" + String.format("%04d", new Random().nextInt(10000));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NewEmployee().setVisible(true));
    }
}
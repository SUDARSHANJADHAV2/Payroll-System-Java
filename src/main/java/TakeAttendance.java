import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TakeAttendance extends JFrame {
    private final JComboBox<String> empIdComboBox;
    private final JComboBox<String> firstHalfComboBox;
    private final JComboBox<String> secondHalfComboBox;

    public TakeAttendance() {
        super("Take Attendance");
        setSize(400, 300);
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
        gbc.gridx = 1;
        panel.add(empIdComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("First Half:"), gbc);
        firstHalfComboBox = new JComboBox<>(new String[]{"Present", "Absent"});
        gbc.gridx = 1;
        panel.add(firstHalfComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Second Half:"), gbc);
        secondHalfComboBox = new JComboBox<>(new String[]{"Present", "Absent"});
        gbc.gridx = 1;
        panel.add(secondHalfComboBox, gbc);

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

        submitButton.addActionListener(this::takeAttendance);
        cancelButton.addActionListener(e -> dispose());
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

    private void takeAttendance(ActionEvent e) {
        String empId = (String) empIdComboBox.getSelectedItem();
        String firstHalf = (String) firstHalfComboBox.getSelectedItem();
        String secondHalf = (String) secondHalfComboBox.getSelectedItem();
        LocalDate date = LocalDate.now();

        String sql = "INSERT INTO attendance(emp_id, date, first_half, second_half) VALUES(?,?,?,?)";

        try (Connection conn = Conn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, empId);
            ps.setObject(2, date);
            ps.setString(3, firstHalf);
            ps.setString(4, secondHalf);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Attendance taken successfully.");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error taking attendance: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TakeAttendance().setVisible(true));
    }
}
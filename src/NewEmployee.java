
//importing necessary java libraries
// import necessary packages for JDBC, Swing and AWT
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/*
 * Create a public class called NewEmployee that extends the JFrame class 
 * and implements the ActionListener interface
 */
public class NewEmployee extends JFrame implements ActionListener {
    // Declare the instance variables
    JLabel nameLabel, genderLabel, addressLabel, stateLabel, cityLabel, emailLabel, phoneLabel;
    JTextField nameCols, genderCols, addressCols, stateCols, cityCols, emailCols, phoneCols;
    JButton submitButton, cancelButton;
    Choice choice;

    // Create a constructor for the NewEmployee class
    NewEmployee() {

        // Call the constructor of the superclass JFrame and set the title of the window
        super("New Employee");

        // Set the size and location of the window, as well as the background color
        setSize(600, 650);
        setLocation(600, 200);
        getContentPane().setBackground(Color.WHITE); // Set the background color of the content pane to white

        // Create a JPanel and set the background color to white
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        /*
         * Set the layout of the panel to a grid
         * with 8 rows, 2 columns, and a 10 pixel horizontal and 40 pixel vertical gap
         * between components
         */
        panel.setLayout(new GridLayout(8, 2, 10, 40));

        // Create JLabels and JTextFields for each piece of employee information, and
        // add them to the panel
        nameLabel = new JLabel("Name");
        nameCols = new JTextField(15);
        panel.add(nameLabel);
        panel.add(nameCols);

        // Create a new choice object and add two options, "Male" and "Female"
        choice = new Choice();
        choice.add("Male");
        choice.add("Female");

        // Create a new label object for the gender and add the choice object to the
        // panel
        genderLabel = new JLabel("Gender");
        panel.add(genderLabel);
        panel.add(choice);

        // Create a new label object for the address and a new text field object with 15
        // columns for inputting the address
        addressLabel = new JLabel("Address");
        addressCols = new JTextField(15);

        // Add the address label and text field to the panel
        panel.add(addressLabel);
        panel.add(addressCols);

        // Create a new label object for the state and a new text field object with 15
        // columns for inputting the state
        stateLabel = new JLabel("State");
        stateCols = new JTextField(15);

        // Add the state label and text field to the panel
        panel.add(stateLabel);
        panel.add(stateCols);

        // Create a new label object for the city and a new text field object with 15
        // columns for inputting the city
        cityLabel = new JLabel("City");
        cityCols = new JTextField(15);

        // Add the city label and text field to the panel
        panel.add(cityLabel);
        panel.add(cityCols);

        // Create a new label object for the email and a new text field object with 15
        // columns for inputting the email
        emailLabel = new JLabel("Email");
        emailCols = new JTextField(15);

        // Add the email label and text field to the panel
        panel.add(emailLabel);
        panel.add(emailCols);

        // Create a new label object for the phone number and a new text field object
        // with 15 columns for inputting the phone number
        phoneLabel = new JLabel("Phone");
        phoneCols = new JTextField(15);
        panel.add(phoneLabel);
        panel.add(phoneCols);

        // Create JButtons for submitting or canceling employee creation, and add them
        // to the panel
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        panel.add(submitButton);
        panel.add(cancelButton);

        // Set the layout of the NewEmployee JFrame as a BorderLayout
        setLayout(new BorderLayout());
        // Add an image to the left side of the JFrame using a JLabel and an ImageIcon
        add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("icon/new_employee.png"))), "West");
        add(panel, "Center"); // Add the panel to the center of the JFrame

        // Add ActionListeners to the submit and cancel buttons, and set their
        // background and foreground colors
        submitButton.addActionListener(this);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);

        cancelButton.addActionListener(this);
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);

    }

    /**
     * Handle button click events
     */
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submitButton) {
            addNewEmployee();
        } else if (ae.getSource() == cancelButton) {
            this.dispose();
        }
    }

    /**
     * Add new employee to database with validation and security
     */
    private void addNewEmployee() {
        // Get and validate input values
        String name = nameCols.getText().trim();
        String gender = choice.getSelectedItem();
        String address = addressCols.getText().trim();
        String state = stateCols.getText().trim();
        String city = cityCols.getText().trim();
        String email = emailCols.getText().trim();
        String phone = phoneCols.getText().trim();

        // Validate required fields
        if (name.isEmpty()) {
            showValidationError("Name is required!");
            nameCols.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            showValidationError("Phone number is required!");
            phoneCols.requestFocus();
            return;
        }

        // Validate email format if provided
        if (!email.isEmpty() && !isValidEmail(email)) {
            showValidationError("Please enter a valid email address!");
            emailCols.requestFocus();
            return;
        }

        // Validate phone number format
        if (!isValidPhone(phone)) {
            showValidationError("Please enter a valid phone number (digits and dashes only)!");
            phoneCols.requestFocus();
            return;
        }

        // Generate unique employee ID
        String empId = generateEmployeeId();

        Conn conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = new Conn();

            // Use PreparedStatement for secure database insertion
            String query = "INSERT INTO employee (emp_id, name, gender, address, state, city, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, empId);
            pstmt.setString(2, name);
            pstmt.setString(3, gender);
            pstmt.setString(4, address);
            pstmt.setString(5, state);
            pstmt.setString(6, city);
            pstmt.setString(7, email.isEmpty() ? null : email);
            pstmt.setString(8, phone);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this,
                        "Employee created successfully!\nEmployee ID: " + empId,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Clear form fields
                clearForm();
                this.dispose();
            } else {
                showValidationError("Failed to create employee. Please try again.");
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry error
                showValidationError("Employee with this ID already exists. Please try again.");
            } else {
                showValidationError("Database error: " + e.getMessage());
            }
            System.err.println("Error creating employee: " + e.getMessage());
        } finally {
            // Clean up resources
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

    /**
     * Generate unique employee ID
     */
    private String generateEmployeeId() {
        try {
            return DatabaseUtils.getNextEmployeeId();
        } catch (Exception e) {
            // Fallback to random generation if database is not available
            return "EMP" + String.format("%04d", new Random().nextInt(9999) + 1);
        }
    }

    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    }

    /**
     * Validate phone number format
     */
    private boolean isValidPhone(String phone) {
        return phone.matches("^[0-9-+()\\s]+$") && phone.replaceAll("[^0-9]", "").length() >= 10;
    }

    /**
     * Show validation error message
     */
    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Clear all form fields
     */
    private void clearForm() {
        nameCols.setText("");
        addressCols.setText("");
        stateCols.setText("");
        cityCols.setText("");
        emailCols.setText("");
        phoneCols.setText("");
        choice.select(0);
    }

    // main method to create and display a new instance of the NewEmployee class
    public static void main(String s[]) {
        new NewEmployee().setVisible(true);
    }
}

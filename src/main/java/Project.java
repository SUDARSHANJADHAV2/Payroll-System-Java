import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Project extends JFrame {

    public Project() {
        super(Config.getInstance().getAppName() + " - " + UserSession.getDisplayName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Set a modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + UserSession.getCurrentUsername(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        // Menu bar
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        setContentPane(contentPanel);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Master Menu
        JMenu masterMenu = new JMenu("Master");
        masterMenu.add(createMenuItem("New Employee", 'N', KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), () -> new NewEmployee().setVisible(true)));
        masterMenu.add(createMenuItem("List Employees", 'L', KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK), () -> new ListEmployee().setVisible(true)));
        masterMenu.add(createMenuItem("Salary", 'S', KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), () -> new Salary().setVisible(true)));
        menuBar.add(masterMenu);

        // Update Menu
        JMenu updateMenu = new JMenu("Update");
        updateMenu.add(createMenuItem("Update Employee", 'U', KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK), () -> new UpdateEmployee().setVisible(true)));
        updateMenu.add(createMenuItem("Update Salary", 'P', KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK), () -> new UpdateSalary().setVisible(true)));
        updateMenu.add(createMenuItem("Take Attendance", 'T', KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK), () -> new TakeAttendance().setVisible(true)));
        menuBar.add(updateMenu);

        // Reports Menu
        JMenu reportsMenu = new JMenu("Reports");
        reportsMenu.add(createMenuItem("Generate Payslip", 'G', KeyStroke.getKeyStroke(KeyEvent.VK_G, KeyEvent.CTRL_DOWN_MASK), () -> new PaySlip().setVisible(true)));
        reportsMenu.add(createMenuItem("List Attendance", 'A', KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK), () -> new ListAttendance().setVisible(true)));
        menuBar.add(reportsMenu);

        // Utilities Menu
        JMenu utilitiesMenu = new JMenu("Utilities");
        utilitiesMenu.add(createMenuItem("Notepad", 'O', KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK), this::openNotepad));
        utilitiesMenu.add(createMenuItem("Calculator", 'C', KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK), this::openCalculator));
        utilitiesMenu.add(createMenuItem("Web Browser", 'E', KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK), this::openWebBrowser));
        menuBar.add(utilitiesMenu);

        // System Menu
        JMenu systemMenu = new JMenu("System");
        systemMenu.add(createMenuItem("Toggle Theme", 'T', KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.ALT_DOWN_MASK), ThemeManager::toggleTheme));
        systemMenu.addSeparator();
        systemMenu.add(createMenuItem("Logout", 'L', KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.ALT_DOWN_MASK), this::handleLogout));
        systemMenu.add(createMenuItem("Exit", 'X', KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.ALT_DOWN_MASK), this::handleExit));
        menuBar.add(systemMenu);

        return menuBar;
    }

    private JMenuItem createMenuItem(String text, char mnemonic, KeyStroke accelerator, Runnable action) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setMnemonic(mnemonic);
        menuItem.setAccelerator(accelerator);
        menuItem.addActionListener(e -> action.run());
        return menuItem;
    }

    private void openExternalApp(String... commands) {
        for (String command : commands) {
            try {
                Runtime.getRuntime().exec(command);
                return;
            } catch (Exception e) {
                // Try the next command
            }
        }
        JOptionPane.showMessageDialog(this, "Could not open the requested utility.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void openNotepad() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) openExternalApp("notepad.exe");
        else if (os.contains("mac")) openExternalApp("open -a TextEdit");
        else openExternalApp("gedit", "kate", "xed");
    }

    private void openCalculator() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) openExternalApp("calc.exe");
        else if (os.contains("mac")) openExternalApp("open -a Calculator");
        else openExternalApp("gnome-calculator", "kcalc");
    }

    private void openWebBrowser() {
        openExternalApp("xdg-open http://www.google.com", "open http://www.google.com", "rundll32 url.dll,FileProtocolHandler http://www.google.com");
    }

    private void handleLogout() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            UserSession.logout();
            dispose();
            SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        }
    }

    private void handleExit() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // This is for testing purposes. The main entry point is Splash.java
        SwingUtilities.invokeLater(() -> {
            UserSession.setCurrentUser("testuser", "admin");
            new Project().setVisible(true);
        });
    }
}
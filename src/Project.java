import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Project extends JFrame implements ActionListener {

    Project() {
        // Set window title with user information
        String title = Config.getInstance().getAppName();
        if (UserSession.isLoggedIn()) {
            title += " - " + UserSession.getDisplayName();
        }
        setTitle(title);

        setSize(1400, 800);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        ImageIcon imageIcon1 = new ImageIcon(ClassLoader.getSystemResource("icon/payroll.jpg"));
        Image imageIcon2 = imageIcon1.getImage().getScaledInstance(1200, 700, Image.SCALE_DEFAULT);
        ImageIcon imageIcon3 = new ImageIcon(imageIcon2);
        JLabel label1 = new JLabel(imageIcon3);
        add(label1);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu1 = new JMenu("Master");
        menu1.setForeground(Color.blue);

        JMenuItem menuItem1 = new JMenuItem("New Employee");

        menuItem1.setForeground(Color.blue);
        menuItem1.setFont(new Font("monospaced", Font.PLAIN, 12));
        menuItem1.setMnemonic('N');
        menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem1.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/New.png")));

        JMenuItem menuItem3 = new JMenuItem("Salary");
        menuItem3.setForeground(Color.blue);
        menuItem3.setFont(new Font("monospaced", Font.PLAIN, 12));
        menuItem3.setMnemonic('S');
        menuItem3.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/schedreport.png")));
        menuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        JMenuItem menuItem4 = new JMenuItem("List Employee");

        menuItem4.setForeground(Color.blue);
        menuItem4.setFont(new Font("monospaced", Font.PLAIN, 12));
        menuItem4.setMnemonic('L');
        menuItem4.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/newinvoice.png")));
        menuItem4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));

        menu1.add(menuItem1);
        menu1.add(menuItem3);
        menu1.add(menuItem4);
        menuBar.add(menu1);

        menuItem1.addActionListener(this);
        menuItem3.addActionListener(this);
        menuItem4.addActionListener(this);

        JMenu edit = new JMenu("Update");
        edit.setForeground(Color.RED);

        menuBar.add(edit);
        JMenuItem menuItemUpdateSal = new JMenuItem("Update Salary");
        menuItemUpdateSal.setForeground(Color.blue);
        menuItemUpdateSal.setFont(new Font("monospaced", Font.PLAIN, 12));
        menuItemUpdateSal.setMnemonic('U');
        menuItemUpdateSal.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/EditOpen.png")));
        menuItemUpdateSal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));

        edit.add(menuItemUpdateSal);

        JMenuItem menuItemUpdateEmp = new JMenuItem("Update Employee");

        menuItemUpdateEmp.setForeground(Color.blue);
        menuItemUpdateEmp.setFont(new Font("monospaced", Font.PLAIN, 12));
        menuItemUpdateEmp.setMnemonic('p');
        menuItemUpdateEmp.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/empreport.png")));
        menuItemUpdateEmp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        edit.add(menuItemUpdateEmp);
        JMenuItem menuTakeAtt = new JMenuItem("Take Attendance");

        menuTakeAtt.setForeground(Color.blue);
        menuTakeAtt.setFont(new Font("monospaced", Font.PLAIN, 12));
        menuTakeAtt.setMnemonic('T');
        menuTakeAtt.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/EXPENSE.PNG")));
        menuTakeAtt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));

        edit.add(menuTakeAtt);

        menuItemUpdateSal.addActionListener(this);
        menuItemUpdateEmp.addActionListener(this);
        menuTakeAtt.addActionListener(this);

        JMenu reports = new JMenu("Reports");
        reports.setForeground(Color.blue);

        menuBar.add(reports);
        JMenuItem genPaySlip = new JMenuItem("Generate PaySlip");

        genPaySlip.setForeground(Color.blue);
        genPaySlip.setFont(new Font("monospaced", Font.PLAIN, 12));
        genPaySlip.setMnemonic('P');
        genPaySlip.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/payments.png")));
        genPaySlip.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        reports.add(genPaySlip);
        JMenuItem listAttendance = new JMenuItem("List Attendance");

        listAttendance.setForeground(Color.blue);
        listAttendance.setFont(new Font("monospaced", Font.PLAIN, 12));
        listAttendance.setMnemonic('L');
        listAttendance.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/empreport.png")));
        listAttendance.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));

        reports.add(listAttendance);
        genPaySlip.addActionListener(this);
        listAttendance.addActionListener(this);

        JMenu util = new JMenu("Utilities");
        util.setForeground(Color.red);

        menuBar.add(util);
        JMenuItem u1 = new JMenuItem("Notepad");

        u1.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/New.png")));

        u1.setForeground(Color.blue);
        u1.setFont(new Font("monospaced", Font.PLAIN, 12));
        u1.setMnemonic('o');
        u1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        util.add(u1);
        JMenuItem u2 = new JMenuItem("Calculator");
        u2.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/calc.png")));

        u2.setForeground(Color.blue);
        u2.setFont(new Font("monospaced", Font.PLAIN, 12));
        u2.setMnemonic('C');
        u2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        util.add(u2);
        JMenuItem u3 = new JMenuItem("Web Browser");
        u3.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/explorer.jpg")));

        u3.setForeground(Color.blue);
        u3.setFont(new Font("monospaced", Font.PLAIN, 12));
        u3.setMnemonic('E');
        u3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        util.add(u3);

        u1.addActionListener(this);
        u2.addActionListener(this);
        u3.addActionListener(this);

        JMenu m8 = new JMenu("System");
        m8.setForeground(Color.red);
        menuBar.add(m8);

        JMenuItem logout = new JMenuItem("Logout");
        logout.setForeground(Color.blue);
        logout.setFont(new Font("monospaced", Font.PLAIN, 14));
        logout.setMnemonic('L');
        logout.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/exit.PNG")));
        logout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        logout.addActionListener(this);
        m8.add(logout);

        JMenuItem m8i1 = new JMenuItem("Exit");
        m8.add(m8i1);
        m8i1.setForeground((Color.blue));
        m8i1.setFont(new Font("monospaced", Font.PLAIN, 14));
        m8i1.setMnemonic('X');
        m8i1.setIcon(new ImageIcon(ClassLoader.getSystemResource("icon/exit.PNG")));
        m8i1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        m8i1.addActionListener(this);

    }

    public void actionPerformed(ActionEvent ae) {
        String msg = ae.getActionCommand();

        if (msg.equals("New Employee"))
            new NewEmployee().setVisible(true);
        else if (msg.equals("List Employee"))
            new ListEmployee().setVisible(true);
        else if (msg.equals("Update Employee"))
            new UpdateEmployee().setVisible(true);
        else if (msg.equals("Salary"))
            new Salary().setVisible(true);
        else if (msg.equals("Update Salary"))
            new UpdateSalary().setVisible(true);
        else if (msg.equals("Notepad")) {
            openNotepad();
        } else if (msg.equals("Calculator")) {
            openCalculator();
        } else if (msg.equals("Web Browser")) {
            openWebBrowser();
        } else if (msg.equals("Take Attendance")) {
            new TakeAttendance().setVisible(true);
        } else if (msg.equals("Logout")) {
            handleLogout();
        } else if (msg.equals("Exit")) {
            handleExit();
        } else if (msg.equals("Generate PaySlip")) {
            new PaySlip().setVisible(true);
        } else if (msg.equals("List Attendance")) {
            new ListAttendance().setVisible(true);
        }
    }

    /**
     * Open notepad/text editor based on operating system
     */
    private void openNotepad() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Runtime.getRuntime().exec("notepad.exe");
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open -a TextEdit");
            } else {
                // Linux/Unix
                Runtime.getRuntime().exec("gedit");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Could not open text editor: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Open calculator application based on operating system
     */
    private void openCalculator() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Runtime.getRuntime().exec("calc.exe");
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open -a Calculator");
            } else {
                // Linux/Unix - try multiple options
                try {
                    Runtime.getRuntime().exec("gnome-calculator");
                } catch (Exception e1) {
                    Runtime.getRuntime().exec("kcalc");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Could not open calculator: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Open web browser based on operating system
     */
    private void openWebBrowser() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Try Edge first, then fallback to default browser
                try {
                    Runtime.getRuntime().exec("msedge");
                } catch (Exception e1) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://www.google.com");
                }
            } else if (os.contains("mac")) {
                Runtime.getRuntime().exec("open http://www.google.com");
            } else {
                // Linux/Unix - try multiple browsers
                try {
                    Runtime.getRuntime().exec("firefox");
                } catch (Exception e1) {
                    try {
                        Runtime.getRuntime().exec("google-chrome");
                    } catch (Exception e2) {
                        Runtime.getRuntime().exec("xdg-open http://www.google.com");
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Could not open web browser: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Handle user logout
     */
    private void handleLogout() {
        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            UserSession.logout();
            this.dispose();
            new Login().setVisible(true);
        }
    }

    /**
     * Handle application exit
     */
    private void handleExit() {
        int option = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to exit the application?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            UserSession.logout();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new Project().setVisible(true);
    }

}
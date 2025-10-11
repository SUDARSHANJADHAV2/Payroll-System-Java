import javax.swing.*;

public class Splash {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ThemeManager.applyTheme();
            SplashScreen frame = new SplashScreen("Payroll System - Version 3.1");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(730, 550);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
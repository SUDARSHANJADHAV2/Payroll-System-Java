import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;

public class ThemeManager {
    private static final String LIGHT_THEME_CLASS = UIManager.getSystemLookAndFeelClassName();
    private static final String DARK_THEME_CLASS = "javax.swing.plaf.nimbus.NimbusLookAndFeel"; // A common dark theme

    private static boolean isDarkTheme = false;

    public static void applyTheme() {
        try {
            if (isDarkTheme) {
                UIManager.setLookAndFeel(DARK_THEME_CLASS);
                // Customize dark theme colors
                UIManager.put("control", new Color(128, 128, 128));
                UIManager.put("info", new Color(128, 128, 128));
                UIManager.put("nimbusBase", new Color(18, 30, 49));
                UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
                UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
                UIManager.put("nimbusFocus", new Color(115, 164, 209));
                UIManager.put("nimbusGreen", new Color(176, 179, 50));
                UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
                UIManager.put("nimbusLightBackground", new Color(18, 30, 49));
                UIManager.put("nimbusOrange", new Color(191, 98, 4));
                UIManager.put("nimbusRed", new Color(169, 46, 34));
                UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
                UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
                UIManager.put("text", new Color(230, 230, 230));
            } else {
                UIManager.setLookAndFeel(LIGHT_THEME_CLASS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        applyTheme();
        // Refresh all open windows to apply the new theme
        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
        }
    }

    public static boolean isDarkTheme() {
        return isDarkTheme;
    }
}
import java.awt.*;
import javax.swing.*;

public class SplashScreen extends JFrame implements Runnable {
    Thread t1;

    public SplashScreen(String s) {
        super(s);
        setLayout(new FlowLayout());
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/payroll_system.jpg"));
        Image imagee = imageIcon.getImage().getScaledInstance(730, 550, Image.SCALE_DEFAULT);
        ImageIcon imageIcon2 = new ImageIcon(imagee);

        JLabel label = new JLabel(imageIcon2);
        add(label);
        t1 = new Thread(this);
        t1.start();
    }

    public void run() {
        try {
            Thread.sleep(7000);
            this.setVisible(false);
            
            // Initialize database before showing login
            DatabaseInitializer.initializeDatabase();
            
            new Login();

        } catch (Exception e) {
            System.err.println("Error during startup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
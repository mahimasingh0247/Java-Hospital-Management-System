package Login;

import javax.swing.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {

        setTitle("Hospital Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create our background panel
        BackgroundPanel background = new BackgroundPanel();

        // Use BorderLayout for positioning
        background.setLayout(new java.awt.GridBagLayout());

        // Create our glass panel
        GlassPanel glass = new GlassPanel();

        // Give it some dimensions
        glass.setPreferredSize(new java.awt.Dimension(420, 400));

        // Place it in the center
        background.add(glass);

        // Add it to the window
        setContentPane(background);

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
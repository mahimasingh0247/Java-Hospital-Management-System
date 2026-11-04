package Login;

import javax.swing.*;
import java.awt.*;

public class GlassPanel extends JPanel {

    public GlassPanel() {

        // Make the panel transparent
        setOpaque(false);

        // Arrange components vertically
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add spacing inside the glass panel
        setBorder(BorderFactory.createEmptyBorder(
                35, 35, 35, 35
        ));

        // Hospital heading
        JLabel hospitalTitle =
                new JLabel("HOSPITAL MANAGEMENT SYSTEM");

        hospitalTitle.setForeground(Color.WHITE);
        hospitalTitle.setFont(new Font("Arial", Font.BOLD, 15));
        hospitalTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Welcome message
        JLabel welcome =
                new JLabel("Welcome Back");

        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Arial", Font.BOLD, 30));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle =
                new JLabel("Sign in to continue");

        subtitle.setForeground(new Color(225, 235, 255));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username field
        JTextField username = new JTextField();
        username.setMaximumSize(new Dimension(300, 40));
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        username.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(190, 205, 230)),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );


        // Password field
        JPasswordField password = new JPasswordField();
        password.setMaximumSize(new Dimension(300, 40));
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        password.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(190, 205, 230)),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );

        // Sign In button
        JButton signIn = new JButton("Sign In");
        signIn.setMaximumSize(new Dimension(300, 42));
        signIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signIn.setFont(new Font("Arial", Font.BOLD, 16));
        signIn.setFocusPainted(false);
        signIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add everything to the panel
        add(hospitalTitle);
        add(Box.createVerticalStrut(15));

        add(welcome);
        add(Box.createVerticalStrut(5));

        add(subtitle);
        add(Box.createVerticalStrut(30));

        add(username);
        add(Box.createVerticalStrut(15));

        add(password);
        add(Box.createVerticalStrut(25));

        add(signIn);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // Convert Graphics into Graphics2D
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable smooth edges
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Set translucent bright color
        g2.setColor(new Color(220, 230, 255, 65));

        // Draw rounded glass panel
        g2.fillRoundRect(
                0, 0,
                getWidth(), getHeight(),
                30, 30
        );

        // Add subtle border
        g2.setColor(new Color(255, 255, 255, 65));

        g2.setStroke(new BasicStroke(1.5f));

        g2.drawRoundRect(
                0, 0,
                getWidth() - 1,
                getHeight() - 1,
                30, 30
        );

        // Dispose of the copied graphics context
        g2.dispose();
    }
}

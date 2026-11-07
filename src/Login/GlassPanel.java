
package Login;

import Dashboard.DashboardFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

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
                new JLabel("Sign in or register to continue");

        subtitle.setForeground(new Color(225, 235, 255));
        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Username field
        JTextField username = new JTextField();
        username.setMaximumSize(new Dimension(300, 40));
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        username.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(190, 205, 230)),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                )
        );

        // Password field
        JPasswordField password = new JPasswordField();
        password.setMaximumSize(new Dimension(300, 40));
        password.setFont(new Font("Arial", Font.PLAIN, 16));
        password.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(190, 205, 230)),
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

        // Register button
        JButton register = new JButton("Register");
        register.setMaximumSize(new Dimension(300, 42));
        register.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setFont(new Font("Arial", Font.BOLD, 16));
        register.setFocusPainted(false);
        register.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Sign In action
        signIn.addActionListener(e -> {

            String enteredUsername = username.getText().trim();
            char[] enteredPassword = password.getPassword();

            try {

                if (enteredUsername.isEmpty()
                        || enteredPassword.length == 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter your username and password.",
                            "Login Required",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                boolean authenticated =
                        AuthenticationService.authenticate(
                                enteredUsername, enteredPassword
                        );

                if (authenticated) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Login successful!",
                            "Welcome",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    // Close Login window
                    Window window =
                            SwingUtilities.getWindowAncestor(this);

                    if (window != null) {
                        window.dispose();
                    }

                    // Open Dashboard
                    new DashboardFrame();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid username or password.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login error: " + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

            } finally {
                Arrays.fill(enteredPassword, '\0');
                password.setText("");
            }
        });

        // Registration action
        register.addActionListener(e -> {

            String enteredUsername = username.getText().trim();
            char[] enteredPassword = password.getPassword();

            try {

                if (enteredUsername.isEmpty()
                        || enteredPassword.length == 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a username and password.",
                            "Registration Required",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                AuthenticationService.RegistrationResult result =
                        AuthenticationService.registerUser(
                                enteredUsername, enteredPassword
                        );

                switch (result) {

                    case REGISTERED:

                        JOptionPane.showMessageDialog(
                                this,
                                "Registration successful! "
                                        + "You can now sign in.",
                                "Account Created",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        password.setText("");
                        break;

                    case USERNAME_EXISTS:

                        JOptionPane.showMessageDialog(
                                this,
                                "This username is already registered.",
                                "Registration Failed",
                                JOptionPane.WARNING_MESSAGE
                        );

                        break;

                    case INVALID_USERNAME:

                        JOptionPane.showMessageDialog(
                                this,
                                "Username must contain English letters only.",
                                "Invalid Username",
                                JOptionPane.WARNING_MESSAGE
                        );

                        break;

                    case INVALID_PASSWORD:

                        JOptionPane.showMessageDialog(
                                this,
                                "Password must contain digits only.",
                                "Invalid Password",
                                JOptionPane.WARNING_MESSAGE
                        );

                        break;
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration error: " + ex.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

            } finally {
                Arrays.fill(enteredPassword, '\0');
                password.setText("");
            }
        });

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
        add(Box.createVerticalStrut(10));

        add(register);
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

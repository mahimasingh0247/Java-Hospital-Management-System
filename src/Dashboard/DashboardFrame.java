package Dashboard;

import Login.BackgroundPanel;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {

        // Window settings
        setTitle("Hospital Management System");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main background
        BackgroundPanel background = new BackgroundPanel();
        background.setLayout(new BorderLayout());

        // Create sidebar
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(230, 0));
        sidebar.setBackground(new Color(18, 36, 65));
        sidebar.setLayout(new BoxLayout(
                sidebar, BoxLayout.Y_AXIS
        ));

        // Sidebar heading
        JLabel heading = new JLabel("HOSPITAL");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setBorder(
                BorderFactory.createEmptyBorder(30, 25, 30, 10)
        );

        sidebar.add(heading);

        // Navigation buttons

        String[] menuItems = {
                "Dashboard",
                "Login",
                "Patients",
                "Doctors",
                "Appointments",
                "Billing"
        };

        for (String item : menuItems) {

            JButton button = new JButton(item);

            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(18, 36, 65));

            button.setBorder(
                    BorderFactory.createEmptyBorder(15, 25, 15, 10)
            );

            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setFocusPainted(false);

            button.setCursor(
                    new Cursor(Cursor.HAND_CURSOR)
            );

            button.setMaximumSize(
                    new Dimension(Integer.MAX_VALUE, 50)
            );

            // Highlight the currently selected section
            if (item.equals("Dashboard")) {

                button.setBackground(new Color(40, 70, 110));
                button.setFont(new Font("Arial", Font.BOLD, 16));
            }

            // Open Login screen
            if (item.equals("Login")) {

                button.addActionListener(e -> {

                    dispose();

                    new Login.LoginFrame();
                });
            }

            sidebar.add(button);
        }

        // Push Logout towards the bottom
        sidebar.add(Box.createVerticalGlue());

        // Logout button
        JButton logoutButton = new JButton("Logout");

        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(18, 36, 65));

        logoutButton.setBorder(
                BorderFactory.createEmptyBorder(15, 25, 15, 10)
        );

        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logoutButton.setFocusPainted(false);

        logoutButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        logoutButton.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 50)
        );

        // Logout action
        logoutButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                dispose();

                new Login.LoginFrame();
            }
        });

        sidebar.add(logoutButton);


        // Main content area
        DashboardContent content = new DashboardContent();

        // Assemble the dashboard
        background.add(sidebar, BorderLayout.WEST);
        background.add(content, BorderLayout.CENTER);

        setContentPane(background);

        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardFrame::new);
    }
}
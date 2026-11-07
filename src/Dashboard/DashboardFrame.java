package Dashboard;

import Login.BackgroundPanel;
import Billing.BillingFrame;
import Hospital.PatientUI;
import Hospital.DoctorUI;
import Hospital.AppointmentUI;

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
                "Patients",
                "Doctors",
                "Appointments",
                "Billing",
                "Login"
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

            // Open Patient Management screen
            if (item.equals("Patients")) {

                button.addActionListener(e -> {

                    PatientUI patientWindow = new PatientUI();

                    patientWindow.setVisible(true);
                });
            }

            // Open Doctor Management screen
            if (item.equals("Doctors")) {

                button.addActionListener(e -> {

                    new DoctorUI();

                });
            }

            // Open Appointment Management screen
            if (item.equals("Appointments")) {
                button.addActionListener(e -> {
                    new AppointmentUI();
                });
            }

            // Open Billing screen
            if (item.equals("Billing")) {

                button.addActionListener(e -> {

                    JFrame billingWindow =
                            new JFrame("Hospital Management - Billing");

                    billingWindow.setDefaultCloseOperation(
                            JFrame.DISPOSE_ON_CLOSE
                    );

                    // Use the same marble background
                    BackgroundPanel billingBackground =
                            new BackgroundPanel();

                    billingBackground.setLayout(new BorderLayout());

                    // Add Billing screen
                    billingBackground.add(
                            new BillingFrame(),
                            BorderLayout.CENTER
                    );

                    billingWindow.setContentPane(billingBackground);

                    billingWindow.setSize(1200, 750);
                    billingWindow.setLocationRelativeTo(null);
                    billingWindow.setVisible(true);
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
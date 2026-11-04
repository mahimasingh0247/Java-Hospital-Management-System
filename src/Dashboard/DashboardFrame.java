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

            sidebar.add(button);
        }

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
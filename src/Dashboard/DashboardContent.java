
package Dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardContent extends JPanel {

    public DashboardContent() {

        // Main content layout
        setLayout(new BorderLayout());
        setOpaque(false);

        // Inner spacing
        setBorder(new EmptyBorder(35, 35, 35, 35));

        // Welcome section
        JPanel header = new JPanel();

        header.setLayout(
                new BoxLayout(header, BoxLayout.Y_AXIS)
        );

        header.setOpaque(false);

        // Welcome message
        JLabel title = new JLabel("Welcome Back!");

        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle = new JLabel(
                "Here's an overview of your hospital."
        );

        subtitle.setFont(new Font("Arial", Font.BOLD, 15));
        subtitle.setForeground(
                new Color(235, 242, 255)
        );
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);

        // Summary cards
        JPanel cards = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 18, 0)
        );

        cards.setOpaque(false);


        cards.add(createCard("Patients", "—"));
        cards.add(createCard("Doctors", "—"));
        cards.add(createCard("Appointments", "—"));
        cards.add(createCard("Billing", "—"));

        // Stack heading and cards vertically
        JPanel contentStack = new JPanel();

        contentStack.setLayout(
                new BoxLayout(contentStack, BoxLayout.Y_AXIS)
        );

        contentStack.setOpaque(false);

        contentStack.add(header);
        contentStack.add(Box.createVerticalStrut(20));
        contentStack.add(cards);

        // Add everything to the main panel
        add(contentStack, BorderLayout.NORTH);
    }

    // Method to create a summary card
    private JPanel createCard(String heading, String value) {

        JPanel card = new JPanel();

        card.setLayout(
                new BoxLayout(card, BoxLayout.Y_AXIS)
        );

        card.setPreferredSize(
                new Dimension(190, 125)
        );

        card.setBackground(
                new Color(255, 255, 255, 220)
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                new Color(190, 210, 235)
                        ),

                        new EmptyBorder(12, 18, 12, 18)
                )
        );

        // Card heading
        JLabel headingLabel = new JLabel(heading);

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        headingLabel.setForeground(
                new Color(65, 85, 110)
        );

        // Card value
        JLabel valueLabel = new JLabel(value);

        valueLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        valueLabel.setForeground(
                new Color(25, 55, 95)
        );

        card.add(headingLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(valueLabel);

        return card;
    }
}

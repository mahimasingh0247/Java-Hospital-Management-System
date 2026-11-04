
package Billing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BillingFrame extends JPanel {

    // Input fields
    private JTextField patientIdField;
    private JTextField patientNameField;

    private JTextField doctorFeesField;
    private JTextField medicineChargesField;
    private JTextField roomChargesField;
    private JTextField testChargesField;
    private JTextField discountField;

    private JComboBox<String> paymentStatus;

    public BillingFrame() {

        // Main panel settings
        setLayout(new BorderLayout(0, 25));
        setOpaque(false);

        setBorder(new EmptyBorder(35, 35, 35, 35));

        // Page heading
        JLabel heading = new JLabel("Billing Management");

        heading.setFont(new Font("Arial", Font.BOLD, 30));
        heading.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Create and manage patient bills."
        );

        subtitle.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitle.setForeground(new Color(235, 242, 255));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        header.add(heading);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // Main billing form
        JPanel form = new JPanel(new GridBagLayout());

        form.setBackground(new Color(255, 255, 255, 225));

        form.setBorder(
                new EmptyBorder(25, 30, 25, 30)
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Patient information heading
        addSectionHeading(
                form, gbc, "Patient Information", 0
        );

        patientIdField = createField();
        patientNameField = createField();

        addFormField(form, gbc, "Patient ID", patientIdField, 1, 0);
        addFormField(form, gbc, "Patient Name", patientNameField, 1, 1);

        // Charges heading
        addSectionHeading(
                form, gbc, "Bill Charges", 2
        );

        doctorFeesField = createField();
        medicineChargesField = createField();
        roomChargesField = createField();
        testChargesField = createField();
        discountField = createField();

        addFormField(form, gbc, "Doctor Fees (₹)", doctorFeesField, 3, 0);
        addFormField(form, gbc, "Medicine Charges (₹)", medicineChargesField, 3, 1);

        addFormField(form, gbc, "Room Charges (₹)", roomChargesField, 4, 0);
        addFormField(form, gbc, "Test Charges (₹)", testChargesField, 4, 1);

        addFormField(form, gbc, "Discount (₹)", discountField, 5, 0);

        // Payment status
        paymentStatus = new JComboBox<>(
                new String[]{"Pending", "Paid"}
        );

        paymentStatus.setFont(new Font("Arial", Font.PLAIN, 14));

        addFormField(
                form, gbc, "Payment Status", paymentStatus, 5, 1
        );

        // Total amount display
        JLabel totalLabel = new JLabel("Total Amount: ₹ 0.00");

        totalLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        totalLabel.setForeground(new Color(25, 55, 95));

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 10, 15, 10);

        form.add(totalLabel, gbc);

        // Generate bill button
        JButton generateButton = new JButton("Generate Bill");

        generateButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        generateButton.setForeground(Color.WHITE);
        generateButton.setBackground(new Color(25, 65, 110));
        generateButton.setFocusPainted(false);

        generateButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;

        form.add(generateButton, gbc);

        add(form, BorderLayout.CENTER);
    }

    // Create a styled text field
    private JTextField createField() {

        JTextField field = new JTextField();

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 35));

        return field;
    }

    // Add a section heading
    private void addSectionHeading(
            JPanel panel,
            GridBagConstraints gbc,
            String text,
            int row
    ) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        label.setForeground(new Color(25, 55, 95));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 8, 10);

        panel.add(label, gbc);

        gbc.gridwidth = 1;
    }

    // Add a label and its input field
    private void addFormField(
            JPanel panel,
            GridBagConstraints gbc,
            String labelText,
            JComponent field,
            int row,
            int column
    ) {

        JPanel fieldPanel = new JPanel();

        fieldPanel.setLayout(
                new BoxLayout(fieldPanel, BoxLayout.Y_AXIS)
        );

        fieldPanel.setOpaque(false);

        JLabel label = new JLabel(labelText);

        label.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        label.setForeground(new Color(65, 85, 110));

        fieldPanel.add(label);
        fieldPanel.add(Box.createVerticalStrut(6));
        fieldPanel.add(field);

        gbc.gridx = column;
        gbc.gridy = row;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(fieldPanel, gbc);
    }
    // Temporary method to test Billing screen
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Hospital Management System");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 750);
            frame.setLocationRelativeTo(null);

            // Use the existing marble background
            Login.BackgroundPanel background =
                    new Login.BackgroundPanel();

            background.setLayout(new BorderLayout());

            // Add Billing screen
            background.add(
                    new BillingFrame(),
                    BorderLayout.CENTER
            );

            frame.setContentPane(background);
            frame.setVisible(true);
        });
    }
}


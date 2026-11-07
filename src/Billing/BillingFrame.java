
package Billing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;

public class BillingFrame extends JPanel {

    // Patient information fields
    private JTextField patientIdField;
    private JTextField patientNameField;

    // Bill charge fields
    private JTextField doctorFeesField;
    private JTextField medicineChargesField;
    private JTextField roomChargesField;
    private JTextField testChargesField;
    private JTextField discountField;

    private JComboBox<String> paymentStatus;

    // Total amount label
    private JLabel totalLabel;

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
        totalLabel = new JLabel("Total Amount: ₹ 0.00");

        totalLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        totalLabel.setForeground(new Color(25, 55, 95));

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 10, 15, 10);

        form.add(totalLabel, gbc);

        // Automatically calculate total when charges change
        DocumentListener calculationListener = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateTotal();
            }
        };

        doctorFeesField.getDocument().addDocumentListener(calculationListener);
        medicineChargesField.getDocument().addDocumentListener(calculationListener);
        roomChargesField.getDocument().addDocumentListener(calculationListener);
        testChargesField.getDocument().addDocumentListener(calculationListener);
        discountField.getDocument().addDocumentListener(calculationListener);

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
        generateButton.addActionListener(e -> validateBill());

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

    // Calculate the bill total
    private void calculateTotal() {

        double doctorFees = getAmount(doctorFeesField);
        double medicineCharges = getAmount(medicineChargesField);
        double roomCharges = getAmount(roomChargesField);
        double testCharges = getAmount(testChargesField);
        double discount = getAmount(discountField);

        double subtotal = doctorFees
                + medicineCharges
                + roomCharges
                + testCharges;

        double total = subtotal - discount;

        totalLabel.setText(
                String.format("Total Amount: ₹ %.2f", total)
        );
    }

    // Convert text field input into a number
    private double getAmount(JTextField field) {

        try {

            String value = field.getText().trim();

            if (value.isEmpty()) {
                return 0;
            }

            return Double.parseDouble(value);

        } catch (NumberFormatException e) {

            return 0;
        }
    }

    private void validateBill() {

        // Check patient information
        if (patientIdField.getText().trim().isEmpty()
                || patientNameField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Patient ID and Patient Name.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Check all charge fields
        JTextField[] amountFields = {
                doctorFeesField,
                medicineChargesField,
                roomChargesField,
                testChargesField,
                discountField
        };

        for (JTextField field : amountFields) {

            String value = field.getText().trim();

            if (!value.isEmpty()) {
                try {
                    double amount = Double.parseDouble(value);

                    if (amount < 0) {
                        throw new NumberFormatException();
                    }

                } catch (NumberFormatException e) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter valid non-negative amounts.",
                            "Invalid Amount",
                            JOptionPane.WARNING_MESSAGE
                    );
                    field.requestFocus();
                    return;
                }
            }
        }

        // Calculate subtotal and discount
        double subtotal =
                getAmount(doctorFeesField)
                        + getAmount(medicineChargesField)
                        + getAmount(roomChargesField)
                        + getAmount(testChargesField);

        double discount = getAmount(discountField);

        if (discount > subtotal) {

            JOptionPane.showMessageDialog(
                    this,
                    "Discount cannot exceed the subtotal.",
                    "Invalid Discount",
                    JOptionPane.WARNING_MESSAGE
            );
            discountField.requestFocus();
            return;
        }

        saveBillToDatabase();
    }

    private void saveBillToDatabase() {

        try {

            int patientId = Integer.parseInt(
                    patientIdField.getText().trim()
            );

            BillingService service = new BillingService();

            int billId = service.saveBill(
                    patientId,
                    patientNameField.getText().trim(),
                    getBigDecimal(doctorFeesField),
                    getBigDecimal(medicineChargesField),
                    getBigDecimal(roomChargesField),
                    getBigDecimal(testChargesField),
                    getBigDecimal(discountField),
                    (String) paymentStatus.getSelectedItem()
            );

            showBillSummary();

            JOptionPane.showMessageDialog(
                    this,
                    "Bill saved successfully! Bill ID: " + billId,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Patient ID and amounts.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (IllegalArgumentException | SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Billing Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private BigDecimal getBigDecimal(JTextField field) {

        String value = field.getText().trim();

        if (value.isEmpty()) {
            return BigDecimal.ZERO;
        }

        return new BigDecimal(value);
    }


    private void showBillSummary() {

        double doctorFees = getAmount(doctorFeesField);
        double medicineCharges = getAmount(medicineChargesField);
        double roomCharges = getAmount(roomChargesField);
        double testCharges = getAmount(testChargesField);
        double discount = getAmount(discountField);

        double subtotal = doctorFees + medicineCharges
                + roomCharges + testCharges;

        double total = subtotal - discount;



        String summary =
                "                 HOSPITAL BILL\n"
                        + "------------------------------------------------\n"
                        + "Patient ID:       " + patientIdField.getText().trim() + "\n"
                        + "Patient Name:     " + patientNameField.getText().trim() + "\n"
                        + "------------------------------------------------\n"
                        + String.format("%-24s %12s%n", "Doctor Fees:", "₹ " + String.format("%.2f", doctorFees))
                        + String.format("%-24s %12s%n", "Medicine Charges:", "₹ " + String.format("%.2f", medicineCharges))
                        + String.format("%-24s %12s%n", "Room Charges:", "₹ " + String.format("%.2f", roomCharges))
                        + String.format("%-24s %12s%n", "Test Charges:", "₹ " + String.format("%.2f", testCharges))
                        + "------------------------------------------------\n"
                        + String.format("%-24s %12s%n", "Subtotal:", "₹ " + String.format("%.2f", subtotal))
                        + String.format("%-24s %12s%n", "Discount:", "₹ " + String.format("%.2f", discount))
                        + "------------------------------------------------\n"
                        + String.format("%-24s %12s%n", "TOTAL AMOUNT:", "₹ " + String.format("%.2f", total))
                        + "Payment Status:   " + paymentStatus.getSelectedItem() + "\n"
                        + "------------------------------------------------\n"
                        + "                  Thank you!\n";


        JTextArea billArea = new JTextArea(summary);

        billArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        billArea.setEditable(false);
        billArea.setOpaque(false);

        JOptionPane.showMessageDialog(
                this,
                billArea,
                "Generated Bill",
                JOptionPane.INFORMATION_MESSAGE
        );
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

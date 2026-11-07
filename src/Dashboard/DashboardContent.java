
package Dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;

public class DashboardContent extends JPanel {

    private JLabel patientCount;
    private JLabel doctorCount;
    private JLabel appointmentCount;
    private JLabel billingCount;

    private DefaultTableModel patientModel;
    private DefaultTableModel doctorModel;
    private DefaultTableModel appointmentModel;

    private JButton refreshButton;

    public DashboardContent() {

        setLayout(new BorderLayout(0, 18));
        setOpaque(false);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        // Welcome heading
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        JLabel title = new JLabel("Welcome Back!");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Here's an overview of your hospital."
        );
        subtitle.setFont(new Font("Arial", Font.BOLD, 15));
        subtitle.setForeground(new Color(235, 242, 255));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);

        // Summary cards
        JPanel cards = new JPanel(new GridLayout(1, 4, 15, 0));
        cards.setOpaque(false);

        patientCount = new JLabel("0");
        doctorCount = new JLabel("0");
        appointmentCount = new JLabel("0");
        billingCount = new JLabel("0");

        cards.add(createCard("Patients", patientCount));
        cards.add(createCard("Doctors", doctorCount));
        cards.add(createCard("Appointments", appointmentCount));
        cards.add(createCard("Billing", billingCount));

        // Header and cards
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        topPanel.add(header);
        topPanel.add(Box.createVerticalStrut(22));
        topPanel.add(cards);

        // Refresh button
        refreshButton = new JButton("Refresh Dashboard");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 12));
        refreshButton.setFocusPainted(false);
        refreshButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        refreshButton.addActionListener(e -> refreshDashboard());

        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshPanel.setOpaque(false);
        refreshPanel.add(refreshButton);

        topPanel.add(refreshPanel);

        add(topPanel, BorderLayout.NORTH);

        // Patient table
        patientModel = createModel(
                "Patient ID", "Name", "Disease"
        );

        JTable patientTable = createTable(patientModel);

        JPanel patientPanel = createTablePanel(
                "Recent Patients", patientTable
        );

        // Doctor table
        doctorModel = createModel(
                "Doctor ID", "Name", "Specialization"
        );

        JTable doctorTable = createTable(doctorModel);

        JPanel doctorPanel = createTablePanel(
                "Available Doctors", doctorTable
        );

        // Appointment table
        appointmentModel = createModel(
                "Appointment ID",
                "Patient",
                "Doctor",
                "Date",
                "Time",
                "Status"
        );

        JTable appointmentTable = createTable(appointmentModel);

        JPanel appointmentPanel = createTablePanel(
                "Recent Appointments", appointmentTable
        );

        // Upper records section
        JPanel upperRecords = new JPanel(new GridLayout(1, 2, 15, 0));
        upperRecords.setOpaque(false);

        upperRecords.add(patientPanel);
        upperRecords.add(doctorPanel);

        // Complete records section
        JPanel recordsPanel = new JPanel(new GridLayout(2, 1, 0, 12));
        recordsPanel.setOpaque(false);

        recordsPanel.add(upperRecords);
        recordsPanel.add(appointmentPanel);

        add(recordsPanel, BorderLayout.CENTER);

        // Load database information
        refreshDashboard();
    }

    // Create summary card
    private JPanel createCard(String heading, JLabel valueLabel) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        // Card appearance
        card.setBackground(new Color(255, 255, 255, 235));

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(200, 215, 235), 1
                        ),
                        new EmptyBorder(16, 20, 16, 20)
                )
        );

        // Heading
        JLabel headingLabel = new JLabel(heading);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headingLabel.setForeground(new Color(85, 105, 130));
        headingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Count
        valueLabel.setFont(new Font("Arial", Font.BOLD, 30));
        valueLabel.setForeground(new Color(30, 65, 110));
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add components
        card.add(headingLabel);
        card.add(Box.createVerticalStrut(12));
        card.add(valueLabel);

        return card;
    }

    // Create table model
    private DefaultTableModel createModel(String... columns) {

        return new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }


    private JTable createTable(DefaultTableModel model) {

        JTable table = new JTable(model);

        // General table appearance
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);

        // Grid and spacing
        table.setShowGrid(true);
        table.setGridColor(new Color(225, 232, 240));
        table.setIntercellSpacing(new Dimension(0, 1));

        // Selection appearance
        table.setSelectionBackground(new Color(205, 222, 242));
        table.setSelectionForeground(new Color(25, 55, 95));

        // Header styling
        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        table.getTableHeader().setBackground(
                new Color(220, 232, 245)
        );

        table.getTableHeader().setForeground(
                new Color(25, 55, 95)
        );

        table.getTableHeader().setPreferredSize(
                new Dimension(0, 34)
        );

        table.getTableHeader().setReorderingAllowed(false);

        // Alternating row colors
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table, value, isSelected,
                                        hasFocus, row, column
                                );

                        if (!isSelected) {

                            if (row % 2 == 0) {
                                component.setBackground(Color.WHITE);
                            } else {
                                component.setBackground(
                                        new Color(240, 245, 251)
                                );
                            }

                            component.setForeground(
                                    new Color(45, 60, 80)
                            );
                        }

                        setBorder(
                                BorderFactory.createEmptyBorder(0, 8, 0, 8)
                        );

                        return component;
                    }
                };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(renderer);
        }

        return table;
    }


    // Create panel containing heading and table
    private JPanel createTablePanel(String heading, JTable table) {

        JPanel panel = new JPanel(new BorderLayout(0, 8));

        panel.setBackground(new Color(255, 255, 255, 225));

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(190, 210, 235)
                        ),
                        new EmptyBorder(10, 12, 10, 12)
                )
        );

        JLabel title = new JLabel(heading);
        title.setFont(new Font("Arial", Font.BOLD, 15));
        title.setForeground(new Color(25, 55, 95));

        panel.add(title, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Load and refresh dashboard data
    public void refreshDashboard() {

        refreshButton.setEnabled(false);
        refreshButton.setText("Loading...");

        SwingWorker<DashboardService.DashboardData, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected DashboardService.DashboardData doInBackground()
                            throws Exception {

                        return DashboardService.loadDashboard();
                    }

                    @Override
                    protected void done() {

                        try {

                            DashboardService.DashboardData data = get();

                            // Update summary cards
                            patientCount.setText(
                                    String.valueOf(data.patientCount)
                            );

                            doctorCount.setText(
                                    String.valueOf(data.doctorCount)
                            );

                            appointmentCount.setText(
                                    String.valueOf(data.appointmentCount)
                            );

                            billingCount.setText(
                                    String.valueOf(data.billingCount)
                            );

                            // Update patient table
                            patientModel.setRowCount(0);

                            for (Object[] row : data.patients) {
                                patientModel.addRow(row);
                            }

                            // Update doctor table
                            doctorModel.setRowCount(0);

                            for (Object[] row : data.doctors) {
                                doctorModel.addRow(row);
                            }

                            // Update appointment table
                            appointmentModel.setRowCount(0);

                            for (Object[] row : data.appointments) {
                                appointmentModel.addRow(row);
                            }

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    DashboardContent.this,
                                    "Unable to load dashboard data:\n"
                                            + ex.getMessage(),
                                    "Database Error",
                                    JOptionPane.ERROR_MESSAGE
                            );

                        } finally {

                            refreshButton.setEnabled(true);
                            refreshButton.setText("Refresh Dashboard");
                        }
                    }
                };

        worker.execute();
    }
}

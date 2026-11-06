package Hospital;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class AppointmentUI extends JFrame {

    private final Color NAVY = new Color(24, 45, 75);
    private final Color TEAL = new Color(0, 150, 136);
    private final Color BG = new Color(242, 246, 250);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(45, 55, 72);

    JComboBox<AppointmentManagement.PersonItem> cmbPatient;
    JComboBox<AppointmentManagement.PersonItem> cmbDoctor;
    JComboBox<String> cmbStatus;

    JTextField txtDate, txtTime, txtDisease;

    JButton btnAdd, btnView, btnUpdate, btnDelete, btnClear;

    JTable table;

    AppointmentManagement management = new AppointmentManagement();

    int selectedAppointmentId = -1;

    public AppointmentUI() {

        setTitle("Hospital Management System | Appointments");
        setSize(1150, 850);
        setMinimumSize(new Dimension(950, 750));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(BG);
        main.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(NAVY);
        header.setBorder(BorderFactory.createEmptyBorder(22, 25, 22, 25));

        JLabel title = new JLabel("APPOINTMENT MANAGEMENT");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(WHITE);

        JLabel subtitle = new JLabel(
                "Manage patient appointments easily and efficiently"
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(new Color(210, 220, 235));

        JPanel heading = new JPanel(new GridLayout(2, 1, 5, 5));
        heading.setBackground(NAVY);
        heading.add(title);
        heading.add(subtitle);

        header.add(heading, BorderLayout.WEST);
        main.add(header, BorderLayout.NORTH);

        // DROPDOWNS
        cmbPatient = new JComboBox<>(
                management.getPatientsComboBoxModel()
        );

        cmbDoctor = new JComboBox<>(
                management.getDoctorsComboBoxModel()
        );

        styleComboBox(cmbPatient);
        styleComboBox(cmbDoctor);

        cmbStatus = new JComboBox<>(
                new String[]{"Scheduled", "Completed", "Cancelled"}
        );

        styleComboBox(cmbStatus);

        txtDate = createField();
        txtTime = createField();
        txtDisease = createField();

        // FORM PANEL
        JPanel formCard = createCard("Appointment Details");

        JPanel formGrid = new JPanel(new GridLayout(3, 4, 15, 15));
        formGrid.setBackground(WHITE);
        formGrid.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        addFormField(formGrid, "Patient Name", cmbPatient);
        addFormField(formGrid, "Doctor Name", cmbDoctor);
        addFormField(formGrid, "Appointment Date (yyyy-MM-dd)", txtDate);
        addFormField(formGrid, "Appointment Time", txtTime);
        addFormField(formGrid, "Disease", txtDisease);
        addFormField(formGrid, "Appointment Status", cmbStatus);

        formCard.add(formGrid, BorderLayout.CENTER);

        // BUTTONS
        btnAdd = createButton(" + Add Appointment", TEAL);
        btnView = createButton("View Appointments", NAVY);
        btnUpdate = createButton("Update", new Color(41, 128, 185));
        btnDelete = createButton("Delete", new Color(220, 70, 70));
        btnClear = createButton("Clear", new Color(100, 110, 125));

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 12, 15)
        );
        buttonPanel.setBackground(WHITE);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnView);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        formCard.add(buttonPanel, BorderLayout.SOUTH);

        // TABLE PANEL
        JPanel tableCard = createCard("Appointment Records");

        table = new JTable();

        styleTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(WHITE);

        tableCard.add(scrollPane, BorderLayout.CENTER);

        // CONTENT
        JPanel content = new JPanel(new BorderLayout(15, 15));
        content.setBackground(BG);

        content.add(formCard, BorderLayout.NORTH);
        content.add(tableCard, BorderLayout.CENTER);

        main.add(content, BorderLayout.CENTER);

        add(main);

        // BUTTON ACTIONS
        btnAdd.addActionListener(e -> addAppointment());
        btnView.addActionListener(e -> loadAppointments());
        btnUpdate.addActionListener(e -> updateAppointment());
        btnDelete.addActionListener(e -> deleteAppointment());
        btnClear.addActionListener(e -> clearFields());

        // TABLE SELECTION
        table.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {

                    int row = table.convertRowIndexToModel(selectedRow);

                    DefaultTableModel model =
                            (DefaultTableModel) table.getModel();

                    selectedAppointmentId =
                            Integer.parseInt(
                                    model.getValueAt(row, 0).toString()
                            );

                    int patientId =
                            Integer.parseInt(
                                    model.getValueAt(row, 7).toString()
                            );

                    int doctorId =
                            Integer.parseInt(
                                    model.getValueAt(row, 8).toString()
                            );

                    selectPatientById(patientId);
                    selectDoctorById(doctorId);

                    txtDate.setText(
                            model.getValueAt(row, 3).toString()
                    );

                    txtTime.setText(
                            model.getValueAt(row, 4).toString()
                    );

                    txtDisease.setText(
                            model.getValueAt(row, 5).toString()
                    );

                    cmbStatus.setSelectedItem(
                            model.getValueAt(row, 6).toString()
                    );
                }
            }
        });

        loadAppointments();

        setVisible(true);
    }

    // STYLE COMBOBOX
    private void styleComboBox(JComboBox<?> combo) {

        combo.setFont(new Font("Arial", Font.PLAIN, 14));
        combo.setBackground(WHITE);
        combo.setPreferredSize(new Dimension(220, 38));
    }

    // SELECT PATIENT BY ID
    private void selectPatientById(int id) {

        for (int i = 0; i < cmbPatient.getItemCount(); i++) {

            AppointmentManagement.PersonItem item =
                    cmbPatient.getItemAt(i);

            if (item.getId() == id) {
                cmbPatient.setSelectedIndex(i);
                return;
            }
        }
    }

    // SELECT DOCTOR BY ID
    private void selectDoctorById(int id) {

        for (int i = 0; i < cmbDoctor.getItemCount(); i++) {

            AppointmentManagement.PersonItem item =
                    cmbDoctor.getItemAt(i);

            if (item.getId() == id) {
                cmbDoctor.setSelectedIndex(i);
                return;
            }
        }
    }

    // CREATE CARD
    private JPanel createCard(String heading) {

        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 232, 240)
                ),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        JLabel label = new JLabel(heading);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(NAVY);

        card.add(label, BorderLayout.NORTH);

        return card;
    }

    // CREATE TEXT FIELD
    private JTextField createField() {

        JTextField field = new JTextField();

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(220, 38));

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(210, 220, 230)
                ),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        return field;
    }

    // ADD LABEL AND FIELD
    private void addFormField(
            JPanel panel, String name, JComponent field) {

        JLabel label = new JLabel(name);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(TEXT);

        JPanel item = new JPanel(new BorderLayout(5, 7));
        item.setBackground(WHITE);

        item.add(label, BorderLayout.NORTH);
        item.add(field, BorderLayout.CENTER);

        panel.add(item);
    }

    // CREATE BUTTON
    private JButton createButton(String name, Color color) {

        JButton button = new JButton(name);

        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(155, 40));

        return button;
    }

    // STYLE TABLE
    private void styleTable() {

        table.setRowHeight(32);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setForeground(TEXT);
        table.setBackground(WHITE);
        table.setSelectionBackground(new Color(205, 235, 230));
        table.setSelectionForeground(NAVY);
        table.setGridColor(new Color(230, 235, 240));
        table.setShowVerticalLines(false);

        JTableHeader th = table.getTableHeader();
        th.setBackground(NAVY);
        th.setForeground(WHITE);
        th.setFont(new Font("Arial", Font.BOLD, 13));
        th.setPreferredSize(new Dimension(100, 38));
    }

    // ADD APPOINTMENT
    private void addAppointment() {

        AppointmentManagement.PersonItem patient =
                (AppointmentManagement.PersonItem)
                        cmbPatient.getSelectedItem();

        AppointmentManagement.PersonItem doctor =
                (AppointmentManagement.PersonItem)
                        cmbDoctor.getSelectedItem();

        if (patient == null || doctor == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please add a patient and doctor first!"
            );
            return;
        }

        try {

            String date = txtDate.getText().trim();
            String time = txtTime.getText().trim();
            String disease = txtDisease.getText().trim();
            String status = cmbStatus.getSelectedItem().toString();

            java.sql.Date.valueOf(date);

            if (time.isEmpty() || disease.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Please fill all fields!"
                );
                return;
            }

            boolean result = management.addAppointmentFromUI(
                    patient.getId(),
                    doctor.getId(),
                    date,
                    time,
                    disease,
                    status
            );

            if (result) {
                JOptionPane.showMessageDialog(
                        this, "Appointment Added Successfully!"
                );
                loadAppointments();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(
                        this, "Appointment Not Added!"
                );
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this, "Enter date in yyyy-MM-dd format!"
            );
        }
    }

    // VIEW APPOINTMENTS
    private void loadAppointments() {

        table.setModel(management.getAppointmentsTableModel());

        // Hide Patient ID and Doctor ID columns.
        // IDs remain available in the table model for update.

        table.removeColumn(
                table.getColumnModel().getColumn(8)
        );

        table.removeColumn(
                table.getColumnModel().getColumn(7)
        );

        styleTable();
    }

    // UPDATE APPOINTMENT
    private void updateAppointment() {

        if (selectedAppointmentId == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an appointment from the table!"
            );
            return;
        }

        AppointmentManagement.PersonItem patient =
                (AppointmentManagement.PersonItem)
                        cmbPatient.getSelectedItem();

        AppointmentManagement.PersonItem doctor =
                (AppointmentManagement.PersonItem)
                        cmbDoctor.getSelectedItem();

        if (patient == null || doctor == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a patient and doctor!"
            );
            return;
        }

        try {

            String date = txtDate.getText().trim();
            String time = txtTime.getText().trim();
            String disease = txtDisease.getText().trim();
            String status = cmbStatus.getSelectedItem().toString();

            java.sql.Date.valueOf(date);

            if (time.isEmpty() || disease.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Please fill all fields!"
                );
                return;
            }

            boolean result = management.updateAppointmentFromUI(
                    selectedAppointmentId,
                    patient.getId(),
                    doctor.getId(),
                    date,
                    time,
                    disease,
                    status
            );

            if (result) {
                JOptionPane.showMessageDialog(
                        this, "Appointment Updated Successfully!"
                );
                loadAppointments();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(
                        this, "Appointment Not Updated!"
                );
            }

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this, "Enter date in yyyy-MM-dd format!"
            );
        }
    }

    // DELETE APPOINTMENT
    private void deleteAppointment() {

        if (selectedAppointmentId == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an appointment first!"
            );
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this appointment?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            boolean result =
                    management.deleteAppointmentFromUI(
                            selectedAppointmentId
                    );

            if (result) {
                JOptionPane.showMessageDialog(
                        this, "Appointment Deleted Successfully!"
                );
                loadAppointments();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(
                        this, "Appointment Not Deleted!"
                );
            }
        }
    }

    // CLEAR FIELDS
    private void clearFields() {

        if (cmbPatient.getItemCount() > 0) {
            cmbPatient.setSelectedIndex(0);
        }

        if (cmbDoctor.getItemCount() > 0) {
            cmbDoctor.setSelectedIndex(0);
        }

        txtDate.setText("");
        txtTime.setText("");
        txtDisease.setText("");

        cmbStatus.setSelectedIndex(0);

        selectedAppointmentId = -1;
        table.clearSelection();
    }

    // MAIN METHOD
    public static void main(String[] args) {

        SwingUtilities.invokeLater(AppointmentUI::new);
    }
}
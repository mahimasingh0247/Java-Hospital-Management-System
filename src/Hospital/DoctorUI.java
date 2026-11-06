package Hospital;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DoctorUI extends JFrame {

    JTextField txtName, txtAge, txtQualification;
    JTextField txtSpecialization, txtEmail, txtAddress;
    JTextField txtPhone, txtFees, txtTiming;

    JComboBox<String> cmbGender, cmbShift;

    JButton btnAdd, btnView, btnUpdate, btnDelete, btnClear;

    JTable doctorTable;
    DefaultTableModel model;

    DoctorManagement dm = new DoctorManagement();

    int selectedDoctorId = -1;

    public DoctorUI() {

        setTitle("Hospital Management System - Doctor");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        // HEADING
        JLabel heading = new JLabel(
                "DOCTOR MANAGEMENT",
                JLabel.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setForeground(Color.WHITE);
        heading.setBackground(new Color(25, 55, 100));
        heading.setOpaque(true);

        add(heading, BorderLayout.NORTH);

        // FORM PANEL
        JPanel formPanel = new JPanel(new GridLayout(6, 4, 10, 10));

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        formPanel.setBackground(Color.WHITE);

        txtName = new JTextField();
        txtAge = new JTextField();
        txtQualification = new JTextField();
        txtSpecialization = new JTextField();
        txtEmail = new JTextField();
        txtAddress = new JTextField();
        txtPhone = new JTextField();
        txtFees = new JTextField();
        txtTiming = new JTextField();

        cmbGender = new JComboBox<>(
                new String[]{"Select Gender", "Male", "Female", "Other"}
        );

        cmbShift = new JComboBox<>(
                new String[]{"Select Shift", "Morning", "Evening", "Night"}
        );

        formPanel.add(new JLabel("Doctor Name:"));
        formPanel.add(txtName);

        formPanel.add(new JLabel("Age:"));
        formPanel.add(txtAge);

        formPanel.add(new JLabel("Gender:"));
        formPanel.add(cmbGender);

        formPanel.add(new JLabel("Qualification:"));
        formPanel.add(txtQualification);

        formPanel.add(new JLabel("Specialization:"));
        formPanel.add(txtSpecialization);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Address:"));
        formPanel.add(txtAddress);

        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Fees:"));
        formPanel.add(txtFees);

        formPanel.add(new JLabel("Shift:"));
        formPanel.add(cmbShift);

        formPanel.add(new JLabel("Timing:"));
        formPanel.add(txtTiming);

        formPanel.add(new JLabel(""));

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel();

        btnAdd = new JButton("Add Doctor");
        btnView = new JButton("View Doctors");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnView);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // TOP PANEL
        JPanel topPanel = new JPanel(new BorderLayout());

        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.CENTER);

        // TABLE
        doctorTable = new JTable();

        doctorTable.setRowHeight(25);
        doctorTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane = new JScrollPane(doctorTable);

        add(scrollPane, BorderLayout.SOUTH);

        scrollPane.setPreferredSize(new Dimension(1100, 250));

        // ADD DOCTOR
        btnAdd.addActionListener(e -> {

            try {

                String name = txtName.getText().trim();
                int age = Integer.parseInt(txtAge.getText().trim());

                String gender = cmbGender.getSelectedItem().toString();

                String qualification = txtQualification.getText().trim();
                String specialization = txtSpecialization.getText().trim();
                String email = txtEmail.getText().trim();
                String address = txtAddress.getText().trim();
                String phone = txtPhone.getText().trim();

                double fees = Double.parseDouble(txtFees.getText().trim());

                String shift = cmbShift.getSelectedItem().toString();
                String timing = txtTiming.getText().trim();

                if (name.isEmpty() || age <= 0 ||
                        gender.equals("Select Gender") ||
                        qualification.isEmpty() ||
                        specialization.isEmpty() ||
                        email.isEmpty() ||
                        shift.equals("Select Shift") ||
                        timing.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please fill all required fields!"
                    );

                    return;
                }

                boolean result = dm.addDoctorFromUI(
                        name, age, gender, qualification,
                        specialization, email, address,
                        phone, fees, shift, timing
                );

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Doctor Added Successfully!"
                    );

                    clearFields();
                    loadDoctors();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Doctor Add Failed!"
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter valid Age and Fees!"
                );
            }
        });

        // VIEW DOCTORS
        btnView.addActionListener(e -> loadDoctors());

        // UPDATE DOCTOR
        btnUpdate.addActionListener(e -> {

            if (selectedDoctorId == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a doctor from the table!"
                );

                return;
            }

            try {

                String name = txtName.getText().trim();
                int age = Integer.parseInt(txtAge.getText().trim());

                String gender = cmbGender.getSelectedItem().toString();

                String qualification = txtQualification.getText().trim();
                String specialization = txtSpecialization.getText().trim();
                String email = txtEmail.getText().trim();
                String address = txtAddress.getText().trim();
                String phone = txtPhone.getText().trim();

                double fees = Double.parseDouble(txtFees.getText().trim());

                String shift = cmbShift.getSelectedItem().toString();
                String timing = txtTiming.getText().trim();

                if (name.isEmpty() || age <= 0 ||
                        gender.equals("Select Gender") ||
                        qualification.isEmpty() ||
                        specialization.isEmpty() ||
                        email.isEmpty() ||
                        shift.equals("Select Shift") ||
                        timing.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please fill all required fields!"
                    );

                    return;
                }

                boolean result = dm.updateDoctorFromUI(
                        selectedDoctorId, name, age, gender,
                        qualification, specialization, email,
                        address, phone, fees, shift, timing
                );

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Doctor Updated Successfully!"
                    );

                    clearFields();
                    loadDoctors();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Doctor Update Failed!"
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter valid Age and Fees!"
                );
            }
        });

        // DELETE DOCTOR
        btnDelete.addActionListener(e -> {

            if (selectedDoctorId == -1) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a doctor from the table!"
                );

                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this doctor?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {

                boolean result = dm.deleteDoctorFromUI(selectedDoctorId);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Doctor Deleted Successfully!"
                    );

                    clearFields();
                    loadDoctors();

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Doctor Delete Failed!"
                    );
                }
            }
        });

        // CLEAR
        btnClear.addActionListener(e -> clearFields());

        // SELECT DOCTOR FROM TABLE
        doctorTable.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int row = doctorTable.getSelectedRow();

                if (row != -1) {

                    int modelRow = doctorTable.convertRowIndexToModel(row);

                    selectedDoctorId = Integer.parseInt(
                            model.getValueAt(modelRow, 0).toString()
                    );

                    txtName.setText(model.getValueAt(modelRow, 1).toString());
                    txtAge.setText(model.getValueAt(modelRow, 2).toString());

                    cmbGender.setSelectedItem(
                            model.getValueAt(modelRow, 3).toString()
                    );

                    txtQualification.setText(model.getValueAt(modelRow, 4).toString());
                    txtSpecialization.setText(model.getValueAt(modelRow, 5).toString());
                    txtEmail.setText(model.getValueAt(modelRow, 6).toString());
                    txtAddress.setText(model.getValueAt(modelRow, 7).toString());
                    txtPhone.setText(model.getValueAt(modelRow, 8).toString());
                    txtFees.setText(model.getValueAt(modelRow, 9).toString());

                    cmbShift.setSelectedItem(
                            model.getValueAt(modelRow, 10).toString()
                    );

                    txtTiming.setText(model.getValueAt(modelRow, 11).toString());
                }
            }
        });

        loadDoctors();

        setVisible(true);
    }

    // LOAD DOCTORS
    private void loadDoctors() {

        model = dm.getDoctorsTableModel();

        doctorTable.setModel(model);
    }

    // CLEAR FIELDS
    private void clearFields() {

        selectedDoctorId = -1;

        txtName.setText("");
        txtAge.setText("");
        txtQualification.setText("");
        txtSpecialization.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtFees.setText("");
        txtTiming.setText("");

        cmbGender.setSelectedIndex(0);
        cmbShift.setSelectedIndex(0);

        doctorTable.clearSelection();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DoctorUI());
    }
}
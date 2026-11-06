
package Hospital;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientUI extends JFrame {

    JTextField txtName, txtAge, txtDob;
    JTextField txtEmail, txtAddress, txtPhone, txtDisease;

    JComboBox<String> cmbGender, cmbMaritalStatus;

    JButton btnAdd, btnView, btnUpdate, btnDelete, btnClear;

    JTable patientTable;
    DefaultTableModel model;

    private int selectedPatientId = -1;

    PatientManagement pm = new PatientManagement();

    public PatientUI() {

        setTitle("Hospital Management System - Patient Management");
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(240, 245, 250));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Heading
        JLabel heading = new JLabel("PATIENT MANAGEMENT");
        heading.setFont(new Font("Arial", Font.BOLD, 28));
        heading.setForeground(new Color(25, 55, 90));

        JLabel subHeading =
                new JLabel("Add and manage patient records");

        subHeading.setFont(new Font("Arial", Font.PLAIN, 15));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(new Color(240, 245, 250));
        header.add(heading);
        header.add(subHeading);

        mainPanel.add(header, BorderLayout.NORTH);

        // Patient Form
        JPanel formPanel =
                new JPanel(new GridLayout(5, 4, 12, 12));

        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(
                BorderFactory.createTitledBorder("Patient Information"));

        txtName = new JTextField();
        txtAge = new JTextField();
        txtDob = new JTextField();
        txtEmail = new JTextField();
        txtAddress = new JTextField();
        txtPhone = new JTextField();
        txtDisease = new JTextField();

        cmbGender = new JComboBox<>(
                new String[]{"Select Gender", "Male", "Female", "Other"});

        cmbMaritalStatus = new JComboBox<>(
                new String[]{"Select Status", "Single", "Married", "Other"});

        addField(formPanel, "Patient Name:", txtName);
        addField(formPanel, "Age:", txtAge);
        addField(formPanel, "Gender:", cmbGender);
        addField(formPanel, "Date of Birth:", txtDob);
        addField(formPanel, "Email:", txtEmail);
        addField(formPanel, "Marital Status:", cmbMaritalStatus);
        addField(formPanel, "Phone Number:", txtPhone);
        addField(formPanel, "Address:", txtAddress);
        addField(formPanel, "Disease:", txtDisease);

        // Buttons
        JPanel buttonPanel =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));

        buttonPanel.setBackground(Color.WHITE);

        btnAdd = createButton("Add Patient");
        btnView = createButton("View Patients");
        btnUpdate = createButton("Update");
        btnDelete = createButton("Delete");
        btnClear = createButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnView);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // Patient Table
        String[] columns = {
                "ID", "Name", "Age", "Gender", "DOB",
                "Email", "Marital Status", "Address",
                "Phone", "Disease"
        };

        model = new DefaultTableModel(columns, 0);

        patientTable = new JTable(model);
        patientTable.setRowHeight(25);
        patientTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(patientTable);

        // Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(240, 245, 250));

        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(buttonPanel, BorderLayout.CENTER);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(
                BorderFactory.createTitledBorder("Patient Records"));

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(new Color(240, 245, 250));

        contentPanel.add(centerPanel, BorderLayout.NORTH);
        contentPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        // ADD PATIENT
        btnAdd.addActionListener(e -> {

            try {

                String name = txtName.getText().trim();
                String ageText = txtAge.getText().trim();
                String gender = cmbGender.getSelectedItem().toString();
                String dob = txtDob.getText().trim();
                String email = txtEmail.getText().trim();
                String maritalStatus =
                        cmbMaritalStatus.getSelectedItem().toString();
                String address = txtAddress.getText().trim();
                String phone = txtPhone.getText().trim();
                String disease = txtDisease.getText().trim();

                if (name.isEmpty() || ageText.isEmpty()
                        || dob.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this, "Please fill required fields!");
                    return;
                }

                int age = Integer.parseInt(ageText);

                if (age <= 0) {
                    JOptionPane.showMessageDialog(
                            this, "Please enter valid Age!");
                    return;
                }

                boolean result = pm.addPatientFromUI(
                        name, age, gender, dob, email,
                        maritalStatus, address, phone, disease);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this, "Patient Added Successfully!");

                    clearFields();
                    loadPatients();

                } else {

                    JOptionPane.showMessageDialog(
                            this, "Patient Not Added!");
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this, "Please enter valid Age!");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this, "Error: " + ex.getMessage());
            }
        });

        // VIEW PATIENTS
        btnView.addActionListener(e -> loadPatients());

        // UPDATE PATIENT
        btnUpdate.addActionListener(e -> {

            try {

                if (selectedPatientId == -1) {

                    JOptionPane.showMessageDialog(
                            this, "Please select a patient from the table!");

                    return;
                }

                String name = txtName.getText().trim();
                String ageText = txtAge.getText().trim();
                String gender = cmbGender.getSelectedItem().toString();
                String dob = txtDob.getText().trim();
                String email = txtEmail.getText().trim();
                String maritalStatus =
                        cmbMaritalStatus.getSelectedItem().toString();
                String address = txtAddress.getText().trim();
                String phone = txtPhone.getText().trim();
                String disease = txtDisease.getText().trim();

                if (name.isEmpty() || ageText.isEmpty()
                        || dob.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this, "Please fill required fields!");

                    return;
                }

                int age = Integer.parseInt(ageText);

                if (age <= 0) {

                    JOptionPane.showMessageDialog(
                            this, "Please enter valid Age!");

                    return;
                }

                boolean result = pm.updatePatientFromUI(
                        selectedPatientId,
                        name, age, gender, dob, email,
                        maritalStatus, address, phone, disease);

                if (result) {

                    JOptionPane.showMessageDialog(
                            this, "Patient Updated Successfully!");

                    clearFields();
                    loadPatients();

                } else {

                    JOptionPane.showMessageDialog(
                            this, "Patient Update Failed!");
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this, "Please enter valid Age!");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this, "Error: " + ex.getMessage());
            }

        });
        btnDelete.addActionListener(e -> {

            if (selectedPatientId == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a patient from the table!"
                );
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this patient?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {

                boolean result = pm.deletePatientFromUI(selectedPatientId);

                if (result) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Patient Deleted Successfully!"
                    );

                    clearFields();
                    loadPatients();

                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Patient Delete Failed!"
                    );
                }
            }
        });

        // CLEAR
        btnClear.addActionListener(e -> clearFields());

        // SELECT PATIENT FROM TABLE
        patientTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int selectedRow = patientTable.getSelectedRow();

                        if (selectedRow != -1) {

                            int row =
                                    patientTable.convertRowIndexToModel(selectedRow);

                            selectedPatientId = Integer.parseInt(
                                    model.getValueAt(row, 0).toString());

                            txtName.setText(
                                    model.getValueAt(row, 1).toString());

                            txtAge.setText(
                                    model.getValueAt(row, 2).toString());

                            cmbGender.setSelectedItem(
                                    model.getValueAt(row, 3).toString());

                            txtDob.setText(
                                    model.getValueAt(row, 4).toString());

                            txtEmail.setText(
                                    model.getValueAt(row, 5).toString());

                            cmbMaritalStatus.setSelectedItem(
                                    model.getValueAt(row, 6).toString());

                            txtAddress.setText(
                                    model.getValueAt(row, 7).toString());

                            txtPhone.setText(
                                    model.getValueAt(row, 8).toString());

                            txtDisease.setText(
                                    model.getValueAt(row, 9).toString());
                        }
                    }
                });

        // Load Records
        loadPatients();
    }

    // ADD FORM FIELD
    private void addField(
            JPanel panel, String label, Component field) {

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));

        panel.add(lbl);
        panel.add(field);
    }

    // CREATE BUTTON
    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setBackground(new Color(35, 75, 115));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);

        return button;
    }

    // CLEAR FORM
    private void clearFields() {

        selectedPatientId = -1;

        txtName.setText("");
        txtAge.setText("");
        txtDob.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtDisease.setText("");

        cmbGender.setSelectedIndex(0);
        cmbMaritalStatus.setSelectedIndex(0);

        patientTable.clearSelection();
    }

    // LOAD PATIENT RECORDS
    private void loadPatients() {

        model = pm.getPatientsTableModel();
        patientTable.setModel(model);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new PatientUI().setVisible(true);
        });
    }
}

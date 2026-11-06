package Hospital;

import java.sql.*;
import java.util.Scanner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class AppointmentManagement {

    // Patient/Doctor dropdown item
    public static class PersonItem {
        private int id;
        private String name;

        public PersonItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // PATIENT DROPDOWN
    public DefaultComboBoxModel<PersonItem> getPatientsComboBoxModel() {

        DefaultComboBoxModel<PersonItem> model =
                new DefaultComboBoxModel<>();

        String sql = "SELECT patient_id, name FROM patient ORDER BY name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addElement(new PersonItem(
                        rs.getInt("patient_id"),
                        rs.getString("name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    // DOCTOR DROPDOWN
    public DefaultComboBoxModel<PersonItem> getDoctorsComboBoxModel() {

        DefaultComboBoxModel<PersonItem> model =
                new DefaultComboBoxModel<>();

        String sql = "SELECT doctor_id, name FROM doctor ORDER BY name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addElement(new PersonItem(
                        rs.getInt("doctor_id"),
                        rs.getString("name")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    // ADD APPOINTMENT
    public boolean addAppointmentFromUI(
            int patientId, int doctorId, String date,
            String time, String disease, String status) {

        String sql = "INSERT INTO appointment " +
                "(patient_id, doctor_id, appointment_date, " +
                "appointment_time, disease, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setDate(3, Date.valueOf(date));
            ps.setString(4, time);
            ps.setString(5, disease);
            ps.setString(6, status);

            return ps.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    // VIEW APPOINTMENTS WITH PATIENT AND DOCTOR NAMES
    public DefaultTableModel getAppointmentsTableModel() {

        String[] columns = {
                "Appointment ID", "Patient Name", "Doctor Name",
                "Date", "Time", "Disease", "Status",
                "Patient ID", "Doctor ID"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql =
                "SELECT a.appointment_id, p.name AS patient_name, " +
                        "d.name AS doctor_name, a.appointment_date, " +
                        "a.appointment_time, a.disease, a.status, " +
                        "a.patient_id, a.doctor_id " +
                        "FROM appointment a " +
                        "LEFT JOIN patient p ON a.patient_id = p.patient_id " +
                        "LEFT JOIN doctor d ON a.doctor_id = d.doctor_id " +
                        "ORDER BY a.appointment_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Object[] row = {
                        rs.getInt("appointment_id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_name"),
                        rs.getDate("appointment_date"),
                        rs.getString("appointment_time"),
                        rs.getString("disease"),
                        rs.getString("status"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id")
                };

                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    // UPDATE APPOINTMENT
    public boolean updateAppointmentFromUI(
            int id, int patientId, int doctorId,
            String date, String time, String disease, String status) {

        String sql = "UPDATE appointment SET patient_id=?, doctor_id=?, " +
                "appointment_date=?, appointment_time=?, disease=?, status=? " +
                "WHERE appointment_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setDate(3, Date.valueOf(date));
            ps.setString(4, time);
            ps.setString(5, disease);
            ps.setString(6, status);
            ps.setInt(7, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE APPOINTMENT
    public boolean deleteAppointmentFromUI(int id) {

        String sql =
                "DELETE FROM appointment WHERE appointment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ADD APPOINTMENT - CONSOLE
    public void addAppointment() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Patient ID: ");
        int patientId = sc.nextInt();

        System.out.print("Enter Doctor ID: ");
        int doctorId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Date (yyyy-mm-dd): ");
        String date = sc.nextLine();

        System.out.print("Enter Time: ");
        String time = sc.nextLine();

        System.out.print("Enter Disease: ");
        String disease = sc.nextLine();

        String status = "Scheduled";

        if (addAppointmentFromUI(
                patientId, doctorId, date, time, disease, status)) {
            System.out.println("Appointment Added Successfully!");
        } else {
            System.out.println("Appointment Not Added!");
        }
    }

    // VIEW APPOINTMENTS - CONSOLE
    public void viewAppointments() {

        String sql =
                "SELECT a.appointment_id, p.name AS patient_name, " +
                        "d.name AS doctor_name, a.appointment_date, " +
                        "a.appointment_time, a.disease, a.status " +
                        "FROM appointment a " +
                        "LEFT JOIN patient p ON a.patient_id=p.patient_id " +
                        "LEFT JOIN doctor d ON a.doctor_id=d.doctor_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("===== ALL APPOINTMENTS =====");

            while (rs.next()) {
                System.out.println("----------------------------");
                System.out.println("Appointment ID: " +
                        rs.getInt("appointment_id"));
                System.out.println("Patient Name: " +
                        rs.getString("patient_name"));
                System.out.println("Doctor Name: " +
                        rs.getString("doctor_name"));
                System.out.println("Date: " +
                        rs.getDate("appointment_date"));
                System.out.println("Time: " +
                        rs.getString("appointment_time"));
                System.out.println("Disease: " +
                        rs.getString("disease"));
                System.out.println("Status: " +
                        rs.getString("status"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // UPDATE APPOINTMENT - CONSOLE
    public void updateAppointment() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Appointment ID: ");
        int id = sc.nextInt();

        System.out.print("Enter New Patient ID: ");
        int patientId = sc.nextInt();

        System.out.print("Enter New Doctor ID: ");
        int doctorId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Date (yyyy-mm-dd): ");
        String date = sc.nextLine();

        System.out.print("Enter New Time: ");
        String time = sc.nextLine();

        System.out.print("Enter New Disease: ");
        String disease = sc.nextLine();

        System.out.print("Enter New Status: ");
        String status = sc.nextLine();

        if (updateAppointmentFromUI(
                id, patientId, doctorId, date, time, disease, status)) {
            System.out.println("Appointment Updated Successfully!");
        } else {
            System.out.println("Appointment Not Updated!");
        }
    }

    // DELETE APPOINTMENT - CONSOLE
    public void deleteAppointment() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Appointment ID to Delete: ");
        int id = sc.nextInt();

        if (deleteAppointmentFromUI(id)) {
            System.out.println("Appointment Deleted Successfully!");
        } else {
            System.out.println("Appointment ID Not Found!");
        }
    }
}
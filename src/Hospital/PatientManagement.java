
package Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

public class PatientManagement {

    // ADD PATIENT - CONSOLE
    public void addPatient() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Patient Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Gender: ");
        String gender = sc.nextLine();

        System.out.print("Enter Date of Birth (yyyy-mm-dd): ");
        String dob = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Marital Status: ");
        String maritalStatus = sc.nextLine();

        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine();

        System.out.print("Enter Disease: ");
        String disease = sc.nextLine();

        String sql = "INSERT INTO patient "
                + "(name, age, gender, date_of_birth, email, "
                + "marital_status, address, phone_number, disease) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setDate(4, java.sql.Date.valueOf(dob));
            ps.setString(5, email);
            ps.setString(6, maritalStatus);
            ps.setString(7, address);
            ps.setString(8, phone);
            ps.setString(9, disease);

            ps.executeUpdate();

            System.out.println("Patient Added Successfully!");

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // VIEW PATIENTS - CONSOLE
    public void viewPatients() {

        String sql = "SELECT * FROM patient";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("===== ALL PATIENT DETAILS =====");

            while (rs.next()) {
                System.out.println("----------------------------");
                System.out.println("Patient ID: " + rs.getInt("patient_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Gender: " + rs.getString("gender"));
                System.out.println("Date of Birth: " + rs.getDate("date_of_birth"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Marital Status: " + rs.getString("marital_status"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Phone: " + rs.getString("phone_number"));
                System.out.println("Disease: " + rs.getString("disease"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // UPDATE PATIENT - CONSOLE
    public void updatePatient() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Patient ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Phone Number: ");
        String phone = sc.nextLine();

        String sql = "UPDATE patient SET phone_number = ? WHERE patient_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, phone);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Patient Updated Successfully!");
            } else {
                System.out.println("Patient ID Not Found!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // DELETE PATIENT - CONSOLE
    public void deletePatient() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Patient ID to Delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM patient WHERE patient_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Patient Deleted Successfully!");
            } else {
                System.out.println("Patient ID Not Found!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ADD PATIENT - SWING UI
    public boolean addPatientFromUI(
            String name, int age, String gender, String dob,
            String email, String maritalStatus, String address,
            String phone, String disease) {

        String sql = "INSERT INTO patient "
                + "(name, age, gender, date_of_birth, email, "
                + "marital_status, address, phone_number, disease) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setDate(4, java.sql.Date.valueOf(dob));
            ps.setString(5, email);
            ps.setString(6, maritalStatus);
            ps.setString(7, address);
            ps.setString(8, phone);
            ps.setString(9, disease);

            return ps.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    // VIEW PATIENTS - SWING JTable
    public DefaultTableModel getPatientsTableModel() {

        String[] columns = {
                "ID", "Name", "Age", "Gender", "DOB",
                "Email", "Marital Status", "Address",
                "Phone", "Disease"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String sql = "SELECT * FROM patient";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Object[] row = {
                        rs.getInt("patient_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getDate("date_of_birth"),
                        rs.getString("email"),
                        rs.getString("marital_status"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getString("disease")
                };

                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    // UPDATE PATIENT - SWING UI
    public boolean updatePatientFromUI(
            int id, String name, int age, String gender, String dob,
            String email, String maritalStatus, String address,
            String phone, String disease) {

        String sql = "UPDATE patient SET name=?, age=?, gender=?, "
                + "date_of_birth=?, email=?, marital_status=?, "
                + "address=?, phone_number=?, disease=? "
                + "WHERE patient_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setDate(4, java.sql.Date.valueOf(dob));
            ps.setString(5, email);
            ps.setString(6, maritalStatus);
            ps.setString(7, address);
            ps.setString(8, phone);
            ps.setString(9, disease);
            ps.setInt(10, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE PATIENT - SWING UI
    public boolean deletePatientFromUI(int id) {

        String sql = "DELETE FROM patient WHERE patient_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

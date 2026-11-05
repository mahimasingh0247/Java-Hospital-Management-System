package Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;

public class DoctorManagement {

    public void addDoctor() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Doctor Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Gender: ");
        String gender = sc.nextLine();

        System.out.print("Enter Qualification: ");
        String qualification = sc.nextLine();

        System.out.print("Enter Specialization: ");
        String specialization = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = sc.nextLine();

        System.out.print("Enter Fees: ");
        double fees = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Shift: ");
        String shift = sc.nextLine();

        System.out.print("Enter Timing: ");
        String timing = sc.nextLine();

        String sql = "INSERT INTO doctor "
                + "(name, age, gender, qualification, specialization, "
                + "email, address, phone_number, fees, shift, timing) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, qualification);
            ps.setString(5, specialization);
            ps.setString(6, email);
            ps.setString(7, address);
            ps.setString(8, phone);
            ps.setDouble(9, fees);
            ps.setString(10, shift);
            ps.setString(11, timing);

            ps.executeUpdate();

            System.out.println("Doctor Added Successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public DefaultTableModel getDoctorsTableModel() {

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Age");
        model.addColumn("Gender");
        model.addColumn("Qualification");
        model.addColumn("Specialization");
        model.addColumn("Email");
        model.addColumn("Address");
        model.addColumn("Phone");
        model.addColumn("Fees");
        model.addColumn("Shift");
        model.addColumn("Timing");

        String sql = "SELECT * FROM doctor";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("doctor_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("qualification"),
                        rs.getString("specialization"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getDouble("fees"),
                        rs.getString("shift"),
                        rs.getString("timing")
                });
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return model;
    }
    public boolean updateDoctorFromUI(
            int id, String name, int age, String gender,
            String qualification, String specialization,
            String email, String address, String phone,
            double fees, String shift, String timing) {

        String sql = "UPDATE doctor SET name=?, age=?, gender=?, " +
                "qualification=?, specialization=?, email=?, address=?, " +
                "phone_number=?, fees=?, shift=?, timing=? " +
                "WHERE doctor_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, qualification);
            ps.setString(5, specialization);
            ps.setString(6, email);
            ps.setString(7, address);
            ps.setString(8, phone);
            ps.setDouble(9, fees);
            ps.setString(10, shift);
            ps.setString(11, timing);
            ps.setInt(12, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    public void viewDoctors() {

        String sql = "SELECT * FROM doctor";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                System.out.println("----------------------------");
                System.out.println("Doctor ID: " + rs.getInt("doctor_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Gender: " + rs.getString("gender"));
                System.out.println("Qualification: " + rs.getString("qualification"));
                System.out.println("Specialization: " + rs.getString("specialization"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Phone: " + rs.getString("phone_number"));
                System.out.println("Fees: " + rs.getDouble("fees"));
                System.out.println("Shift: " + rs.getString("shift"));
                System.out.println("Timing: " + rs.getString("timing"));
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void updateDoctor() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Doctor ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();

        System.out.print("Enter New Specialization: ");
        String specialization = sc.nextLine();

        System.out.print("Enter New Fees: ");
        double fees = sc.nextDouble();

        String sql = "UPDATE doctor SET name=?, specialization=?, fees=? WHERE doctor_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, specialization);
            ps.setDouble(3, fees);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Doctor Updated Successfully!");
            } else {
                System.out.println("Doctor ID Not Found!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public boolean deleteDoctorFromUI(int id) {

        String sql = "DELETE FROM doctor WHERE doctor_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    public void deleteDoctor() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Doctor ID to Delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM doctor WHERE doctor_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Doctor Deleted Successfully!");
            } else {
                System.out.println("Doctor ID Not Found!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public boolean addDoctorFromUI(
            String name, int age, String gender,
            String qualification, String specialization,
            String email, String address, String phone,
            double fees, String shift, String timing) {

        String sql = "INSERT INTO doctor " +
                "(name, age, gender, qualification, specialization, " +
                "email, address, phone_number, fees, shift, timing) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setString(4, qualification);
            ps.setString(5, specialization);
            ps.setString(6, email);
            ps.setString(7, address);
            ps.setString(8, phone);
            ps.setDouble(9, fees);
            ps.setString(10, shift);
            ps.setString(11, timing);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}

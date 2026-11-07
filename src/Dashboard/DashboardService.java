
package Dashboard;

import Hospital.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardService {

    public static class DashboardData {

        public int patientCount;
        public int doctorCount;
        public int appointmentCount;
        public int billingCount;

        public List<Object[]> patients = new ArrayList<>();
        public List<Object[]> doctors = new ArrayList<>();
        public List<Object[]> appointments = new ArrayList<>();
    }

    public static DashboardData loadDashboard() throws SQLException {

        DashboardData data = new DashboardData();

        try (Connection con = DBConnection.getConnection()) {

            // Fetch total counts
            data.patientCount = getCount(con, "patient");
            data.doctorCount = getCount(con, "doctor");
            data.appointmentCount = getCount(con, "appointment");
            data.billingCount = getCount(con, "billing");

            // Fetch recent patients
            String patientQuery =
                    "SELECT patient_id, name, disease " +
                            "FROM patient ORDER BY patient_id DESC LIMIT 5";

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(patientQuery)) {

                while (rs.next()) {

                    data.patients.add(new Object[]{
                            rs.getInt("patient_id"),
                            rs.getString("name"),
                            rs.getString("disease")
                    });
                }
            }

            // Fetch doctors
            String doctorQuery =
                    "SELECT doctor_id, name, specialization " +
                            "FROM doctor ORDER BY doctor_id DESC LIMIT 5";

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(doctorQuery)) {

                while (rs.next()) {

                    data.doctors.add(new Object[]{
                            rs.getInt("doctor_id"),
                            rs.getString("name"),
                            rs.getString("specialization")
                    });
                }
            }

            // Fetch recent appointments
            String appointmentQuery =
                    "SELECT a.appointment_id, " +
                            "p.name AS patient_name, " +
                            "d.name AS doctor_name, " +
                            "a.appointment_date, " +
                            "a.appointment_time, " +
                            "a.status " +
                            "FROM appointment a " +
                            "LEFT JOIN patient p ON a.patient_id = p.patient_id " +
                            "LEFT JOIN doctor d ON a.doctor_id = d.doctor_id " +
                            "ORDER BY a.appointment_id DESC LIMIT 5";

            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(appointmentQuery)) {

                while (rs.next()) {

                    data.appointments.add(new Object[]{
                            rs.getInt("appointment_id"),
                            rs.getString("patient_name"),
                            rs.getString("doctor_name"),
                            rs.getDate("appointment_date"),
                            rs.getString("appointment_time"),
                            rs.getString("status")
                    });
                }
            }
        }

        return data;
    }

    // Reusable method for counting table records
    private static int getCount(Connection con, String table)
            throws SQLException {

        String query = "SELECT COUNT(*) FROM " + table;

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }
}
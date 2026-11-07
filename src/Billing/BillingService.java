
package Billing;

import Hospital.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BillingService {

    public int saveBill(
            int patientId,
            String patientName,
            BigDecimal doctorFees,
            BigDecimal medicineCharges,
            BigDecimal roomCharges,
            BigDecimal testCharges,
            BigDecimal discount,
            String paymentStatus
    ) throws SQLException {


// Validate patient information
        if (patientId <= 0 || patientName == null
                || patientName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Invalid patient information."
            );
        }

// Validate all charge amounts
        if (doctorFees == null || medicineCharges == null
                || roomCharges == null || testCharges == null
                || discount == null) {
            throw new IllegalArgumentException(
                    "Charge amounts cannot be null."
            );
        }

        if (doctorFees.signum() < 0
                || medicineCharges.signum() < 0
                || roomCharges.signum() < 0
                || testCharges.signum() < 0
                || discount.signum() < 0) {
            throw new IllegalArgumentException(
                    "Amounts cannot be negative."
            );
        }

// Validate discount
        BigDecimal subtotal = doctorFees
                .add(medicineCharges)
                .add(roomCharges)
                .add(testCharges);

        if (discount.compareTo(subtotal) > 0) {
            throw new IllegalArgumentException(
                    "Discount cannot exceed subtotal."
            );
        }

// Validate payment status
        if (!"Paid".equals(paymentStatus)
                && !"Pending".equals(paymentStatus)) {
            throw new IllegalArgumentException(
                    "Invalid payment status."
            );
        }

        BigDecimal totalAmount = subtotal.subtract(discount);

        // Connect to the database
        try (Connection connection = DBConnection.getConnection()) {

            // Verify that the patient exists
            String checkPatient =
                    "SELECT name FROM patient WHERE patient_id = ?";

            try (PreparedStatement ps =
                         connection.prepareStatement(checkPatient)) {

                ps.setInt(1, patientId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        throw new SQLException(
                                "Patient ID does not exist."
                        );
                    }

                    String databaseName = rs.getString("name");

                    if (!databaseName.equalsIgnoreCase(
                            patientName.trim())) {

                        throw new SQLException(
                                "Patient name does not match the Patient ID."
                        );
                    }
                }
            }

            // Insert the bill
            String insertBill =
                    "INSERT INTO billing " +
                            "(patient_id, patient_name, doctor_fees, " +
                            "medicine_charges, room_charges, test_charges, " +
                            "discount, total_amount, payment_status) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps =
                         connection.prepareStatement(
                                 insertBill,
                                 Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, patientId);
                ps.setString(2, patientName.trim());
                ps.setBigDecimal(3, doctorFees);
                ps.setBigDecimal(4, medicineCharges);
                ps.setBigDecimal(5, roomCharges);
                ps.setBigDecimal(6, testCharges);
                ps.setBigDecimal(7, discount);
                ps.setBigDecimal(8, totalAmount);
                ps.setString(9, paymentStatus);

                ps.executeUpdate();

                // Retrieve the automatically generated bill ID
                try (ResultSet keys = ps.getGeneratedKeys()) {

                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        }

        throw new SQLException("Bill could not be generated.");
    }
}

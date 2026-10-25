
import Hospital.Patient;
import Hospital.Doctor;
import Hospital.Appointment;
import Hospital.Billing;

public class Main {
    public static void main(String[] args) {

        Patient patient = new Patient(
                1, "Rahul Sharma", 25, "Male",
                "15-05-2001", "rahul@gmail.com",
                "Single", "Ballia, Uttar Pradesh",
                "9876543210", "Fever"
        );

        Doctor doctor = new Doctor(
                101, "Dr. Amit Kumar", 40, "Male",
                "MBBS, MD", "Cardiologist",
                "amit@gmail.com", "Ballia, Uttar Pradesh",
                "9123456780", 500.0, "Morning",
                "9:00 AM - 2:00 PM"
        );

        Appointment appointment = new Appointment(
                1001, 1, 101, "26-10-2026",
                "10:00 AM", "Fever", "Confirmed"
        );

        // Billing Management
        Billing bill = new Billing(
                5001,          // Bill ID
                1,             // Patient ID
                "Rahul Sharma",
                500.0,         // Doctor Fees
                300.0,         // Medicine Charges
                1000.0,        // Room Charges
                200.0,         // Test Charges
                100.0,         // Discount
                "Pending"      // Payment Status
        );

        System.out.println("===== PATIENT DETAILS =====");
        patient.displayDetails();

        System.out.println("\n===== DOCTOR DETAILS =====");
        doctor.displayDetails();

        System.out.println("\n===== APPOINTMENT DETAILS =====");
        appointment.displayDetails();

        System.out.println("\n===== BILLING DETAILS =====");
        bill.displayBill();
    }
}

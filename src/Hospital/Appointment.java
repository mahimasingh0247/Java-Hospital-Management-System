
package Hospital;

public class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private String appointmentDate;
    private String appointmentTime;
    private String reason;
    private String status;

    public Appointment(int appointmentId, int patientId,
                       int doctorId, String appointmentDate,
                       String appointmentTime, String reason,
                       String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.reason = reason;
        this.status = status;
    }

    public void displayDetails() {
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Date: " + appointmentDate);
        System.out.println("Time: " + appointmentTime);
        System.out.println("Reason: " + reason);
        System.out.println("Status: " + status);
    }
}


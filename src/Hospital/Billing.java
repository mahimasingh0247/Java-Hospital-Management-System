
package Hospital;

public class Billing {

    private int billId;
    private int patientId;
    private String patientName;
    private double doctorFees;
    private double medicineCharges;
    private double roomCharges;
    private double testCharges;
    private double discount;
    private double totalAmount;
    private String paymentStatus;

    public Billing(int billId, int patientId,
                   String patientName, double doctorFees,
                   double medicineCharges, double roomCharges,
                   double testCharges, double discount,
                   String paymentStatus) {

        this.billId = billId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorFees = doctorFees;
        this.medicineCharges = medicineCharges;
        this.roomCharges = roomCharges;
        this.testCharges = testCharges;
        this.discount = discount;
        this.paymentStatus = paymentStatus;

        this.totalAmount = doctorFees + medicineCharges
                + roomCharges + testCharges - discount;
    }

    public void displayBill() {
        System.out.println("Bill ID: " + billId);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Patient Name: " + patientName);
        System.out.println("Doctor Fees: Rs. " + doctorFees);
        System.out.println("Medicine Charges: Rs. " + medicineCharges);
        System.out.println("Room Charges: Rs. " + roomCharges);
        System.out.println("Test Charges: Rs. " + testCharges);
        System.out.println("Discount: Rs. " + discount);
        System.out.println("Total Amount: Rs. " + totalAmount);
        System.out.println("Payment Status: " + paymentStatus);
    }
}


package Hospital;

public class Doctor {
    private int doctorId;
    private String name;
    private int age;
    private String gender;
    private String qualification;
    private String specialization;
    private String email;
    private String address;
    private String phoneNumber;
    private double fees;
    private String shift;
    private String timing;

    public Doctor(int doctorId, String name, int age,
                  String gender, String qualification,
                  String specialization, String email,
                  String address, String phoneNumber,
                  double fees, String shift, String timing) {

        this.doctorId = doctorId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.qualification = qualification;
        this.specialization = specialization;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.fees = fees;
        this.shift = shift;
        this.timing = timing;
    }

    public void displayDetails() {
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Qualification: " + qualification);
        System.out.println("Specialization: " + specialization);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Fees: Rs. " + fees);
        System.out.println("Shift: " + shift);
        System.out.println("Timing: " + timing);
    }
}


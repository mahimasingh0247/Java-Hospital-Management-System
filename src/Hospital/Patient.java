
package Hospital;

public class Patient {
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String dateOfBirth;
    private String email;
    private String maritalStatus;
    private String address;
    private String phoneNumber;
    private String disease;

    public Patient(int patientId, String name, int age,
                   String gender, String dateOfBirth,
                   String email, String maritalStatus,
                   String address, String phoneNumber,
                   String disease) {

        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.maritalStatus = maritalStatus;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.disease = disease;
    }

    public void displayDetails() {
        System.out.println("Patient ID: " + patientId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Email: " + email);
        System.out.println("Marital Status: " + maritalStatus);
        System.out.println("Address: " + address);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Disease: " + disease);
    }
}



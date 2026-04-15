package dev.mr3.sb.model;

public class Doctor extends Person {
    private final String specialty;
    private final int id;
    private static int ID_Counter =1 ;

    public Doctor(String name, int age, boolean gender, String contact_no, String address, String specialty) {
        super(name, age, gender, contact_no, address);
        this.id = ID_Counter++;
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public int getId() {
        return id;
    }
}
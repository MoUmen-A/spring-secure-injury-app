package dev.mr3.sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
/**
 * Doctor entity storing specialty and person details.
 * Keywords: entity, doctor, specialty
 */
public class Doctor extends Person {
    @Column
    private  String specialty;

    public Doctor() {
    }
    public Doctor(String name, int age, boolean gender, String contact_no, String address, String specialty) {
        super(name, age, gender, contact_no, address);
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
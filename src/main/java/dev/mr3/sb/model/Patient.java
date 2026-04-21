package dev.mr3.sb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient extends Person {
    @Column
    private  String username;
    @Column
    private  String password;

    //need to check if we can use
    // @OneToMany for the appointments and injuries,
    // or if we need to create a
    // separate table for them
    //and if we can use @OneToMany,
    // we need to check if we can use it
    // with a List or if we need to use a Set
    @Column
    private  List<Appointment> reservations = new ArrayList<>();
    @Column
    private  List<String> reports = new ArrayList<>();
    @Column
    private  List<Injury> injuries = new ArrayList<>();

    public Patient() {
    }
    public Patient(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Appointment> getReservations() {
        return reservations;
    }
    public void setReservations(List<Appointment> reservations) {
        this.reservations = reservations;
    }
    public List<String> getReports() {
        return reports;
    }
    public void setReports(List<String> reports) {
        this.reports = reports;
    }
    public List<Injury> getInjuries() {
        return injuries;
    }
    public void setInjuries(List<Injury> injuries) {
        this.injuries = injuries;
    }
}
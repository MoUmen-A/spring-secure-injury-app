package dev.mr3.sb.model;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private  String username;
    private  String password;
    private  List<Appointment> reservations = new ArrayList<>();
    private  List<String> reports = new ArrayList<>();
    private  List<Injury> injuries = new ArrayList<>();

    public Patient() {
    }

    public Patient(String username, String password, String name, int age, boolean gender, String contact_no, String address) {
        super(name, age, gender, contact_no, address);
        this.username = username;
        this.password = password;
    }

     public Patient(String username, String password) {
        this(username, password, "New Patient", 0, true, "00000000000", "");
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

    public List<String> getReports() {
        return reports;
    }

    public List<Injury> getInjuries() {
        return injuries;
    }

    public void addReservation(Appointment appointment) {
        if (appointment != null) {
            reservations.add(appointment);
        }
    }

    public void addReport(String report) {
        if (report != null && !report.trim().isEmpty()) {
            reports.add(report);
        }
    }

    public void addInjury(Injury injury) {
        if (injury != null) {
            injuries.add(injury);
        }
    }

    public Patient updateDetails(String name, int age, boolean gender, String contact_no, String address) {
        Patient updated = new Patient(this.username, this.password, name, age, gender, contact_no, address);
        // Copy all reservations
        updated.reservations.addAll(this.reservations);
        // Copy all reports
        updated.reports.addAll(this.reports);
        // Copy all injuries
        updated.injuries.addAll(this.injuries);
        return updated;
    }
}
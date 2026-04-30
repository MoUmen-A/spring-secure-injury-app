package dev.mr3.sb.model;

import jakarta.persistence.*;

@Entity
/**
 * Report entity linking patient, injury, treatment, and appointment.
 * Keywords: entity, report, diagnosis
 */
public class Report {

    @Id
    @GeneratedValue
    private Long id;

    //still need to add retaltionship with
    // patient
    // injury
    // treatment
    // appointment
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "injury_id")
    private Injury injury;

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    public Report() {
    }
    public Report(Patient patient, Injury injury, Treatment treatment, Appointment appointment) {
        this.patient = patient;
        this.injury = injury;
        this.treatment = treatment;
        this.appointment = appointment;
    }
    public Long getId() {
        return id;
    }
    public Patient getPatient() {
        return patient;
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    public Injury getInjury() {
        return injury;
    }
    public void setInjury(Injury injury) {
        this.injury = injury;
    }
    public Treatment getTreatment() {
        return treatment;
    }
    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }
    public Appointment getAppointment() {
        return appointment;
    }
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
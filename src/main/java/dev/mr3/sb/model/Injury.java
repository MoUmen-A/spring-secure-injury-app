package dev.mr3.sb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table (name = "injury")
/**
 * Injury entity describing type, body part, and patient notes.
 * Keywords: entity, injury, assessment
 */
public class Injury {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotBlank(message = "Injury type is required")
    private String type;

    @Column
    @NotBlank(message = "Body part is required")
    private String bodyPart;

    @Column
    private boolean movable;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Description is required")
    private String athleteDescription;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Injury() {
    }

    public Injury(String type, String bodyPart, boolean movable, String athleteDescription) {
        this.type = type;
        this.bodyPart = bodyPart;
        this.movable = movable;
        this.athleteDescription = athleteDescription;
    }

    public Long getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getBodyPart() {
        return bodyPart;
    }
    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }
    public boolean isMovable() {
        return movable;
    }
    public void setMovable(boolean movable) {
        this.movable = movable;
    }
    public String getAthleteDescription() {
        return athleteDescription;
    }
    public void setAthleteDescription(String athleteDescription) {
        this.athleteDescription = athleteDescription;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}

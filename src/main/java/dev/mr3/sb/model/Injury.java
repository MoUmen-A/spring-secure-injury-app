package dev.mr3.sb.model;

import jakarta.persistence.*;

@Entity
/**
 * Injury entity describing type, body part, and patient notes.
 * Keywords: entity, injury, assessment
 */
public class Injury {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String type;

    @Column
    private String bodyPart;

    @Column
    private boolean movable;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String athleteDescription;

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
}
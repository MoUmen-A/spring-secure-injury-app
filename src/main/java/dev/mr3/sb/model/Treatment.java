package dev.mr3.sb.model;

import jakarta.persistence.*;

@Entity
/**
 * Treatment entity and lookup helper for injury suggestions.
 * Keywords: entity, treatment, suggestion
 */
public class Treatment {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String injuryType;

    @Column
    private String treatmentSuggestion;


    public Treatment() {
    }
    public Treatment(String injuryType, String treatmentSuggestion) {
        this.injuryType = injuryType;
        this.treatmentSuggestion = treatmentSuggestion;
    }

    public Long getId() {
        return id;
    }
    public String getInjuryType() {
        return injuryType;
    }
    public void setInjuryType(String injuryType) {
        this.injuryType = injuryType;
    }
    public String getTreatmentSuggestion() {
        return treatmentSuggestion;
    }
    public void setTreatmentSuggestion(String treatmentSuggestion) {
        this.treatmentSuggestion = treatmentSuggestion;
    }
}
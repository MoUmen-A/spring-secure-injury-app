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
    // Keep static method (not persisted)
    public static Treatment getTreatment(String injuryName) {
        String treatmentSuggestion = "";
        switch (injuryName) {
            case "Quadriceps Contusion":
                treatmentSuggestion = "Rest from impact activities, apply ice for 15–20 minutes every 2–3 hours, gently stretch as tolerated, and avoid massaging deep bruises early on.";
                break;
            // ... rest of cases
            default:
                treatmentSuggestion = "No specific treatment found. Consult a healthcare provider for proper care.";
                break;
        }
        return new Treatment(injuryName, treatmentSuggestion);
    }
}
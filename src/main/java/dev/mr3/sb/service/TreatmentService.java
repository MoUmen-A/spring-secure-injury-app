package dev.mr3.sb.service;

/**
 * Business logic placeholder for treatment lookup and suggestions.
 * Keywords: service, treatment, suggestion
 */
public class TreatmentService {
    public static dev.mr3.sb.model.Treatment getTreatment(String injuryName) {
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
        return new dev.mr3.sb.model.Treatment(injuryName, treatmentSuggestion);
    }
}

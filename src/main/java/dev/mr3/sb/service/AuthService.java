package dev.mr3.sb.service;
 
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
/**
 * Handles authentication and registration logic.
 */
public class AuthService {
 
    @Autowired
    private PatientRepository patientRepo;
 
    /**
     * Verifies credentials by Email.
     */
    public boolean validateLogin(Patient patient) {
        if (patient == null || patient.getEmail() == null || patient.getPassword() == null) {
            return false;
        }
 
        // Log in using Email as the unique key
        Patient dbPatient = patientRepo.findByEmail(patient.getEmail().toLowerCase().trim());
        return dbPatient != null && dbPatient.getPassword().equals(patient.getPassword());
    }
 
    /**
     * Validates and persists a new patient. Ensures Email is unique.
     */
    public void register(Patient patient) {
        if (patient == null || patient.getEmail() == null) {
            return;
        }
 
        // Normalize everything (Name, Age, Email, etc.)
        PersonValidation.validateAndNormalize(patient);
 
        // Ensure Email is unique before saving
        if (patientRepo.findByEmail(patient.getEmail()) == null) {
            patientRepo.save(patient);
            System.out.println("Patient registered with unique email: " + patient.getEmail());
        } else {
            System.out.println("Registration failed: Email already exists.");
        }
    }
}
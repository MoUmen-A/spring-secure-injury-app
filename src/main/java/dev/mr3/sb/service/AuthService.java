package dev.mr3.sb.service;

import dev.mr3.sb.model.Patient;
import dev.mr3.sb.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
/**
 * Handles authentication and registration logic.
 */
public class AuthService {

    private final PatientRepository patientRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthService(PatientRepository patientRepo, PasswordEncoder passwordEncoder) {
        this.patientRepo = patientRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Descriptive results for registration
    public enum RegistrationResult {
        SUCCESS,
        EMAIL_TAKEN,
        DATABASE_ERROR
    }

    /**
     * Verifies credentials by Email.
     */
    public Patient validateLogin(Patient patient) {
        if (patient == null || patient.getEmail() == null || patient.getPassword() == null) {
            return null;
        }
  
        Patient dbPatient = patientRepo.findByEmail(patient.getEmail().toLowerCase().trim());
        if (dbPatient == null) {
            return null;
        }

        String rawPassword = patient.getPassword();
        String storedPassword = dbPatient.getPassword();

        if (storedPassword != null && passwordEncoder.matches(rawPassword, storedPassword)) {
            return dbPatient;
        }

        // Backward compatibility for legacy plain-text records; migrate on successful login.
        if (storedPassword != null && storedPassword.equals(rawPassword)) {
            dbPatient.setPassword(passwordEncoder.encode(rawPassword));
            patientRepo.save(dbPatient);
            return dbPatient;
        }

        return null;
    }

    /**
     * Validates and persists a new patient.
     */
    public RegistrationResult register(Patient patient) {
        if (patient == null || patient.getEmail() == null || patient.getPassword() == null || patient.getPassword().isBlank()) {
            return RegistrationResult.DATABASE_ERROR;
        }

        // Normalize everything (Name, Age, Email, etc.)
        PersonValidation.validateAndNormalize(patient);

        // 1. Check if Email is taken
        if (patientRepo.findByEmail(patient.getEmail()) != null) {
            return RegistrationResult.EMAIL_TAKEN;
        }

        patient.setPassword(passwordEncoder.encode(patient.getPassword()));

        // 2. Try to save
        try {
            patientRepo.save(patient);
            return RegistrationResult.SUCCESS;
        } catch (Exception e) {
            System.err.println("Registration failed: " + e.getMessage());
            return RegistrationResult.DATABASE_ERROR;
        }
    }
}
package dev.mr3.sb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mr3.sb.model.Patient;
import dev.mr3.sb.repository.PatientRepository;

@Service
/**
 * Handles authentication and registration logic.
 */
public class AuthService {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private EmailService emailService;

    // Descriptive results for registration
    public enum RegistrationResult {
        SUCCESS_EMAIL_SENT,
        SUCCESS_EMAIL_FAILED,
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
        if (dbPatient != null && dbPatient.getPassword().equals(patient.getPassword())) {
            return dbPatient;
        }
        return null;
    }

    /**
     * Validates and persists a new patient.
     */
    public RegistrationResult register(Patient patient) {
        if (patient == null || patient.getEmail() == null) {
            return RegistrationResult.DATABASE_ERROR;
        }

        // Normalize everything (Name, Age, Email, etc.)
        PersonValidation.validateAndNormalize(patient);

        // 1. Check if Email is taken
        if (patientRepo.findByEmail(patient.getEmail()) != null) {
            return RegistrationResult.EMAIL_TAKEN;
        }

        // 2. Try to save
        try {
            patientRepo.save(patient);
            boolean emailSent = emailService.sendWelcomeEmail(patient.getEmail());
            if (!emailSent) {
                System.err.println("Welcome email failed: unable to send.");
            }
            return emailSent ? RegistrationResult.SUCCESS_EMAIL_SENT : RegistrationResult.SUCCESS_EMAIL_FAILED;
        } catch (Exception e) {
            System.err.println("Registration failed: " + e.getMessage());
            return RegistrationResult.DATABASE_ERROR;
        }
    }
}
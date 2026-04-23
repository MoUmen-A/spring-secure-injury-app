package dev.mr3.sb.service;

import dev.mr3.sb.model.Patient;
import org.springframework.stereotype.Service;

@Service
public class LoginValidation {
    public boolean validateLogin(Patient patient) {
        System.out.println("Patient object: " + patient);
        if (patient == null) {
            System.out.println("Patient is null");
            return false;
        }

        System.out.println("Username: '" + patient.getUsername() + "'");
        System.out.println("Password: '" + patient.getPassword() + "'");

        if (patient.getUsername() == null || patient.getPassword() == null) {
            System.out.println("Username or password is null");
            return false;
        }

        if (patient.getUsername().isEmpty() || patient.getPassword().isEmpty()) {
            System.out.println("Username or password is empty");
            return false;
        }

        if (patient.getUsername().length() < 3 || patient.getPassword().length() < 6) {
            System.out.println("Length too short. Username length: " + patient.getUsername().length() + ", Password length: " + patient.getPassword().length());
            return false;
        }

        if (patient.getUsername().contains(" ") || patient.getPassword().contains(" ")) {
            System.out.println("Contains spaces");
            return false;
        }

        System.out.println("Validation passed!");
        return true;
    }
}
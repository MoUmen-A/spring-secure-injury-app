package dev.mr3.sb.service;
 
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
 
import java.util.List;
 
@Component
public class DataInitializer implements CommandLineRunner {
 
    private final PatientRepository patientRepo;
 
    public DataInitializer(PatientRepository patientRepo) {
        this.patientRepo = patientRepo;
    }
 
    @Override
    public void run(String... args) throws Exception {
        System.out.println("========== DATABASE CHECK ==========");
        
        // 1. Create a default user if none exists
        if (patientRepo.findByEmail("test@example.com") == null) {
            Patient testPatient = new Patient();
            testPatient.setName("Test User");
            testPatient.setEmail("test@example.com");
            testPatient.setPassword("password123");
            testPatient.setAge(25);
            testPatient.setContact_no("123456789");
            testPatient.setAddress("123 Test St");
            patientRepo.save(testPatient);
            System.out.println(">>> Created default user: test@example.com / password123");
        }
 
        // 2. Print all users currently in the DB
        List<Patient> patients = patientRepo.findAll();
        System.out.println("Current users in database:");
        for (Patient p : patients) {
            System.out.println("- Email: " + p.getEmail() + " | Password: " + p.getPassword());
        }
        System.out.println("====================================");
    }
}

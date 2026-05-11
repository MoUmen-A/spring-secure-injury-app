package dev.mr3.sb.service;
 
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.repository.DoctorRepository;
import dev.mr3.sb.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
 
import java.util.List;
 
@Component
public class DataInitializer implements CommandLineRunner {
 
    private final PatientRepository patientRepo;
    private final DoctorRepository doctorRepo;
 
    public DataInitializer(PatientRepository patientRepo, DoctorRepository doctorRepo) {
        this.patientRepo = patientRepo;
        this.doctorRepo = doctorRepo;
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

        seedDefaultDoctors();
 
        // 2. Print all users currently in the DB
        List<Patient> patients = patientRepo.findAll();
        System.out.println("Current users in database:");
        for (Patient p : patients) {
            System.out.println("- Email: " + p.getEmail() + " | Password: " + p.getPassword());
        }
        System.out.println("====================================");
    }

    private void seedDefaultDoctors() {
        seedDoctorsIfMissing("General",
                new Doctor("Dr. General One", 45, true, "100000001", "general1@example.com", "Main Clinic", "General"),
                new Doctor("Dr. General Two", 42, false, "100000002", "general2@example.com", "Main Clinic", "General"));
        seedDoctorsIfMissing("KNEE", new Doctor("Dr. Knee Specialist", 50, true, "100000003", "knee@example.com", "Sports Clinic", "KNEE"));
        seedDoctorsIfMissing("SHOULDER", new Doctor("Dr. Shoulder Specialist", 48, false, "100000004", "shoulder@example.com", "Sports Clinic", "SHOULDER"));
        seedDoctorsIfMissing("ANKLE", new Doctor("Dr. Ankle Specialist", 44, true, "100000005", "ankle@example.com", "Sports Clinic", "ANKLE"));
        seedDoctorsIfMissing("ARM", new Doctor("Dr. Arm Specialist", 39, false, "100000006", "arm@example.com", "Sports Clinic", "ARM"));
        seedDoctorsIfMissing("LEG", new Doctor("Dr. Leg Specialist", 41, true, "100000007", "leg@example.com", "Sports Clinic", "LEG"));
        seedDoctorsIfMissing("THIGH", new Doctor("Dr. Thigh Specialist", 43, true, "100000008", "thigh@example.com", "Sports Clinic", "THIGH"));
        seedDoctorsIfMissing("HAMSTRING", new Doctor("Dr. Hamstring Specialist", 40, false, "100000009", "hamstring@example.com", "Sports Clinic", "HAMSTRING"));
        seedDoctorsIfMissing("CALF", new Doctor("Dr. Calf Specialist", 46, true, "100000010", "calf@example.com", "Sports Clinic", "CALF"));
        seedDoctorsIfMissing("FOOT", new Doctor("Dr. Foot Specialist", 47, false, "100000011", "foot@example.com", "Sports Clinic", "FOOT"));
        seedDoctorsIfMissing("SHIN", new Doctor("Dr. Shin Specialist", 38, true, "100000012", "shin@example.com", "Sports Clinic", "SHIN"));
        seedDoctorsIfMissing("WRIST", new Doctor("Dr. Wrist Specialist", 36, false, "100000013", "wrist@example.com", "Sports Clinic", "WRIST"));
        seedDoctorsIfMissing("ELBOW", new Doctor("Dr. Elbow Specialist", 49, true, "100000014", "elbow@example.com", "Sports Clinic", "ELBOW"));
        seedDoctorsIfMissing("ACHILLES", new Doctor("Dr. Achilles Specialist", 51, false, "100000015", "achilles@example.com", "Sports Clinic", "ACHILLES"));
    }

    private void seedDoctorsIfMissing(String specialty, Doctor... doctors) {
        if (!doctorRepo.findBySpecialtyIgnoreCase(specialty).isEmpty()) {
            return;
        }
        for (Doctor doctor : doctors) {
            doctorRepo.save(doctor);
        }
        System.out.println(">>> Created default doctors for " + specialty);
    }
}

package dev.mr3.sb.service;

import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
/**
 * Business logic for managing doctors, including lookup and persistence.
 * Keywords: service, doctor, availability
 */
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> findAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        if (!doctors.isEmpty()) {
            return doctors;
        }

        seedDefaultDoctors();
        return doctorRepository.findAll();
    }

    public List<Doctor> findBySpecialty(String specialty) {
        if (specialty == null || specialty.isBlank()) {
            return doctorRepository.findAll();
        }
        return doctorRepository.findBySpecialtyIgnoreCase(specialty.trim());
    }

    public List<Doctor> findRecommendedDoctors(boolean critical, String bodyPart) {
        List<Doctor> doctors;

        if (!critical) {
            doctors = doctorRepository.findBySpecialtyIgnoreCase("General");
        } else {
            doctors = findBySpecialty(bodyPart);
        }

        if (!doctors.isEmpty()) {
            return doctors;
        }

        List<Doctor> generalDoctors = doctorRepository.findBySpecialtyIgnoreCase("General");
        if (!generalDoctors.isEmpty()) {
            return generalDoctors;
        }

        return findAllDoctors();
    }

    public Optional<Doctor> findDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public Doctor saveDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        PersonValidation.validateAndNormalize(doctor);
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    private void seedDefaultDoctors() {
        List<Doctor> defaults = new ArrayList<>();
        defaults.add(new Doctor("Dr. General One", 45, true, "100000001", "general1@example.com", "Main Clinic", "General"));
        defaults.add(new Doctor("Dr. General Two", 42, false, "100000002", "general2@example.com", "Main Clinic", "General"));
        defaults.add(new Doctor("Dr. Knee Specialist", 50, true, "100000003", "knee@example.com", "Sports Clinic", "KNEE"));
        defaults.add(new Doctor("Dr. Shoulder Specialist", 48, false, "100000004", "shoulder@example.com", "Sports Clinic", "SHOULDER"));
        defaults.add(new Doctor("Dr. Ankle Specialist", 44, true, "100000005", "ankle@example.com", "Sports Clinic", "ANKLE"));
        doctorRepository.saveAll(defaults);
    }
}

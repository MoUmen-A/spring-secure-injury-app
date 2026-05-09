package dev.mr3.sb.service;

import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.repository.DoctorRepository;
import org.springframework.stereotype.Service;

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
        return doctorRepository.findAll();
    }

    public List<Doctor> findBySpecialty(String specialty) {
        if (specialty == null || specialty.isBlank()) {
            return doctorRepository.findAll();
        }
        return doctorRepository.findBySpecialtyIgnoreCase(specialty.trim());
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
}

package dev.mr3.sb.repository;

import dev.mr3.sb.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access placeholder for patient entities.
 * Keywords: repository, patient, persistence
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByUsername(String username);
    Patient findByEmail(String email);
}

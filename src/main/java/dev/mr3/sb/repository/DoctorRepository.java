package dev.mr3.sb.repository;

import dev.mr3.sb.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/**
 * Data access placeholder for doctor entities.
 * Keywords: repository, doctor, persistence
 */
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findBySpecialtyIgnoreCase(String specialty);
}

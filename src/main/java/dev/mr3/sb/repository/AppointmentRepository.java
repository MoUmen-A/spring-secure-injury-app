package dev.mr3.sb.repository;

import dev.mr3.sb.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA repository for Appointment entities.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndAppointmentDateAndTime(Long doctorId, LocalDate appointmentDate, String time);

    List<Appointment> findByPatientId(Long patientId);
}

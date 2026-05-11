package dev.mr3.sb.service;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.model.Weekday;
import dev.mr3.sb.repository.AppointmentRepository;
import dev.mr3.sb.repository.DoctorRepository;
import dev.mr3.sb.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * Business logic for booking and managing appointments.
 */
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public void saveAppointment(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("appointment cannot be null");
		appointmentRepository.save(appointment);
	}

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> findAppointmentsForPatient(Patient patient) {
        if (patient == null || patient.getId() == null) {
            return List.of();
        }
        return appointmentRepository.findByPatientId(patient.getId());
    }

    public boolean isDoctorAvailable(Long doctorId, LocalDate appointmentDate, String time) {
        return !appointmentRepository.existsByDoctorIdAndAppointmentDateAndTime(doctorId, appointmentDate, time);
    }

    public Appointment bookAppointment(Long doctorId, Patient patient, LocalDate appointmentDate, String time) {
        if (doctorId == null) {
            throw new IllegalArgumentException("Please select a doctor.");
        }
        if (patient == null || patient.getId() == null) {
            throw new IllegalArgumentException("Please log in before booking an appointment.");
        }
        if (appointmentDate == null) {
            throw new IllegalArgumentException("Please choose an appointment date.");
        }
        if (time == null || time.isBlank()) {
            throw new IllegalArgumentException("Please choose an appointment time.");
        }
        if (!isDoctorAvailable(doctorId, appointmentDate, time)) {
            throw new IllegalArgumentException("This doctor already has an appointment at that day and hour.");
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found."));
        Patient managedPatient = patientRepository.getReferenceById(patient.getId());

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(managedPatient);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setWeekday(toWeekday(appointmentDate));
        appointment.setTime(time);

        return appointmentRepository.save(appointment);
    }

    private Weekday toWeekday(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return switch (dayOfWeek) {
            case MONDAY -> Weekday.MONDAY;
            case TUESDAY -> Weekday.TUESDAY;
            case WEDNESDAY -> Weekday.WEDNESDAY;
            case THURSDAY -> Weekday.THURSDAY;
            case FRIDAY -> Weekday.FRIDAY;
            case SATURDAY -> Weekday.SATURDAY;
            case SUNDAY -> Weekday.SUNDAY;
        };
    }
}

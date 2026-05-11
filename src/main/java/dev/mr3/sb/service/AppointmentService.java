package dev.mr3.sb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.repository.AppointmentRepository;

/**
 * Business logic for booking and managing appointments.
 */
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final EmailService emailService;

    public AppointmentService(AppointmentRepository appointmentRepository, EmailService emailService) {
        this.appointmentRepository = appointmentRepository;
        this.emailService = emailService;
    }

    public void saveAppointment(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("appointment cannot be null");
		appointmentRepository.save(appointment);
	}

    public boolean saveAppointmentAndNotify(Appointment appointment, String email) {
        if (appointment == null) throw new IllegalArgumentException("appointment cannot be null");
        appointmentRepository.save(appointment);
        if (email == null || email.isBlank()) {
            return false;
        }
        return emailService.sendAppointmentEmail(email);
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }
}

package dev.mr3.sb.service;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business logic for booking and managing appointments.
 */
@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void saveAppointment(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("appointment cannot be null");
		appointmentRepository.save(appointment);
	}

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }
}

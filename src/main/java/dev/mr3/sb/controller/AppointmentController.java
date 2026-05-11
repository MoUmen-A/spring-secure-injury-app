package dev.mr3.sb.controller;

import dev.mr3.sb.model.Patient;
import dev.mr3.sb.service.AppointmentService;
import dev.mr3.sb.service.DoctorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

	private final DoctorService doctorService;
	private final AppointmentService appointmentService;

	public AppointmentController(DoctorService doctorService, AppointmentService appointmentService) {
		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
	}

	/**
	 * Show form to create a new appointment.
	 * Exposes `doctors` in the model for the select element.
	 */
	@GetMapping({"", "/new"})
	public String newAppointment(@RequestParam(required = false) Long doctorId,
								 HttpSession session,
								 Model model,
								 RedirectAttributes redirectAttributes) {
		Patient user = (Patient) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		Long selectedDoctorId = doctorId != null ? doctorId : (Long) session.getAttribute("selectedDoctorId");
		if (selectedDoctorId == null) {
			redirectAttributes.addFlashAttribute("error", "Please select a doctor first.");
			return "redirect:/doctors/recommended";
		}

		var doctor = doctorService.findDoctorById(selectedDoctorId);
		if (doctor.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Doctor not found.");
			return "redirect:/doctors/recommended";
		}

		session.setAttribute("selectedDoctorId", selectedDoctorId);
		model.addAttribute("doctor", doctor.get());
		model.addAttribute("doctorId", selectedDoctorId);
		return "Appointment"; // Thymeleaf template Appointment.html
	}

	/**
	 * Handle form submission to create an appointment.
	 * Accepts doctorId, date and time. The `date` is converted to Weekday enum.
	 */
	@PostMapping
	public String createAppointment(@RequestParam("doctorId") Long doctorId,
									@RequestParam("date") String date,
									@RequestParam("time") String time,
									HttpSession session,
									RedirectAttributes redirectAttributes) {
		Patient user = (Patient) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

		try {
			LocalDate appointmentDate = LocalDate.parse(date);
			appointmentService.bookAppointment(doctorId, user, appointmentDate, time);
		} catch (DateTimeParseException ex) {
			redirectAttributes.addFlashAttribute("error", "Please choose a valid appointment date.");
			return "redirect:/appointments/new?doctorId=" + doctorId;
		} catch (IllegalArgumentException ex) {
			redirectAttributes.addFlashAttribute("error", ex.getMessage());
			return "redirect:/appointments/new?doctorId=" + doctorId;
		}

		session.removeAttribute("pendingInjuryId");
		session.removeAttribute("pendingInjuryCritical");
		session.removeAttribute("pendingInjuryBodyPart");
		session.removeAttribute("selectedDoctorId");
		redirectAttributes.addFlashAttribute("message", "You booked appointment successfully.");
		return "redirect:/dashboard";
	}
}

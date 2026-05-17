package dev.mr3.sb.controller;

import dev.mr3.sb.model.Patient;
import dev.mr3.sb.service.AppointmentService;
import dev.mr3.sb.service.DoctorService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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

	public AppointmentController(DoctorService doctorService,
								 AppointmentService appointmentService) {
		this.doctorService = doctorService;
		this.appointmentService = appointmentService;
	}

	/**
	 * Show appointment form
	 */
	@GetMapping({"", "/new"})
	public String newAppointment(
			@RequestParam(required = false) Long doctorId,
			HttpSession session,
			Model model,
			RedirectAttributes redirectAttributes,

			// Read cookies if they exist
			@CookieValue(value = "lastDoctorName", required = false) String lastDoctorName,
			@CookieValue(value = "lastAppointmentDate", required = false) String lastAppointmentDate,
			@CookieValue(value = "lastAppointmentTime", required = false) String lastAppointmentTime
	) {

		// Check login
		Patient user = (Patient) session.getAttribute("user");

		if (user == null) {
			return "redirect:/login";
		}

		// Get selected doctor
		Long selectedDoctorId =
				doctorId != null
						? doctorId
						: (Long) session.getAttribute("selectedDoctorId");

		if (selectedDoctorId == null) {
			redirectAttributes.addFlashAttribute(
					"error",
					"Please select a doctor first."
			);

			return "redirect:/doctors/recommended";
		}

		// Find doctor
		var doctor = doctorService.findDoctorById(selectedDoctorId);

		if (doctor.isEmpty()) {

			redirectAttributes.addFlashAttribute(
					"error",
					"Doctor not found."
			);

			return "redirect:/doctors/recommended";
		}

		// Save doctor in session
		session.setAttribute("selectedDoctorId", selectedDoctorId);

		// Send data to HTML
		model.addAttribute("doctor", doctor.get());
		model.addAttribute("doctorId", selectedDoctorId);

		// Send cookie values to HTML
		model.addAttribute("lastDoctorName", lastDoctorName);
		model.addAttribute("lastAppointmentDate", lastAppointmentDate);
		model.addAttribute("lastAppointmentTime", lastAppointmentTime);

		return "Appointment";
	}

	/**
	 * Create appointment
	 */
	@PostMapping
	public String createAppointment(

			@RequestParam("doctorId") Long doctorId,
			@RequestParam("date") String date,
			@RequestParam("time") String time,

			HttpSession session,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes
	) {

		// Check login
		Patient user = (Patient) session.getAttribute("user");

		if (user == null) {
			return "redirect:/login";
		}

		try {

			// Convert string to LocalDate
			LocalDate appointmentDate = LocalDate.parse(date);

			// Save appointment
			appointmentService.bookAppointment(
					doctorId,
					user,
					appointmentDate,
					time
			);

			// Get doctor name
			String doctorName = doctorService
					.findDoctorById(doctorId)
					.get()
					.getName();

			/*
			 * Create Cookies
			 */

			addCookie(
					response,
					"lastDoctorName",
					doctorName
			);

			addCookie(
					response,
					"lastAppointmentDate",
					date
			);

			addCookie(
					response,
					"lastAppointmentTime",
					time
			);

		}

		catch (DateTimeParseException ex) {

			redirectAttributes.addFlashAttribute(
					"error",
					"Please choose a valid appointment date."
			);

			return "redirect:/appointments/new?doctorId=" + doctorId;
		}

		catch (IllegalArgumentException ex) {

			redirectAttributes.addFlashAttribute(
					"error",
					ex.getMessage()
			);

			return "redirect:/appointments/new?doctorId=" + doctorId;
		}

		// Clear temporary session data
		session.removeAttribute("pendingInjuryId");
		session.removeAttribute("pendingInjuryCritical");
		session.removeAttribute("pendingInjuryBodyPart");
		session.removeAttribute("selectedDoctorId");

		// Success message
		redirectAttributes.addFlashAttribute(
				"message",
				"You booked appointment successfully."
		);

		return "redirect:/dashboard";
	}

	/**
	 * Helper method to create cookies
	 */
	private void addCookie(
			HttpServletResponse response,
			String name,
			String value
	) {

		Cookie cookie = new Cookie(name, value);

		// Cookie available in all project
		cookie.setPath("/");

		// 1 day
		cookie.setMaxAge(60 * 60 * 24);

		response.addCookie(cookie);
	}
}
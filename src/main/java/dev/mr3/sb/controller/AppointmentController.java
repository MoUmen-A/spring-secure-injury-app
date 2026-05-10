package dev.mr3.sb.controller;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Weekday;
import dev.mr3.sb.service.AppointmentService;
import dev.mr3.sb.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.time.LocalDate;

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
	public String newAppointment(Model model) {
		model.addAttribute("doctors", doctorService.findAllDoctors());
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
									RedirectAttributes redirectAttributes) {
		// convert date string to weekday enum
		Weekday weekday = WeekdayMapper.fromDateString(date);
		Appointment appointment = new Appointment();
		appointment.setWeekday(weekday);
		appointment.setTime(time);

		appointmentService.saveAppointment(appointment);

		redirectAttributes.addFlashAttribute("message", "Appointment booked successfully");
		redirectAttributes.addFlashAttribute("selectedDoctorId", doctorId);
		return "redirect:/dashboard";
	}

	// Helper to map LocalDate -> model.Weekday
	private static class WeekdayMapper {
		static Weekday fromDateString(String dateStr) {
			try {
				LocalDate d = LocalDate.parse(dateStr);
				DayOfWeek dow = d.getDayOfWeek();
				return switch (dow) {
					case MONDAY -> Weekday.MONDAY;
					case TUESDAY -> Weekday.TUESDAY;
					case WEDNESDAY -> Weekday.WEDNESDAY;
					case THURSDAY -> Weekday.THURSDAY;
					case FRIDAY -> Weekday.FRIDAY;
					case SATURDAY -> Weekday.SATURDAY;
					case SUNDAY -> Weekday.SUNDAY;
				};
			} catch (Exception e) {
				return Weekday.MONDAY;
			}
		}
	}
}

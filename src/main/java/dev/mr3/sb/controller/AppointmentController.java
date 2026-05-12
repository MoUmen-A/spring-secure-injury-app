package dev.mr3.sb.controller;

import dev.mr3.sb.model.Appointment;
import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Weekday;
import dev.mr3.sb.service.AppointmentService;
import dev.mr3.sb.service.DoctorService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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

	@GetMapping({"", "/new"})
	public String showForm(
			Model model,
			@CookieValue(value = "lastDoctorId", required = false) String lastDoctorId,
			@CookieValue(value = "lastDate", required = false) String lastDate,
			@CookieValue(value = "lastTime", required = false) String lastTime
	) {
		model.addAttribute("doctors", doctorService.findAllDoctors());
		model.addAttribute("lastDoctorId", lastDoctorId);
		model.addAttribute("lastDate", lastDate);
		model.addAttribute("lastTime", lastTime);
		return "Appointment";
	}

	@PostMapping
	public String book(@RequestParam Long doctorId,
					   @RequestParam String date,
					   @RequestParam String time,
					   RedirectAttributes redirectAttributes,
					   HttpServletResponse response) {

		Doctor doctor = doctorService.findDoctorById(doctorId)
				.orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

		Appointment appointment = new Appointment();


		appointmentService.saveAppointment(appointment);

		// Save last selections as cookies (1 day)
		addCookie(response, "lastDoctorId", String.valueOf(doctorId));
		addCookie(response, "lastDate", date);
		addCookie(response, "lastTime", time);

		redirectAttributes.addFlashAttribute("message", "Appointment booked successfully");
		redirectAttributes.addFlashAttribute("aptDate", date);
		redirectAttributes.addFlashAttribute("aptTime", time);
		redirectAttributes.addFlashAttribute("aptDoctor", doctor.getName());

		return "redirect:/dashboard";
	}

	private static Weekday toWeekday(String dateStr) {
		try {
			DayOfWeek dow = LocalDate.parse(dateStr).getDayOfWeek();
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

	private void addCookie(HttpServletResponse response, String name, String value) {
		Cookie c = new Cookie(name, value);
		c.setPath("/");
		c.setMaxAge(60);
		response.addCookie(c);
	}
}
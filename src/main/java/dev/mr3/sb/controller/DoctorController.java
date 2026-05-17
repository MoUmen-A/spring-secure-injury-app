package dev.mr3.sb.controller;

import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.service.DoctorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/doctors")
/*
 * Handles doctor-related endpoints for profile and specialty management.
 * Keywords: controller, doctor, scheduling
 */
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String listDoctors(@RequestParam(required = false) String specialty, Model model) {
        var doctors = doctorService.findBySpecialty(specialty);
        model.addAttribute("doctors", doctors);
        return "doctor-list";
    }

    @GetMapping("/recommended")
    public String recommendedDoctors(HttpSession session, Model model) {
        Patient user = (Patient) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Boolean critical = (Boolean) session.getAttribute("pendingInjuryCritical");
        String bodyPart = (String) session.getAttribute("pendingInjuryBodyPart");
        if (critical == null || bodyPart == null) {
            return "redirect:/injury/select";
        }

        var doctors = doctorService.findRecommendedDoctors(critical, bodyPart);
        model.addAttribute("doctors", doctors);
        model.addAttribute("critical", critical);
        model.addAttribute("bodyPart", bodyPart);
        model.addAttribute("specialtyLabel", critical ? bodyPart : "General");
        return "DoctorSelection";
    }

    @PostMapping("/select")
    public String selectDoctor(@RequestParam Long doctorId, HttpSession session) {
        Patient user = (Patient) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        session.setAttribute("selectedDoctorId", doctorId);
        return "redirect:/appointments/new?doctorId=" + doctorId;
    }

    @GetMapping("/{id}")
    public String getDoctor(@PathVariable Long id, Model model) {
        doctorService.findDoctorById(id).ifPresentOrElse(
                doctor -> model.addAttribute("doctor", doctor),
                () -> {
                    model.addAttribute("error", "Doctor not found");
                }
        );
        return "doctor-details";
    }

    @PostMapping
    public String createDoctor(Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/doctors";
    }

    @PostMapping("/{id}")
    public String updateDoctor(@PathVariable Long id, Doctor doctor) {
        doctorService.findDoctorById(id).ifPresent(
                existing -> {
                    existing.setName(doctor.getName());
                    existing.setAge(doctor.getAge());
                    existing.setGender(doctor.isGender());
                    existing.setContact_no(doctor.getContact_no());
                    existing.setAddress(doctor.getAddress());
                    existing.setSpecialty(doctor.getSpecialty());
                    doctorService.saveDoctor(existing);
                }
        );
        return "redirect:/doctors";
    }

    @PostMapping("/{id}/delete")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}

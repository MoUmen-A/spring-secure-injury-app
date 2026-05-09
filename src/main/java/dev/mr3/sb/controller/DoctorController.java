package dev.mr3.sb.controller;

import dev.mr3.sb.model.Doctor;
import dev.mr3.sb.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/doctors")
/**
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

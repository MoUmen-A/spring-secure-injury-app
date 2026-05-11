package dev.mr3.sb.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mr3.sb.model.Patient;
import dev.mr3.sb.service.AuthService;
import dev.mr3.sb.service.AuthService.RegistrationResult;
import jakarta.servlet.http.HttpSession;
 
@Controller
/**
 * Renders the login page and validates submitted credentials.
 */
public class AuthController {
    private final AuthService authService;
 
    AuthController(AuthService authService) {
        this.authService = authService;
    }
 
    @GetMapping("/login")
    public String viewLoginPage() {
        return "Login";
    }
 
    @PostMapping("/login")
    public String login(Patient data, Model model, HttpSession session) {
        Patient patient = authService.validateLogin(data);
 
        if (patient == null) {
            model.addAttribute("error", "Invalid email or password!");
            return "Login";
        }
 
        // Save to session
        session.setAttribute("user", patient);
        return "redirect:/dashboard";
    }
 
    @GetMapping("/dashboard")
    public String viewDashboard(HttpSession session, Model model) {
        Patient user = (Patient) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Shield the dashboard from non-logged-in users
        }
        model.addAttribute("patient", user);
        return "Dashboard";
    }
 
    @GetMapping("/signup")
    public String viewSignupPage() {
        return "SignUp";
    }
 
    @PostMapping("/signup")
    public String signup(Patient patient, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        RegistrationResult result = authService.register(patient);
 
        if (result == RegistrationResult.SUCCESS_EMAIL_SENT) {
            // Save to session so they are logged in immediately after signup
            session.setAttribute("user", patient);
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            redirectAttributes.addFlashAttribute("emailNotice", "Welcome email sent. Please check your inbox.");
            return "redirect:/dashboard";
        } else if (result == RegistrationResult.SUCCESS_EMAIL_FAILED) {
            session.setAttribute("user", patient);
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return "redirect:/dashboard";
        } else if (result == RegistrationResult.EMAIL_TAKEN) {
            model.addAttribute("error", "Registration failed: This Email is already in use.");
            return "SignUp";
        } else {
            model.addAttribute("error", "Registration failed: Database error.");
            return "SignUp";
        }
    }
 
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

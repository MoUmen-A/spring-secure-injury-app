package dev.mr3.sb.controller;


import dev.mr3.sb.model.Patient;
import dev.mr3.sb.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
/**
 * Renders the login page and validates submitted credentials.
 * Keywords: controller, login, authentication
 */
public class AuthController {
    private final AuthService authService;

    // constructor ,DI
    AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "Login";
    }

    @PostMapping("/login")
    public String login(Patient patient) {
        if (!authService.validateLogin(patient)) {
            return "Login";
        }
        return "Dashboard";
    }

    @GetMapping("/signup")
    public String viewSignupPage() {
        return "Signup";
    }

    @PostMapping("/signup")
    public String signup(Patient patient) {
        authService.register(patient);
        return "Login";
    }
}

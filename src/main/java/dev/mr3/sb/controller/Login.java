package dev.mr3.sb.controller;


import dev.mr3.sb.model.Patient;
import dev.mr3.sb.service.LoginValidation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Login{
    private final LoginValidation loginValidation;

    Login(LoginValidation loginValidation){
        this.loginValidation=loginValidation;
    }
    @GetMapping("/login")
    public String loginPage() {
        return "Login";
    }

@PostMapping("/login")
    public String login(Patient patient) {
    if (!loginValidation.validateLogin(patient)) {
        return "Login";
    }
    return "Dashboard";
}

}

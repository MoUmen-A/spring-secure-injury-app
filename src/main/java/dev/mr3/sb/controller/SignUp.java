package dev.mr3.sb.controller;

import dev.mr3.sb.model.Patient;
import dev.mr3.sb.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUp {

	@GetMapping("/signup")
	public String signupPage() {
		return "SignUp";
	}

	@PostMapping("/signup")
	public String signup(Patient patient) {
		return "Login";
	}

}



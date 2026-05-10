package dev.mr3.sb.controller;


import dev.mr3.sb.model.BodyPart;
import dev.mr3.sb.model.Injury;
import dev.mr3.sb.service.InjuryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Displays the injury selection form and submission flow.
 * Keywords: controller, injury, form
 */
@Controller
@RequestMapping ("/injury")
public class InjuryController {

    private final InjuryService injuryService;

    public InjuryController(InjuryService injuryService) {
        this.injuryService = injuryService;
    }

    @GetMapping ("/select")
    public String showForm(Model model) {
        model.addAttribute("injury", new Injury());
        model.addAttribute("bodyPart", BodyPart.values());
        return "selectInjury";
    }

    @PostMapping("/submit")
    public String submitInjury(@ModelAttribute Injury injury, Model model) {
        String result = injuryService.processAssessment(injury);
        model.addAttribute("assessmentResult", result);
        return "SelectInjury.html";
    }
}

package dev.mr3.sb.controller;


import dev.mr3.sb.model.BodyPart;
import dev.mr3.sb.model.Injury;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.service.InjuryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public String showForm(Model model, HttpSession session) {
        Patient user = (Patient) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("injury", new Injury());
        model.addAttribute("bodyPart", BodyPart.values());
        return "SelectInjury";
    }

    @PostMapping("/submit")
    public String submitInjury(@Valid @ModelAttribute Injury injury,
                               BindingResult bindingResult,
                               Model model,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        Patient user = (Patient) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in before reporting an injury.");
            return "redirect:/login";
        }

        if (bindingResult.hasErrors() || hasInvalidRequiredFields(injury)) {
            model.addAttribute("injury", injury);
            model.addAttribute("bodyPart", BodyPart.values());
            model.addAttribute("error", "Please enter the injury type and description.");
            return "SelectInjury";
        }

        String result = injuryService.processAssessment(injury, user);
        session.setAttribute("pendingInjuryId", injury.getId());
        session.setAttribute("pendingInjuryCritical", injuryService.checkCriticality(injury));
        session.setAttribute("pendingInjuryBodyPart", injury.getBodyPart());
        redirectAttributes.addFlashAttribute("assessmentResult", result);
        return "redirect:/doctors/recommended";
    }

    private boolean hasInvalidRequiredFields(Injury injury) {
        return injury.getBodyPart() == null || injury.getBodyPart().isBlank()
                || injury.getType() == null || injury.getType().isBlank()
                || injury.getAthleteDescription() == null || injury.getAthleteDescription().isBlank();
    }
}

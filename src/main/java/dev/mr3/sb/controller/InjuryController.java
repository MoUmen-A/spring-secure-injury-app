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

@Controller
@RequestMapping("/injury")
public class InjuryController {

    private static final String INJURY_PAGE = "SelectInjury";
    private static final String LOGIN_REDIRECT = "redirect:/login";
    private static final String DOCTOR_REDIRECT = "redirect:/doctors/recommended";

    private final InjuryService injuryService;

    public InjuryController(InjuryService injuryService) {
        this.injuryService = injuryService;
    }

    @GetMapping("/select")
    public String openInjuryPage(HttpSession session, Model model) {
        if (getLoggedPatient(session) == null) {
            return LOGIN_REDIRECT;
        }

        preparePage(model, new Injury());
        return INJURY_PAGE;
    }

    @PostMapping("/submit")
    public String submitInjury(@Valid @ModelAttribute("injury") Injury injury,
                               BindingResult result,
                               HttpSession session,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Patient patient = getLoggedPatient(session);

        if (patient == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in before reporting an injury.");
            return LOGIN_REDIRECT;
        }

        if (result.hasErrors() || isMissingData(injury)) {
            preparePage(model, injury);
            model.addAttribute("error", "Please enter the injury type and description.");
            return INJURY_PAGE;
        }

        String message = injuryService.processAssessment(injury, patient);
        saveInjuryForNextStep(session, injury);

        redirectAttributes.addFlashAttribute("assessmentResult", message);
        return DOCTOR_REDIRECT;
    }

    private Patient getLoggedPatient(HttpSession session) {
        return (Patient) session.getAttribute("user");
    }

    private void preparePage(Model model, Injury injury) {
        model.addAttribute("injury", injury);
        model.addAttribute("bodyPart", BodyPart.values());
    }

    private void saveInjuryForNextStep(HttpSession session, Injury injury) {
        session.setAttribute("pendingInjuryId", injury.getId());
        session.setAttribute("pendingInjuryCritical", injuryService.checkCriticality(injury));
        session.setAttribute("pendingInjuryBodyPart", injury.getBodyPart());
    }

    private boolean isMissingData(Injury injury) {
        return isBlank(injury.getBodyPart())
                || isBlank(injury.getType())
                || isBlank(injury.getAthleteDescription());
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}

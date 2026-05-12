package dev.mr3.sb.service;

import dev.mr3.sb.model.Injury;
import dev.mr3.sb.model.Patient;
import dev.mr3.sb.repository.InjuryRepository;
import dev.mr3.sb.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Business logic placeholder for injury assessment and routing.
 * Keywords: service, injury, assessment
 */
@Service
public class InjuryService {


    private final InjuryRepository injuryRepo;
    private final PatientRepository patientRepo;

    public InjuryService(InjuryRepository injuryRepo, PatientRepository patientRepo) {
        this.injuryRepo = injuryRepo;
        this.patientRepo = patientRepo;
    }

    public String processAssessment(Injury injury, Patient patient) {
        Patient managedPatient = patientRepo.getReferenceById(patient.getId());
        injury.setPatient(managedPatient);

        injuryRepo.save(injury);

        if (checkCriticality(injury)) {
            return "Your injury is critical";
        }
        return "Your injury is minor";
    }

    public List<Injury> findInjuriesForPatient(Patient patient) {
        return injuryRepo.findByPatientId(patient.getId());
    }

    public Optional<Injury> findById(Long injuryId) {
        return injuryRepo.findById(injuryId);
    }

    public boolean checkCriticality(Injury injury) {

        if (!injury.isMovable()) {
            return true;
        }
        return false;
    }
    
}

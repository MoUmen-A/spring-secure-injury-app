package dev.mr3.sb.repository;

import dev.mr3.sb.model.Injury;
import dev.mr3.sb.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * Data access placeholder for treatment entities.
 * Keywords: repository, treatment, persistence
 */
@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
}

package dev.mr3.sb.repository;

import dev.mr3.sb.model.Injury;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * Data access placeholder for injury entities.
 * Keywords: repository, injury, persistence
 */
@Repository
public interface InjuryRepository extends JpaRepository<Injury, Long> {

}

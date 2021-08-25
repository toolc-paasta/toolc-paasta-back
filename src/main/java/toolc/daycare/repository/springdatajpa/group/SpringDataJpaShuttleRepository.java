package toolc.daycare.repository.springdatajpa.group;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.group.Shuttle;
import toolc.daycare.repository.interfaces.group.ShuttleRepository;

public interface SpringDataJpaShuttleRepository extends JpaRepository<Shuttle, Long>, ShuttleRepository {
}

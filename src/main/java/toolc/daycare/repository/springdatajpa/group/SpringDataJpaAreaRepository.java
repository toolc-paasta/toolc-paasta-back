package toolc.daycare.repository.springdatajpa.group;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.group.Area;
import toolc.daycare.repository.interfaces.group.AreaRepository;

public interface SpringDataJpaAreaRepository extends JpaRepository<Area, Long>, AreaRepository {
}

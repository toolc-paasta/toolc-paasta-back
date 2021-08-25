package toolc.daycare.repository.springdatajpa.group;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.group.Center;
import toolc.daycare.repository.interfaces.group.CenterRepository;

public interface SpringDataJpaCenterRepository extends JpaRepository<Center, Long>, CenterRepository {
}

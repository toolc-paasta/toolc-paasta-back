package toolc.daycare.repository.springdatajpa.group;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.group.Class;
import toolc.daycare.repository.interfaces.group.ClassRepository;

public interface SpringDataJpaClassRepository extends JpaRepository<Class, Long>, ClassRepository {
}

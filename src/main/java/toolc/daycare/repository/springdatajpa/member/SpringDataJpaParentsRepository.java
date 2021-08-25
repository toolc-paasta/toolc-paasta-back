package toolc.daycare.repository.springdatajpa.member;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.repository.interfaces.member.ParentsRepository;

public interface SpringDataJpaParentsRepository extends JpaRepository<Parents, Long>, ParentsRepository {
}

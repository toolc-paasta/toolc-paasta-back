package toolc.daycare.repository.springdatajpa.member;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.member.Director;
import toolc.daycare.repository.interfaces.member.DirectorRepository;

public interface SpringDataJpaDirectorRepository extends JpaRepository<Director, Long>, DirectorRepository {
}

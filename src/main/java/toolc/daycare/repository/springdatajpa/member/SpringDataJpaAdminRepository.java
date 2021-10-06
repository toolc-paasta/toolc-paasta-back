package toolc.daycare.repository.springdatajpa.member;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.member.Admin;
import toolc.daycare.repository.interfaces.member.AdminRepository;
import toolc.daycare.repository.interfaces.member.DirectorRepository;

public interface SpringDataJpaAdminRepository extends JpaRepository<Admin, Long>, AdminRepository {
}

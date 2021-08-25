package toolc.daycare.repository.springdatajpa.member;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.repository.interfaces.member.TeacherRepository;

public interface SpringDataJpaTeacherRepository extends JpaRepository<Teacher, Long>, TeacherRepository {
}

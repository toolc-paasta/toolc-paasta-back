package toolc.daycare.repository.springdatajpa.member;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.member.Student;
import toolc.daycare.repository.interfaces.member.StudentRepository;

public interface SpringDataJpaStudentRepository extends JpaRepository<Student, Long>, StudentRepository {
}

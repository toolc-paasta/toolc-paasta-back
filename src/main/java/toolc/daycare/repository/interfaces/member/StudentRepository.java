package toolc.daycare.repository.interfaces.member;

import toolc.daycare.domain.member.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);
    Optional<Student> findById(Long id);
    List<Student> findByaClassId(Long classId);
}

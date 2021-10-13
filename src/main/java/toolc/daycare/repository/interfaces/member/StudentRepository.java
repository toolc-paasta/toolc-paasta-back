package toolc.daycare.repository.interfaces.member;

import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student save(Student student);
    List<Student> findByaClassId(Long id);
}

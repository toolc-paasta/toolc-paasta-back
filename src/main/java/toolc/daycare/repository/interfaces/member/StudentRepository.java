package toolc.daycare.repository.interfaces.member;

import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Student;

public interface StudentRepository {
    Student save(Student student);
}

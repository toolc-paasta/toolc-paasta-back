package toolc.daycare.repository.interfaces.member;

import toolc.daycare.domain.group.Class;
import toolc.daycare.domain.member.Teacher;

import java.util.Optional;

public interface TeacherRepository {
    Teacher save(Teacher center);
    Optional <Teacher> findByLoginId(String loginId);
    Teacher findByaClassId(Long classId);
}

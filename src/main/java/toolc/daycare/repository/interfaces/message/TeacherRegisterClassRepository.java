package toolc.daycare.repository.interfaces.message;

import toolc.daycare.domain.message.CenterRegisterMessage;
import toolc.daycare.domain.message.TeacherRegisterClassMessage;

import java.util.List;
import java.util.Optional;


public interface TeacherRegisterClassRepository {
    TeacherRegisterClassMessage save(TeacherRegisterClassMessage registerMessage);
    List<TeacherRegisterClassMessage> findByCenterId(Long id);
    Optional<TeacherRegisterClassMessage> findById(Long id);
    void deleteById(Long id);
}

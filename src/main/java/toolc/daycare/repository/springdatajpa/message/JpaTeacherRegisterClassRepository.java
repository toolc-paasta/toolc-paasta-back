package toolc.daycare.repository.springdatajpa.message;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.message.CenterRegisterMessage;
import toolc.daycare.domain.message.TeacherRegisterClassMessage;
import toolc.daycare.repository.interfaces.message.CenterRegisterRepository;
import toolc.daycare.repository.interfaces.message.TeacherRegisterClassRepository;


public interface JpaTeacherRegisterClassRepository extends JpaRepository<TeacherRegisterClassMessage, Long>,
  TeacherRegisterClassRepository {

}

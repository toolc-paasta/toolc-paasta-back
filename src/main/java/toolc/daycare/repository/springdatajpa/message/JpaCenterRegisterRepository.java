package toolc.daycare.repository.springdatajpa.message;

import org.springframework.data.jpa.repository.JpaRepository;
import toolc.daycare.domain.message.CenterRegisterMessage;
import toolc.daycare.repository.interfaces.message.CenterRegisterRepository;


public interface JpaCenterRegisterRepository extends JpaRepository<CenterRegisterMessage, Long>,
  CenterRegisterRepository {

}

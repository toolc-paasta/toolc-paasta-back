package toolc.daycare.repository.interfaces.message;

import toolc.daycare.domain.message.CenterRegisterMessage;

import java.util.List;
import java.util.Optional;


public interface CenterRegisterRepository {
    CenterRegisterMessage save(CenterRegisterMessage registerMessage);
    List<CenterRegisterMessage> findAll();
    Optional<CenterRegisterMessage> findById(Long id);
    void deleteById(Long id);
}

package toolc.daycare.repository.interfaces.message;

import toolc.daycare.domain.message.CenterRegisterMessage;

import java.util.List;


public interface CenterRegisterRepository {
    CenterRegisterMessage save(CenterRegisterMessage registerMessage);
    List<CenterRegisterMessage> findAll();

}

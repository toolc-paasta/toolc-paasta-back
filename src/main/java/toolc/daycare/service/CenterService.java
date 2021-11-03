package toolc.daycare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.message.CenterRegisterMessage;
import toolc.daycare.exception.AlreadyMatchCenterException;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.message.CenterRegisterRepository;

import java.time.LocalDate;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@Service
@RequiredArgsConstructor
public class CenterService {

    private final CenterRepository centerRepository;
    private final CenterRegisterRepository registerRepository;


    public Center register(Long messageId) {
        CenterRegisterMessage allow = registerRepository.findById(messageId).get();
        Center center = Center.builder()
          .name(allow.getCenterName())
          .address(allow.getAddress())
          .foundationDate(allow.getFoundationDate())
          .build();
        checkNotExistCenter(allow.getDirector());



        return centerRepository.save(center);
    }


    private void checkNotExistCenter(Director director){
        if(centerRepository.findByDirectorId(director.getId()) != null){
            throw new AlreadyMatchCenterException();
        }
    }
}

package toolc.daycare.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Director;
import toolc.daycare.exception.AlreadyMatchCenterException;
import toolc.daycare.repository.interfaces.group.CenterRepository;

import java.time.LocalDate;

@Slf4j
@Service
public class CenterService {

    private final CenterRepository centerRepository;

    @Autowired
    public CenterService(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    public Center register(Director director,
                           String name, String address, LocalDate foundationDate) {
        checkNotExistCenter(director);
        Center center = Center.builder()
                .name(name)
                .address(address)
                .foundationDate(foundationDate)
                .build();
        center.setDirector(director);

        return centerRepository.save(center);
    }


    private void checkNotExistCenter(Director director){
        if(centerRepository.findByDirectorId(director.getId()) != null){
            throw new AlreadyMatchCenterException();
        }
    }
}

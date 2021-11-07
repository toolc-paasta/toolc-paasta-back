package toolc.daycare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.group.Class;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.message.CenterRegisterMessage;
import toolc.daycare.exception.AlreadyMatchCenterException;
import toolc.daycare.exception.NoExistCenterException;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.message.CenterRegisterRepository;
import toolc.daycare.service.fcm.FcmSender;
import toolc.daycare.repository.interfaces.group.ClassRepository;
import toolc.daycare.vo.CenterVO;
import toolc.daycare.vo.ClassVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@Service
@RequiredArgsConstructor
public class CenterService {
  private final CenterRepository centerRepository;
  private final ClassRepository classRepository;

  public CenterVO register(Director director,
                           String name, String address, LocalDate foundationDate) {
    Center center = Center.builder()
      .name(name)
      .address(address)
      .foundationDate(foundationDate)
      .channelName(director.getLoginId() + '_' + name)
      .build();
    center.setDirector(director);

    centerRepository.save(center);

    return CenterVO.builder()
      .name(center.getName())
      .channelName(center.getChannelName())
      .star(0L)
      .directorName(director.getName())
      .address(center.getAddress())
      .foundationDate(center.getFoundationDate())
      .build();
  }

  public ClassVO createClass(Long directorId, String name) {
    Center center = centerRepository.findByDirectorId(directorId).orElseThrow(NoExistCenterException::new);
    Class aClass = Class.builder()
      .name(name)
      .build();
    aClass.setCenter(center);
    classRepository.save(aClass);

    return new ClassVO(center.getName(), aClass.getName());
  }

  public Optional<Center> findCenter(Long directorId) {
    return centerRepository.findByDirectorId(directorId);
  }

  public List<Center> getAllCenter() {
    return centerRepository.findAll();
  }

  public List<ClassVO> getAllClassByCenterId(Long centerId) {
    return classRepository.findByCenterId(centerId)
      .stream()
      .map(c -> new ClassVO(c.getCenter().getName(), c.getName()))
      .collect(Collectors.toList());
  }
}

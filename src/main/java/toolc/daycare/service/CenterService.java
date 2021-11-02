package toolc.daycare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.group.Class;
import toolc.daycare.domain.member.Director;
import toolc.daycare.exception.AlreadyMatchCenterException;
import toolc.daycare.exception.NoExistCenterException;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.group.ClassRepository;
import toolc.daycare.vo.ClassVO;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CenterService {

  private final CenterRepository centerRepository;
  private final ClassRepository classRepository;

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

  private void checkNotExistCenter(Director director) {

    if (centerRepository.findByDirectorId(director.getId()) != null) {
      throw new AlreadyMatchCenterException();
    }
  }

  public ClassVO createClass(Center center, String name) {
    Class aClass = Class.builder()
      .name(name)
      .build();
    aClass.setCenter(center);

    classRepository.save(aClass);

    return new ClassVO(center.getName(), aClass.getName());
  }
}

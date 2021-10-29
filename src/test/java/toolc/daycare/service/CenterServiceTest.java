package toolc.daycare.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.group.Class;
import toolc.daycare.repository.interfaces.group.CenterRepository;
import toolc.daycare.repository.interfaces.group.ClassRepository;
import toolc.daycare.vo.ClassVO;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static toolc.daycare.Fixture.center;
import static toolc.daycare.Fixture.director;

@ExtendWith(MockitoExtension.class)
class CenterServiceTest {
  @Mock
  CenterRepository centerRepository;
  @Mock
  ClassRepository classRepository;

  @InjectMocks
  CenterService centerService;

  @Test
  void 반생성() {
    // given
    Center center = center().build();
    center.setDirector(director().build());
    String name = "class001";
    String channelName = center.getDirector().getLoginId() + '_' + name;
    ClassVO target = new ClassVO(name, channelName);
    given(classRepository.save(any())).willReturn(any());

    // when
    ClassVO classVO = centerService.createClass(center, name);

    // then
    assertThat(classVO, is(target));
  }
}
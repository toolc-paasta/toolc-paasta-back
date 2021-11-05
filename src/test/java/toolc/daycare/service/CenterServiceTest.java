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
import toolc.daycare.vo.CenterVO;
import toolc.daycare.vo.ClassVO;

import java.time.LocalDate;

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
  void 센터생성() {
    // given
    String name = "center001";
    String address = "address001";
    LocalDate foundationDate = LocalDate.of(1998, 02, 25);
    CenterVO target = CenterVO.builder()
      .directorName(director().build().getName())
      .star(0L)
      .channelName(director().build().getLoginId() + '_' + name)
      .foundationDate(foundationDate)
      .address(address)
      .name(name)
      .build();

    given(centerRepository.save(any())).willReturn(any());

    // when
    CenterVO centerVO = centerService.register(director().build(), name, address, foundationDate);

    // then
    assertThat(centerVO, is(target));
  }
}
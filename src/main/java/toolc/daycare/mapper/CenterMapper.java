package toolc.daycare.mapper;

import toolc.daycare.domain.group.Center;
import toolc.daycare.vo.CenterDetailVO;
import toolc.daycare.vo.ClassVO;

import java.util.List;

public class CenterMapper {
  public CenterDetailVO toCenterDetailVo(Center center, List<ClassVO> classList) {
    return CenterDetailVO.builder()
      .name(center.getName())
      .channelName(center.getChannelName())
      .address(center.getAddress())
      .directorLoginId(center.getDirector().getLoginId())
      .directorName(center.getDirector().getName())
      .foundationDate(center.getFoundationDate())
      .star(center.getStar())
      .classVOList(classList)
      .build();
  }
}

package toolc.daycare.mapper;

import toolc.daycare.domain.group.Center;
import toolc.daycare.domain.member.Director;
import toolc.daycare.vo.DirectorVO;

public class DirectorMapper {
  public DirectorVO toDirectorVO(Director director) {
    return DirectorVO.builder()
      .loginId(director.getLoginId())
      .connectionNumber(director.getConnectionNumber())
      .name(director.getName())
      .sex(director.getSex())
      .token(director.getExpoToken())
      .build();
  }

  public DirectorVO toDirectorVOWithCenter(Director director, Center center) {
    return DirectorVO.builder()
      .loginId(director.getLoginId())
      .connectionNumber(director.getConnectionNumber())
      .name(director.getName())
      .sex(director.getSex())
      .token(director.getExpoToken())
      .centerName(center.getName())
      .centerId(center.getId())
      .build();
  }
}

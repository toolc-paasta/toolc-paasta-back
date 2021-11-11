package toolc.daycare.mapper;

import toolc.daycare.domain.member.Parents;
import toolc.daycare.vo.ParentDetailVO;

public class ParentMapper {
  public ParentDetailVO toParentWithDirectorVO(Parents parents) {
    return ParentDetailVO.builder()
      .name(parents.getName())
      .loginId(parents.getLoginId())
      .sex(parents.getSex())
      .connectionNumber(parents.getConnectionNumber())
      .childBirthday(parents.getChildBirthday())
      .childName(parents.getChildName())
      .childSex(parents.getChildSex())
      .childId(parents.getStudent().getId())
      .className(parents.getStudent().getAClass().getName())
      .centerName(parents.getStudent().getAClass().getCenter().getName())
      .directorLoginId(parents.getStudent().getAClass().getCenter().getDirector().getLoginId())
      .build();
  }

  public ParentDetailVO toParentWithDirectorVOExcludeClass(Parents parents) {
    return ParentDetailVO.builder()
      .name(parents.getName())
      .loginId(parents.getLoginId())
      .sex(parents.getSex())
      .connectionNumber(parents.getConnectionNumber())
      .childBirthday(parents.getChildBirthday())
      .childName(parents.getChildName())
      .childSex(parents.getChildSex())
      .childId(parents.getStudent().getId())
      .build();
  }

  public ParentDetailVO toParentWithDirectorVOExcludeDirector(Parents parents) {
    return ParentDetailVO.builder()
      .name(parents.getName())
      .loginId(parents.getLoginId())
      .sex(parents.getSex())
      .connectionNumber(parents.getConnectionNumber())
      .childBirthday(parents.getChildBirthday())
      .childName(parents.getChildName())
      .childSex(parents.getChildSex())
      .childId(parents.getStudent().getId())
      .className(parents.getStudent().getAClass().getName())
      .build();
  }
}

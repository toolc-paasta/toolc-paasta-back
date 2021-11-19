package toolc.daycare.mapper;

import toolc.daycare.domain.member.Teacher;
import toolc.daycare.vo.TeacherVO;

public class TeacherMapper {
  public TeacherVO toTeacherVO(Teacher teacher) {
    return TeacherVO.builder()
      .name(teacher.getName())
      .loginId(teacher.getLoginId())
      .connectionNumber(teacher.getConnectionNumber())
      .sex(teacher.getSex())
      .token(teacher.getExpoToken())
      .classId(teacher.getAClass().getId())
      .className(teacher.getAClass().getName())
      .directorLoginId(teacher.getAClass().getCenter().getDirector().getLoginId())
      .centerName(teacher.getAClass().getCenter().getName())
      .build();
  }

  public TeacherVO toTeacherVOExcludeClass(Teacher teacher) {
    return TeacherVO.builder()
      .name(teacher.getName())
      .loginId(teacher.getLoginId())
      .connectionNumber(teacher.getConnectionNumber())
      .sex(teacher.getSex())
      .token(teacher.getExpoToken())
      .build();
  }

  public TeacherVO toTeacherVOExcludeDirector(Teacher teacher) {
    return TeacherVO.builder()
      .name(teacher.getName())
      .loginId(teacher.getLoginId())
      .connectionNumber(teacher.getConnectionNumber())
      .sex(teacher.getSex())
      .token(teacher.getExpoToken())
      .classId(teacher.getAClass().getId())
      .className(teacher.getAClass().getName())
      .build();
  }
}

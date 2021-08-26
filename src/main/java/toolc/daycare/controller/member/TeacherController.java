package toolc.daycare.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.dto.member.teacher.TeacherSignupRequestDto;
import toolc.daycare.service.member.TeacherService;
import toolc.daycare.util.RequestUtil;

@Slf4j
@RestController
@RequestMapping("/api/member/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody TeacherSignupRequestDto teacherSignupRequestDto) {
        RequestUtil.checkNeedValue(
                teacherSignupRequestDto.getLoginId(),
                teacherSignupRequestDto.getPassword(),
                teacherSignupRequestDto.getName()
        );
        RequestUtil.checkCorrectEnum(
                teacherSignupRequestDto.getSex()
        );

        Teacher newTeacher = teacherService.signUp(
                teacherSignupRequestDto.getLoginId(),
                teacherSignupRequestDto.getPassword(),
                teacherSignupRequestDto.getName(),
                teacherSignupRequestDto.getSex()
        );

        return ResponseEntity.ok(newTeacher);
    }
}

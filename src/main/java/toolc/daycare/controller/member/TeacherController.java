package toolc.daycare.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toolc.daycare.domain.member.Director;
import toolc.daycare.domain.member.Teacher;
import toolc.daycare.dto.BaseResponseSuccessDto;
import toolc.daycare.dto.member.request.LoginRequestDto;
import toolc.daycare.dto.member.request.teacher.TeacherSignupRequestDto;
import toolc.daycare.dto.member.response.director.DirectorSignupResponseDto;
import toolc.daycare.dto.member.response.teacher.TeacherLoginResponseDto;
import toolc.daycare.dto.member.response.teacher.TeacherSignupResponseDto;
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

        BaseResponseSuccessDto responseBody = new TeacherSignupResponseDto(newTeacher);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signUp(@RequestBody LoginRequestDto loginRequestDto){
        RequestUtil.checkNeedValue(
                loginRequestDto.getLoginId(),
                loginRequestDto.getPassword()
        );

        Teacher loginTeacher = teacherService.login(
                loginRequestDto.getLoginId(),
                loginRequestDto.getPassword()
        );

        BaseResponseSuccessDto responseBody = new TeacherLoginResponseDto(loginTeacher);
        return ResponseEntity.ok(responseBody);
    }

}

package toolc.daycare.dto.member.student.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentTestDto {
    private String name;
    private String loginId;
    private String password;

    @Builder
    public StudentTestDto(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }
}

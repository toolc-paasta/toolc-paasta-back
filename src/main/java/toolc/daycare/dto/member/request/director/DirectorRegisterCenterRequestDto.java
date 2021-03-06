package toolc.daycare.dto.member.request.director;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.member.Sex;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DirectorRegisterCenterRequestDto {

    private String centerName;
    private String address;
    private LocalDate foundationDate;

    @Builder
    public DirectorRegisterCenterRequestDto(String centerName, String address, LocalDate foundationDate) {
        this.centerName = centerName;
        this.address = address;
        this.foundationDate = foundationDate;
    }
}

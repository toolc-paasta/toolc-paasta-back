package toolc.daycare.dto.group.request.center;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CenterRegisterRequestDto {

    private String directorLoginId;
    private String name;
    private String address;
    private Date foundationDate;

    private Long star;

    @Builder
    public CenterRegisterRequestDto(String directorLoginId, String name, String address, Date foundationDate, Long star) {
        this.directorLoginId = directorLoginId;
        this.name = name;
        this.address = address;
        this.foundationDate = foundationDate;
        this.star = star;
    }
}

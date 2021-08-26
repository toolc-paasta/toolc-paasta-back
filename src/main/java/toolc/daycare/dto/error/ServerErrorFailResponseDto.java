package toolc.daycare.dto.error;

import lombok.Builder;
import lombok.Getter;
import toolc.daycare.dto.BaseResponseFailDto;

@Getter
public class ServerErrorFailResponseDto extends BaseResponseFailDto {
    private final int status;
    private final String message;

    @Builder
    public ServerErrorFailResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

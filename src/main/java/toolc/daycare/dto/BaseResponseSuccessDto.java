package toolc.daycare.dto;

import lombok.Getter;

@Getter
public class BaseResponseSuccessDto {
    private final boolean success = true;
    private final ErrorDto error = null;
}

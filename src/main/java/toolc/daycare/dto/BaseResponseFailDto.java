package toolc.daycare.dto;

import lombok.Getter;

@Getter
public class BaseResponseFailDto {
    private final boolean success = false;
    private final Object response = null;
}

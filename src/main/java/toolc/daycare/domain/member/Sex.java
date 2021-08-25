package toolc.daycare.domain.member;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sex {

    MAN("남성"),
    WOMAN("여성");

    @JsonValue
    private String sex;
}

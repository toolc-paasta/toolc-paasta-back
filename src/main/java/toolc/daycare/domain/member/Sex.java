package toolc.daycare.domain.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sex {

    @JsonProperty("MAN")
    MAN("남성"),
    @JsonProperty("WOMAN")
    WOMAN("여성");

    @JsonValue
    private String sex;

    public String getSex() {
        return this.sex;
    }

    @JsonCreator
    public static Sex getSexFromCode(String value){
        for(Sex sex : Sex.values()){
            if(sex.getSex().equals(value)){
                return sex;
            }
        }
        return null;
    }
}

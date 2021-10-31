package toolc.daycare.dto.group.request.Class;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateClassRequestDto {
  private String name;
}

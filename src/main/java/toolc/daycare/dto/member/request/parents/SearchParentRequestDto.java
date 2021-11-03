package toolc.daycare.dto.member.request.parents;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchParentRequestDto {
  private String name;
  private String connectionNumber;
  @Builder
  public SearchParentRequestDto(String name, String connectionNumber) {
    this.name = name;
    this.connectionNumber = connectionNumber;
  }
}

package toolc.daycare.domain.group;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;
import toolc.daycare.domain.connection.ShuttleArea;
import toolc.daycare.domain.member.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Area extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    private List<ShuttleArea> shuttles = new ArrayList<>();

    @Builder
    public Area(String name) {
        this.name = name;
    }
}

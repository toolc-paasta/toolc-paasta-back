package toolc.daycare.domain.group;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;
import toolc.daycare.domain.connection.ShuttleArea;
import toolc.daycare.domain.member.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shuttle extends BaseEntity {
    private String driverName;

    @OneToMany(mappedBy = "shuttle", cascade = CascadeType.ALL)
    private List<ShuttleArea> areas = new ArrayList<>();

    @OneToMany(mappedBy = "shuttle", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

    @Builder
    public Shuttle(String driverName) {
        this.driverName = driverName;
    }

}

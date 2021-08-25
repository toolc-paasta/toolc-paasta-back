package toolc.daycare.domain.connection;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;
import toolc.daycare.domain.group.Area;
import toolc.daycare.domain.group.Class;
import toolc.daycare.domain.group.Shuttle;
import toolc.daycare.domain.member.Parents;
import toolc.daycare.domain.member.Student;
import toolc.daycare.domain.member.Teacher;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShuttleArea extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne
    @JoinColumn(name = "shuttle_id")
    private Shuttle shuttle;

}

package toolc.daycare.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.group.Area;
import toolc.daycare.domain.group.Class;
import toolc.daycare.domain.group.Shuttle;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends MemberBaseEntity {

    private Integer age;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class aClass;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne
    @JoinColumn(name = "shuttle_id")
    private Shuttle shuttle;

    @OneToMany(mappedBy = "student")
    private List<Parents> parents = new ArrayList<>();


    @Builder
    public Student(String loginId, String password, String name, String connectionNumber, Sex sex, Integer age) {
        super(loginId, password, name, connectionNumber, sex);
        this.age = age;
    }

    public void setaClass(Class aClass){
        this.aClass = aClass;
    }

    public void setArea(Area area){
        this.area = area;
    }

    public void setShuttle(Shuttle shuttle){
        this.shuttle = shuttle;
    }
}

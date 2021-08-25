package toolc.daycare.domain.group;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toolc.daycare.domain.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Center extends BaseEntity {

    private String name;


    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    private List<Class> classes = new ArrayList<>();

    @Builder
    public Center(String name) {
        this.name = name;
    }
}

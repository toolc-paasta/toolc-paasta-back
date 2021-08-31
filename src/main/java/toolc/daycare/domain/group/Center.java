package toolc.daycare.domain.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import toolc.daycare.domain.BaseEntity;
import toolc.daycare.domain.member.Director;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Center extends BaseEntity {

    private String name;
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date foundationDate;

    private Long star;


    @OneToOne
    @JoinColumn(name = "director_id")
    private Director director;


    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    private List<Class> classes = new ArrayList<>();

    @Builder

    public Center(String name, String address, Date foundationDate, Long star) {
        this.name = name;
        this.address = address;
        this.foundationDate = foundationDate;
        this.star = star;
    }

    public void setDirector(Director director) {
        this.director = director;
    }
}

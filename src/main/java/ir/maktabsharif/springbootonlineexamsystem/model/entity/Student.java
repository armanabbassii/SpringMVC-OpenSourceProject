package ir.maktabsharif.springbootonlineexamsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DiscriminatorValue("STUDENT")
public class Student extends User {
    private String major;

    //RELATIONS
    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private List<Course> courseList = new ArrayList<>();

}

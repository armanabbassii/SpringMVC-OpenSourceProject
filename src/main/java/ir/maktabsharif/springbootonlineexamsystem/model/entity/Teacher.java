package ir.maktabsharif.springbootonlineexamsystem.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("TEACHER")
public class Teacher extends User {

    private String specialization;
    private Double yearsOfExperience;
    private String employeeCode;

    //RELATIONS
//    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
//    private List<Course> courseList = new ArrayList<>();
}

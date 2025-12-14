package ir.maktabsharif.springbootonlineexamsystem.model.entity;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Course extends BaseEntity<Long> {

    @Column(nullable = false)
    private String title;
    private String courseUniqueId;
    @Column(nullable = false)
    private LocalDate startDateCourse;
    @Column(nullable = false)
    private LocalDate endDateCourse;

    //RELATIONS
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    // @JoinTable(
//            name = "role_authorities",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "authority_id")
//    )
    @ManyToMany
    @JoinTable(
            name = "course_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> studentList = new ArrayList<>();
}

package ir.maktabsharif.springbootonlineexamsystem.model.entity;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Course extends BaseEntity<Long> {

    @Column(nullable = false)
    private String title;
    @Column(nullable = false, unique = true)
    private String courseUniqueId;
    @Column(nullable = false)
    private LocalDate startDateCourse;
    @Column(nullable = false)
    private LocalDate endDateCourse;

    //RELATIONS
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<UserCourseRole> userCourseRoles = new ArrayList<>(); // one course == many user-course

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "teacher_id")
//    private Teacher teacher;

//    @ManyToMany
//    @JoinTable(
//            name = "course_students",
//            joinColumns = @JoinColumn(name = "course_id"),
//            inverseJoinColumns = @JoinColumn(name = "student_id")
//    )
//    private List<Student> studentList = new ArrayList<>();
}

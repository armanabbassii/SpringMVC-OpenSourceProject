package ir.maktabsharif.springbootonlineexamsystem.model.entity;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.base.BaseEntity;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.UserType;
import jakarta.persistence.*;

@Entity
public class UserCourseRole extends BaseEntity<Long> {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) //user in course
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false  )
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;

    @Column(nullable = false)
    private boolean active = true;
}

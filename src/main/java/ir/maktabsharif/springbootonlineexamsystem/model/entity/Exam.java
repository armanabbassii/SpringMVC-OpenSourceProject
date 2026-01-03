package ir.maktabsharif.springbootonlineexamsystem.model.entity;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Exam extends BaseEntity<Long> {
    @Column(nullable = false)
    @Size(max = 11)
    private String title;

    private String description;

    @Column(nullable = false)
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ExamQuestion> examQuestions = new ArrayList<>();
}

// valid date for start student exam
//teacher update before starting course
//teacher end of the  score
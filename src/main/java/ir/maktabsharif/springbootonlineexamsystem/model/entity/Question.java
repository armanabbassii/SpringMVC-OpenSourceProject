package ir.maktabsharif.springbootonlineexamsystem.model.entity;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.base.BaseEntity;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.QUESTION_TYPE;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question extends BaseEntity<Long> {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    // for banking question
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    //question creator
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionOption> questionOptions = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QUESTION_TYPE questionType;
}


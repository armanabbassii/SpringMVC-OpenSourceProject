package ir.maktabsharif.springbootonlineexamsystem.repository;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {
    //calculate
    List<ExamQuestion> findAllByExam_Id(Long examId);
//    @Query("""
//    select eq
//    from ExamQuestion eq
//    join fetch eq.question q
//    where eq.exam.id = :examId
//""")
//    List<ExamQuestion> findAllByExamIdWithQuestion(Long examId);
    //repetitive add question
    boolean existsByExam_IdAndQuestion_Id(Long examId, Long questionId);

}

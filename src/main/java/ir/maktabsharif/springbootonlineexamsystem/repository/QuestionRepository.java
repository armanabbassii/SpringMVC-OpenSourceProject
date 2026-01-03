package ir.maktabsharif.springbootonlineexamsystem.repository;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    //find list of question
    List<Question> findAllByCourse_idAndCreatedBy_idOrderByCreatedAtDesc(Long courseId, Long createdById);
    Optional<Question> findByIdAndCreatedBy_id(Long questionId, Long createdById);

    List<Question> findAllByCourse_IdAndCreatedBy_Id(
            Long courseId,
            Long createdById
    );
}

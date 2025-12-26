package ir.maktabsharif.springbootonlineexamsystem.repository;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.Exam;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findAllByCourse_IdAndCreatedBy_Id(
            Long courseId,
            Long teacherId
    );


}

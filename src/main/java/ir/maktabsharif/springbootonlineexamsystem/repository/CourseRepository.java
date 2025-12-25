package ir.maktabsharif.springbootonlineexamsystem.repository;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCourseUniqueId(String courseUniqueId);

}

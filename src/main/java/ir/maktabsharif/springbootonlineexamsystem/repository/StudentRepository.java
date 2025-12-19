package ir.maktabsharif.springbootonlineexamsystem.repository;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.Student;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByUserStatus(USER_STATUS userStatus);

}

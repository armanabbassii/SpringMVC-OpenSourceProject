package ir.maktabsharif.springbootonlineexamsystem.repository;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.UserCourseRole;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCourseRoleRepository extends JpaRepository<UserCourseRole, Long> {
    //for prevent exist one teacher for each course
    boolean existsByCourse_IdAndUserRole(Long courseId, USER_ROLE userRole);

    // for prevent duplicate same teacher to one course
    boolean existsByUser_IdAndCourse_Id(Long userId, Long courseId);

    // for changing role
    Optional<UserCourseRole> findByUser_IdAndCourse_Id(Long userId, Long courseId);

    //delete user from a course
    void deleteByUser_IdAndCourse_Id(Long userId, Long courseId);

    // list of users in a course
    List<UserCourseRole> findAllByCourse_Id(Long courseId);

    //list of users in a course in depends on role
    List<UserCourseRole> findAllByCourse_IdAndUserRole(
            Long courseId,
            USER_ROLE role
    );
    boolean existsByCourse_Id(Long courseId);

}

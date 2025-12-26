package ir.maktabsharif.springbootonlineexamsystem.repository;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.UserCourseRole;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // for finding course related to a teacher
//    List<UserCourseRole> findAllByUser_IdAndUserRole(Long userId, USER_ROLE userRole);

    @Query("""
            select ucr.course
            from UserCourseRole ucr
            where ucr.user.id = :userId
              and ucr.userRole = :role
            """)
    List<Course> findCoursesByUserAndRole(@Param("userId") Long userId,
                                          @Param("role") USER_ROLE role);

    boolean existsByUser_IdAndUserRole(Long userId, USER_ROLE role);
    boolean existsByUser_IdAndCourse_IdAndUserRole(Long userId, Long courseId, USER_ROLE userRole);

}

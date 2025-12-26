package ir.maktabsharif.springbootonlineexamsystem.service.impl;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.UserCourseRole;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import ir.maktabsharif.springbootonlineexamsystem.repository.CourseRepository;
import ir.maktabsharif.springbootonlineexamsystem.repository.UserCourseRoleRepository;
import ir.maktabsharif.springbootonlineexamsystem.repository.UserRepository;
import ir.maktabsharif.springbootonlineexamsystem.service.UserCourseRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class UserCourseRoleServiceImpl implements UserCourseRoleService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserCourseRoleRepository userCourseRoleRepository;

    @Transactional
    @Override
    public void assignTeacherToCourse(Long userId, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        if (user.getUserStatus() != USER_STATUS.APPROVED) {
            throw new IllegalStateException("user is not approved");
        }
//phase 2
        if (userCourseRoleRepository.existsByCourse_IdAndUserRole(courseId, USER_ROLE.TEACHER)) {
            throw new IllegalStateException("course already has a teacher ");
        }
        //phase 2

        if (userCourseRoleRepository.existsByUser_IdAndCourse_Id(userId, courseId)) {
            throw new IllegalStateException("User already assigned to this course");
        }

        UserCourseRole userCourseRole = UserCourseRole.builder()
                .user(user)
                .course(course)
                .userRole(USER_ROLE.TEACHER)
                .build();

        userCourseRoleRepository.save(userCourseRole);
    }


    //student
    @Transactional
    @Override
    public void addStudentToCourse(Long userId, Long courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        if (user.getUserStatus() != USER_STATUS.APPROVED) {
            throw new IllegalStateException("User is not approved");
        }

        if (userCourseRoleRepository
                .existsByUser_IdAndCourse_Id(userId, courseId)) {
            throw new IllegalStateException("User already enrolled in this course");
        }

        UserCourseRole userCourseRole = UserCourseRole.builder()
                .user(user)
                .course(course)
                .userRole(USER_ROLE.STUDENT)
                .build();
        userCourseRoleRepository.save(userCourseRole);
    }

    @Override
    public void removeUserFromCourse(Long userId, Long courseId) {

        UserCourseRole userCourseRole = userCourseRoleRepository
                .findByUser_IdAndCourse_Id(userId, courseId)
                .orElseThrow(() -> new IllegalStateException("User is not enrolled in this course"));
        //phase 2 ( for future features)
        if (userCourseRole.getUserRole() == USER_ROLE.TEACHER) {

        }
        if (userCourseRole.getUserRole() == USER_ROLE.STUDENT) {

        }
        userCourseRoleRepository.delete(userCourseRole);
    }


    @Transactional
    @Override
    public void changeUserRoleInCourse(Long userId, Long courseId, USER_ROLE newRole) {
        UserCourseRole userCourseRole = userCourseRoleRepository
                .findByUser_IdAndCourse_Id(userId, courseId)
                .orElseThrow(() -> new IllegalStateException("user is not assigned to this course"));
//                .orElseThrow(() -> new IllegalStateException("user is not assigned to this course"));کاربر به هیچ دوره ای اساین نشده، یا اصلا هنوز رولش مشخص نیست
        if (userCourseRole.getUserRole() == newRole) {
            return;
        }

        if (newRole == USER_ROLE.TEACHER) {
            boolean hasTeacher =
                    userCourseRoleRepository
                            .existsByCourse_IdAndUserRole(courseId, USER_ROLE.TEACHER);
            if (hasTeacher) {
                throw new IllegalStateException("Course already has a teacher");

            }
        }
        userCourseRole.setUserRole(newRole);


        userCourseRoleRepository.save(userCourseRole);
    }

    @Override
    public List<UserCourseRole> getCourseParticipants(Long courseId) {
        return userCourseRoleRepository.findAllByCourse_Id(courseId);
    }

    @Override
    public List<UserCourseRole> getCourseParticipantsByRole(Long courseId, USER_ROLE role) {
        return userCourseRoleRepository.findAllByCourse_IdAndUserRole(courseId, role);
    }
}



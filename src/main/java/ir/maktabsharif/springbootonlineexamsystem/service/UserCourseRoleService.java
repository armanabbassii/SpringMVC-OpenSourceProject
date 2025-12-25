package ir.maktabsharif.springbootonlineexamsystem.service;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.UserCourseRole;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;

import java.util.List;

public interface UserCourseRoleService {
    void assignTeacherToCourse(Long userId, Long courseId);

    void addStudentToCourse(Long userId, Long courseId);

    void removeUserFromCourse(Long userId, Long courseId);

    void changeUserRoleInCourse(Long userId, Long courseId, USER_ROLE newRole);

    List<UserCourseRole> getCourseParticipants(Long courseId);

    List<UserCourseRole> getCourseParticipantsByRole(Long courseId, USER_ROLE role);
}

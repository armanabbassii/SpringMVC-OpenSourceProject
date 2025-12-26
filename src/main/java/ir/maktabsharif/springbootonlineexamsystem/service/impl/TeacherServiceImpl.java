package ir.maktabsharif.springbootonlineexamsystem.service.impl;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.UserCourseRole;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;
import ir.maktabsharif.springbootonlineexamsystem.repository.UserCourseRoleRepository;
import ir.maktabsharif.springbootonlineexamsystem.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final UserCourseRoleRepository userCourseRoleRepository;
    private final SecurityService securityService;
//list of my teach courses
    @Override
    @Transactional(readOnly = true)
    public List<Course> getCoursesTeachByCurrentTeacher() {
        User currentUser = securityService.getCurrentUser();

        return userCourseRoleRepository.findCoursesByUserAndRole(
                currentUser.getId(),
                USER_ROLE.TEACHER

        );
    }
}

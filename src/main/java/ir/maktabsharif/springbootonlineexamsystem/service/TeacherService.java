package ir.maktabsharif.springbootonlineexamsystem.service;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.UserCourseRole;

import java.util.List;

public interface TeacherService {
    List<Course> getCoursesTeachByCurrentTeacher();
}

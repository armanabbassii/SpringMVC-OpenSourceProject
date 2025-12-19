package ir.maktabsharif.springbootonlineexamsystem.service;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.course.CourseDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    void create(CourseDto courseDto);

    void assignTeacher(Long courseId, Long teacherId);
    Course findById(Long id);

    void addStudent(Long courseId, Long studentId);
    void removeStudent(Long courseId, Long studentId);
}

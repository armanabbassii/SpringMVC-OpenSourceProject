package ir.maktabsharif.springbootonlineexamsystem.service;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.course.CourseDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.course.CourseUpdateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;

import java.util.List;

public interface CourseService {
    List<Course> findAll();

    void create(CourseDto courseDto);

    Course findById(Long id);

    void updateCourse(Long id, CourseUpdateDto courseUpdateDto);

    void deleteCourse(Long id);
}

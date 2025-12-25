package ir.maktabsharif.springbootonlineexamsystem.service.impl;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.course.CourseDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.course.CourseUpdateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import ir.maktabsharif.springbootonlineexamsystem.repository.CourseRepository;

import ir.maktabsharif.springbootonlineexamsystem.repository.UserCourseRoleRepository;
import ir.maktabsharif.springbootonlineexamsystem.service.CourseService;
import ir.maktabsharif.springbootonlineexamsystem.service.UserCourseRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserCourseRoleRepository userCourseRoleRepository;

    @Transactional
    @Override
    public void create(CourseDto courseDto) {
        if (courseDto.getStartDateCourse() != null && courseDto.getEndDateCourse() != null) {
            if (courseDto.getEndDateCourse().isBefore(courseDto.getStartDateCourse())) {
                throw new IllegalArgumentException("تاریخ پایان نمی‌تواند قبل از تاریخ شروع باشد");
            }
        }
        //In 18
        Course course = Course.builder()
                .title(courseDto.getTitle())
                .courseUniqueId(generateUniqueCourseId())
                .startDateCourse(courseDto.getStartDateCourse())
                .endDateCourse(courseDto.getEndDateCourse())
                .build();

        courseRepository.save(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("course not found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    private String generateUniqueCourseId() {
        String id = "CRS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        while (courseRepository.existsByCourseUniqueId(id)) {
            id = "CRS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        return id;
    }

    @Override
    @Transactional
    public void updateCourse(Long courseId, CourseUpdateDto courseUpdateDto) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalStateException("course not found"));
//        if(exam == RUNNING) {
//        }
//        if(courseStarted == True) {
//        }
//        if(courseHasExams == True) {
//        }
        if (courseUpdateDto.getEndDateCourse().isBefore(courseUpdateDto.getStartDateCourse())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        course.setTitle(courseUpdateDto.getTitle());
        course.setStartDateCourse(courseUpdateDto.getStartDateCourse());
        course.setEndDateCourse(courseUpdateDto.getEndDateCourse());

        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalStateException("course not found"));
//        if(exam == RUNNING) {
//        }
//        if(courseStarted == True) {
//        }
//        if(courseHasExams == True) {
//        }
        if (userCourseRoleRepository.existsByCourse_Id(id)) {
            throw new IllegalStateException("این دوره دارای اعضا است و قابل حذف نیست");
        }
        courseRepository.delete(course);


    }
}

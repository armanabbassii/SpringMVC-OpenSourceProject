package ir.maktabsharif.springbootonlineexamsystem.service.impl;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.course.CourseDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Student;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Teacher;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import ir.maktabsharif.springbootonlineexamsystem.repository.CourseRepository;
import ir.maktabsharif.springbootonlineexamsystem.repository.StudentRepository;
import ir.maktabsharif.springbootonlineexamsystem.repository.TeacherRepository;
import ir.maktabsharif.springbootonlineexamsystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

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

    private String generateUniqueCourseId() {
        String id = "CRS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        while (courseRepository.existsByCourseUniqueId(id)) {
            id = "CRS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
        return id;
    }

    @Override
    public void assignTeacher(Long courseId, Long teacherId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (teacher.getUserStatus() != USER_STATUS.APPROVED) {
            throw new IllegalStateException("Teacher is not approved");
        }
        course.setTeacher(teacher);
        courseRepository.save(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("course not found with id: " + id));
    }

    //student
    @Transactional
    @Override
    public void addStudent(Long courseId, Long studentId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow();
        // entity managed
        // keep in Persistence Context

        Student student = studentRepository.findById(studentId)
                .orElseThrow();

        if (!course.getStudentList().contains(student)) {
            course.getStudentList().add(student);
            // changed on entity managed
            // hibernate keep tracking
        }

    }

        @Transactional
        @Override
        public void removeStudent(Long courseId, Long studentId) {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow();

            course.getStudentList()
                    .removeIf(s -> s.getId().equals(studentId));
        }
}

package ir.maktabsharif.springbootonlineexamsystem.controller;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.course.CourseDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Student;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Teacher;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import ir.maktabsharif.springbootonlineexamsystem.repository.StudentRepository;
import ir.maktabsharif.springbootonlineexamsystem.repository.TeacherRepository;
import ir.maktabsharif.springbootonlineexamsystem.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @GetMapping
    public String courseList(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "admin/courses";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("form", new CourseDto());
        return "admin/createCourse";
    }

    @PostMapping("/create")
    public String create(
            @Valid @ModelAttribute("form") CourseDto courseDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/createCourse";
        }

        courseService.create(courseDto);
        return "redirect:/admin/courses";
    }

    @GetMapping("/{id}/assign-teacher")
    public String assignTeacherForm(@PathVariable Long id, Model model) {

        Course course = courseService.findById(id);
        List<Teacher> teachers =
                teacherRepository.findByUserStatus(USER_STATUS.APPROVED);

        model.addAttribute("course", course);
        model.addAttribute("teachers", teachers);

        return "admin/course-assign-teacher";
    }


    @PostMapping("/{id}/assign-teacher")
    public String assignTeacherSubmit(
            @PathVariable Long id,
            @RequestParam Long teacherId
    ) {
        courseService.assignTeacher(id, teacherId);
        return "redirect:/admin/courses";
    }


    //course-student
    @GetMapping("/{id}/students")
    public String courseStudents(
            @PathVariable Long id,
            Model model
    ) {
        Course course = courseService.findById(id);

        List<Student> students = course.getStudentList();

        model.addAttribute("course", course);
        model.addAttribute("students", students);

        return "admin/course-students";
    }

    @GetMapping("/{id}/students/add")
    public String addStudentForm(
            @PathVariable Long id,
            Model model
    ) {
        Course course = courseService.findById(id);

        List<Student> students =
                studentRepository.findByUserStatus(USER_STATUS.APPROVED);

        model.addAttribute("course", course);
        model.addAttribute("students", students);

        return "admin/course-add-student";
    }

    @PostMapping("/{id}/students/add")
    public String addStudentSubmit(
            @PathVariable Long id,
            @RequestParam Long studentId
    ) {
        courseService.addStudent(id, studentId);
        return "redirect:/admin/courses/" + id + "/students";
    }

    // delete student:
    @PostMapping("/{courseId}/students/{studentId}/remove")
    public String removeStudent(
            @PathVariable Long courseId,
            @PathVariable Long studentId
    ) {
        courseService.removeStudent(courseId, studentId);
        return "redirect:/admin/courses/" + courseId + "/students";
    }


    @GetMapping("/{id}/participants")
    public String courseParticipants(
            @PathVariable Long id,
            Model model
    ) {
        Course course = courseService.findById(id);

        model.addAttribute("course", course);
        model.addAttribute("teacher", course.getTeacher());
        model.addAttribute("students", course.getStudentList());

        return "admin/course-participants";
    }
}

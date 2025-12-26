package ir.maktabsharif.springbootonlineexamsystem.controller;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.exam.ExamCreateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.exam.ExamEditDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Exam;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.UserCourseRole;
import ir.maktabsharif.springbootonlineexamsystem.service.CourseService;
import ir.maktabsharif.springbootonlineexamsystem.service.ExamService;
import ir.maktabsharif.springbootonlineexamsystem.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final ExamService examService;
    private final CourseService courseService;

    @GetMapping("/courses")
    public String myCourses(Model model) {
        List<Course> courseList = teacherService.getCoursesTeachByCurrentTeacher();
        model.addAttribute("courses", courseList);
        return "teacher/courses";
    }

    @GetMapping("/courses/{courseId}/exams")
    public String examList(
            @PathVariable Long courseId,
            Model model
    ) {
        Course course = courseService.findById(courseId);
        List<Exam> examList = examService.getMyExamsForCourse(courseId);

        model.addAttribute("course", course);
        model.addAttribute("exams", examList);

        return "teacher/exams";
    }

    @GetMapping("/courses/{courseId}/exams/create")
    public String createExamForm(
            @PathVariable Long courseId,
            Model model
    ) {
        Course course = courseService.findById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("form", new ExamCreateDto());

        return "teacher/exam-create";
    }

    @PostMapping("/courses/{courseId}/exams/create")
    public String createExamSubmit(
            @PathVariable Long courseId,
            @Valid @ModelAttribute("form") ExamCreateDto form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Course course = courseService.findById(courseId);
            model.addAttribute("course", course);
            return "teacher/exam-create";
        }
        examService.createExam(courseId, form);
        return "redirect:/teacher/courses/" + courseId + "/exams";
    }

    @GetMapping("/exams/{examId}/edit")
    public String editExamForm(
            @PathVariable Long examId,
            Model model
    ) {
        ExamEditDto form = examService.getExamForEdit(examId);
        model.addAttribute("form", form);
        model.addAttribute("examId", examId);

        return "teacher/exam-edit";
    }

    @PostMapping("/exams/{examId}/edit")
    public String exitExamSubmit(
            @PathVariable Long examId,
            @Valid @ModelAttribute("form") ExamEditDto form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("examId", examId);
            return "teacher/exam-edit";
        }
        examService.updateExam(examId, form);

        Exam exam = examService.findById(examId);
        Long courseId = exam.getCourse().getId();

        return "redirect:/teacher/courses/" + courseId + "/exams";

    }

    @PostMapping("/exams/{examId}/delete")
    public String deleteExam(
            @PathVariable Long examId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Exam exam = examService.findById(examId);
            Long courseId = exam.getCourse().getId();

            examService.deleteExam(examId);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "آزمون با موفقیت حذف شد"
            );
            return "redirect:/teacher/courses/" + courseId + "/exams";

        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );
            return "redirect:/teacher/courses";
        }
    }
}

package ir.maktabsharif.springbootonlineexamsystem.controller;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.course.CourseDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.course.CourseUpdateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.UserCourseRole;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;

import ir.maktabsharif.springbootonlineexamsystem.service.CourseService;
import ir.maktabsharif.springbootonlineexamsystem.service.UserCourseRoleService;
import ir.maktabsharif.springbootonlineexamsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final UserCourseRoleService userCourseRoleService;
    private final UserService userService;


    @PostMapping("/{courseId}/users/{userId}/change-role")
    public String changeUserRoleInCourse(
            @PathVariable Long courseId,
            @PathVariable Long userId,
            @RequestParam USER_ROLE role
    ) {
        userCourseRoleService.changeUserRoleInCourse(userId, courseId, role);
        return "redirect:/admin/courses/" + courseId + "/participants";
    }

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
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/createCourse";
        }

        try {
            courseService.create(courseDto);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("logicError", ex.getMessage());
            return "admin/createCourse";
        }

        return "redirect:/admin/courses";
    }

    @GetMapping("/{id}/assign-teacher")
    public String assignTeacherForm(@PathVariable Long id, Model model) {

        Course course = courseService.findById(id);
        List<User> users =
                userService.findApprovedTeachers();

        model.addAttribute("course", course);
        model.addAttribute("users", users);

        return "admin/course-assign-teacher";
    }


    @PostMapping("/{id}/assign-teacher")
    public String assignTeacherSubmit(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        userCourseRoleService.assignTeacherToCourse(userId, id);
        return "redirect:/admin/courses/" + id + "/participants";
    }


    //course-student
    @GetMapping("/{id}/students")
    public String courseStudents(
            @PathVariable Long id,
            Model model
    ) {
        Course course = courseService.findById(id);

        List<UserCourseRole> students = userCourseRoleService.getCourseParticipantsByRole(id, USER_ROLE.STUDENT);

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

        List<User> users =
                userService.findApprovedStudents();

        model.addAttribute("course", course);
        model.addAttribute("users", users);

        return "admin/course-add-student";
    }

    @PostMapping("/{id}/students/add")
    public String addStudentSubmit(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        userCourseRoleService.addStudentToCourse(userId, id);
        return "redirect:/admin/courses/" + id + "/participants";
    }

    // delete student:
    @PostMapping("/{courseId}/users/{userId}/remove")
    public String removeUserFromCourse(
            @PathVariable Long courseId,
            @PathVariable Long userId
    ) {
        userCourseRoleService.removeUserFromCourse(userId, courseId);
        return "redirect:/admin/courses/" + courseId + "/participants";
    }


    @GetMapping("/{id}/participants")
    public String courseParticipants(
            @PathVariable Long id,
            Model model
    ) {
        Course course = courseService.findById(id);
        List<UserCourseRole> participants =
                userCourseRoleService.getCourseParticipants(id);
        model.addAttribute("course", course);
        model.addAttribute("participants", participants);

        return "admin/course-participants";
    }

    @GetMapping("/{id}/edit")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        CourseUpdateDto courseUpdateDto = new CourseUpdateDto(
                course.getTitle(),
                course.getStartDateCourse(),
                course.getEndDateCourse()
        );
        model.addAttribute("courseId", id);
        model.addAttribute("form", courseUpdateDto);
        return "admin/course-edit";
    }

    @PostMapping("/{id}/edit")
    public String editCourseSubmit(@PathVariable Long id,
                                   @Valid @ModelAttribute("form") CourseUpdateDto dto,
                                   BindingResult result) {
        if (result.hasErrors()) {
            return "admin/course-edit";
        }

        courseService.updateCourse(id, dto);
        return "redirect:/admin/courses";
    }

    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("successMessage", "دوره با موفقیت حذف شد");
        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/admin/courses";
    }
}

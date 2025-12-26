package ir.maktabsharif.springbootonlineexamsystem.controller;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.SYSTEM_ROLE;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;
import ir.maktabsharif.springbootonlineexamsystem.repository.UserCourseRoleRepository;
import ir.maktabsharif.springbootonlineexamsystem.service.impl.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class HomeController {
    private final SecurityService securityService;
    private final UserCourseRoleRepository userCourseRoleRepository;

    @GetMapping("/home")
    public String home(Model model) {

        User currentUser = securityService.getCurrentUser();
        boolean isAdmin = currentUser.getSystemRole() == SYSTEM_ROLE.ADMIN;
        boolean isTeacher = userCourseRoleRepository.existsByUser_IdAndUserRole(
                currentUser.getId(), USER_ROLE.TEACHER);

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isTeacher", isTeacher);

        return "home";    }
}
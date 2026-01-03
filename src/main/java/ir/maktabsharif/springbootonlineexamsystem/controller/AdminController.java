package ir.maktabsharif.springbootonlineexamsystem.controller;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.list.UserDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.list.UserSearchDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import ir.maktabsharif.springbootonlineexamsystem.service.UserCourseRoleService;
import ir.maktabsharif.springbootonlineexamsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final UserCourseRoleService userCourseRoleService;

    @GetMapping("/users/{id}/edit")
    public String getUserId(@PathVariable Long id, Model model) {
        model.addAttribute("form", userService.getUserById(id));
        model.addAttribute("statuses", USER_STATUS.values());
        return "admin/userUpdate";
    }

    @PostMapping("/users/{id}/edit")
    public String updateUser(
            @PathVariable Long id,
            @Valid @ModelAttribute("form") UserDto userDto,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("statuses", USER_STATUS.values());
        if (bindingResult.hasErrors()) {
            return "admin/userUpdate";
        }
        userService.updateUser(id, userDto);
        return "redirect:/admin/users";
    }

        @ModelAttribute("search")
        public UserSearchDto searchDto() {
            return new UserSearchDto();
        }

        @GetMapping("/users")
        public String userSearch(
                @ModelAttribute("search") UserSearchDto userSearchDto,
                Model model
        ) {
            model.addAttribute("users", userService.userSearch(userSearchDto));
            return "admin/users";
        }
}
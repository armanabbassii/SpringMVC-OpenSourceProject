package ir.maktabsharif.springbootonlineexamsystem.controller;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.list.UserDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.list.UserSearchDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
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




    @GetMapping("/users/{id}/edit")
    public String getUserId(@PathVariable Long id, Model model) {
        model.addAttribute("form", userService.getUserId(id));
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

    @GetMapping("/users/{id}/change-role")
    public String changeRoleForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);

        model.addAttribute("userId", user.getId());
        model.addAttribute("currentType", user.getDtype());
        model.addAttribute("types", List.of("STUDENT", "TEACHER"));

        return "admin/change-role";
    }
    @PostMapping("/users/{id}/change-role")
    public String changeUserRole(@PathVariable Long id,
                                 @RequestParam String targetType) {

        userService.changeUserType(id, targetType);
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
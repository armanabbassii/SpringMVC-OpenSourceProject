package ir.maktabsharif.springbootonlineexamsystem.controller;

import ir.maktabsharif.springbootonlineexamsystem.model.enums.REGISTER_TYPE;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.auth.UserRegisterDto;
import ir.maktabsharif.springbootonlineexamsystem.service.UserService;
import ir.maktabsharif.springbootonlineexamsystem.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new UserRegisterDto());
        model.addAttribute("types", REGISTER_TYPE.publicTypes());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerSubmit(
            @Valid @ModelAttribute("form") UserRegisterDto form,
            BindingResult bindingResult,
            Model model) {
        model.addAttribute("types", REGISTER_TYPE.publicTypes());
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        userService.register(form);
        return "redirect:/register-success";
    }

    @GetMapping("/register-success")
    public String registerSuccess() {
        return "auth/register-success";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}

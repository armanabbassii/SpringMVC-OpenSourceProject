package ir.maktabsharif.springbootonlineexamsystem.controller;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.question.DescriptiveQuestionCreateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.question.MCQQuestionCreateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.question.QuestionOptionDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Question;
import ir.maktabsharif.springbootonlineexamsystem.service.CourseService;
import ir.maktabsharif.springbootonlineexamsystem.service.ExamService;
import ir.maktabsharif.springbootonlineexamsystem.service.QuestionService;
import ir.maktabsharif.springbootonlineexamsystem.service.impl.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class QuestionController {
    private final CourseService courseService;
    private final QuestionService questionService;

    @GetMapping("/exams/{examId}/questions/new/descriptive")
    public String descriptiveQuestionForm(
            @PathVariable Long examId,
            Model model) {
        model.addAttribute("examId", examId);
        model.addAttribute("form", new DescriptiveQuestionCreateDto());
        return "teacher/question-descriptive-form";
    }

    @PostMapping("/exams/{examId}/questions/new/descriptive")
    public String createDescriptiveQuestion(
            @PathVariable Long examId,
            @Valid @ModelAttribute("form") DescriptiveQuestionCreateDto dto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("examId", examId);
            return "teacher/question-descriptive-form";
        }
        questionService.createDescriptiveQuestion(examId, dto);
        return "redirect:/teacher/exams/" + examId + "/questions";
    }


    @GetMapping("/exams/{examId}/questions/new/mcq")
    public String newMcqQuestionForm(
            @PathVariable Long examId,
            Model model
    ){
        MCQQuestionCreateDto dto = new MCQQuestionCreateDto();
        dto.getOptions().add(new QuestionOptionDto());
        dto.getOptions().add(new QuestionOptionDto());

        model.addAttribute("examId", examId);
        model.addAttribute("form", dto);

        return "teacher/question-mcq-form";
    }

    @PostMapping("/exams/{examId}/questions/new/mcq")
    public String createMcqQuestion(
            @PathVariable Long examId,
            @Valid @ModelAttribute("form") MCQQuestionCreateDto dto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("examId", examId);
            return "teacher/question-mcq-form";
        }

        questionService.createMCQQuestion(examId, dto);

        return "redirect:/teacher/exams/" + examId + "/questions";
    }
}


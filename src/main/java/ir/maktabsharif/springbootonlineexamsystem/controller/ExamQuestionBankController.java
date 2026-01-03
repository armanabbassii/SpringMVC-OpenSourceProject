package ir.maktabsharif.springbootonlineexamsystem.controller;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.Exam;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Question;
import ir.maktabsharif.springbootonlineexamsystem.service.ExamQuestionService;
import ir.maktabsharif.springbootonlineexamsystem.service.ExamService;
import ir.maktabsharif.springbootonlineexamsystem.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/teacher/exams/{examId}/questions")
@RequiredArgsConstructor
public class ExamQuestionBankController {
    private final QuestionService questionService;
    private final ExamService examService;
    private final ExamQuestionService examQuestionService;

    @GetMapping("/bank")
    public String questionBank(
            @PathVariable Long examId,
            Model model
    ) {
        Exam exam = examService.findById(examId);
        List<Question> questions =
                questionService.getQuestionBankForExam(examId);

        model.addAttribute("exam", exam);
        model.addAttribute("questions", questions);

        return "teacher/question-bank";
    }
    @PostMapping("/bank/add")
    public String addQuestionToExam(
            @PathVariable Long examId,
            @RequestParam Long questionId,
            @RequestParam Double defaultScore,
            RedirectAttributes redirectAttributes
    ) {
        try {
            examQuestionService.addQuestionToExam(examId, questionId, defaultScore);
            return "redirect:/teacher/exams/" + examId + "/questions";

        } catch (IllegalStateException ex) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    ex.getMessage()
            );
            return "redirect:/teacher/exams/" + examId + "/questions/bank";
        }
    }
}

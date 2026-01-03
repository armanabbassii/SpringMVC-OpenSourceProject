package ir.maktabsharif.springbootonlineexamsystem.service.impl;


import ir.maktabsharif.springbootonlineexamsystem.model.dto.question.DescriptiveQuestionCreateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.question.MCQQuestionCreateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.*;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.QUESTION_TYPE;
import ir.maktabsharif.springbootonlineexamsystem.repository.*;

import ir.maktabsharif.springbootonlineexamsystem.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final SecurityService securityService;

    private final ExamRepository examRepository;
    private final ExamQuestionRepository examQuestionRepository;

    @Override
    public void createDescriptiveQuestion(Long examId, DescriptiveQuestionCreateDto descriptiveQuestionCreateDto) {
        User currentUser = securityService.getCurrentUser();
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalStateException("exam not found"));
        if (!exam.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("Access denied");
        }
        Question question = Question.builder()
                .title(descriptiveQuestionCreateDto.getTitle())
                .content(descriptiveQuestionCreateDto.getContent())
                .questionType(QUESTION_TYPE.DESCRIPTIVE)
                .course(exam.getCourse())
                .createdBy(currentUser)
                .build();

        questionRepository.save(question);

        ExamQuestion examQuestion = ExamQuestion.builder()
                .exam(exam)
                .question(question)
                .defaultScore(descriptiveQuestionCreateDto.getDefaultScore())
                .build();

        examQuestionRepository.save(examQuestion);
    }

    @Override
    public void createMCQQuestion(Long examId, MCQQuestionCreateDto mcqQuestionCreateDto) {
        User currentUser = securityService.getCurrentUser();
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalStateException("exam not found"));
        if (!exam.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("Access denied");
        }
        Question question = Question.builder()
                .title(mcqQuestionCreateDto.getTitle())
                .content(mcqQuestionCreateDto.getContent())
                .questionType(QUESTION_TYPE.MCQ)
                .course(exam.getCourse())
                .createdBy(currentUser)
                .build();

        List<QuestionOption> options = mcqQuestionCreateDto.getOptions().stream()
                .filter(opt -> opt.getText() != null && !opt.getText().isBlank())
                .map(opt -> QuestionOption.builder()
                        .text(opt.getText().trim())
                        .correctAnswer(opt.isCorrectAnswer())
                        .question(question)
                        .build())
                .collect(Collectors.toList());
        question.setQuestionOptions(options);
        questionRepository.save(question);


        ExamQuestion examQuestion = ExamQuestion.builder()
                .exam(exam)
                .question(question)
                .defaultScore(mcqQuestionCreateDto.getDefaultScore())
                .build();
        examQuestionRepository.save(examQuestion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> getQuestionBankForExam(Long examId) {
        //course id
        User currentUser = securityService.getCurrentUser();

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalStateException("Exam not found"));

        Long courseId = exam.getCourse().getId();
        return questionRepository.findAllByCourse_IdAndCreatedBy_Id(
                courseId,
                currentUser.getId()
        );
    }
}

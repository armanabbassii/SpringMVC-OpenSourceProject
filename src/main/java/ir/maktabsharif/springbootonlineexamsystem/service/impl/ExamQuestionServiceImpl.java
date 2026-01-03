package ir.maktabsharif.springbootonlineexamsystem.service.impl;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.Exam;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.ExamQuestion;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Question;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.repository.ExamQuestionRepository;
import ir.maktabsharif.springbootonlineexamsystem.repository.ExamRepository;
import ir.maktabsharif.springbootonlineexamsystem.repository.QuestionRepository;
import ir.maktabsharif.springbootonlineexamsystem.service.ExamQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExamQuestionServiceImpl implements ExamQuestionService {
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ExamQuestionRepository examQuestionRepository;
    private final SecurityService securityService;

    @Override
    @Transactional
    public void addQuestionToExam(Long examId, Long questionId, Double defaultScore) {
        User currentUser = securityService.getCurrentUser();
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalStateException("Exam not found"));
        if (!exam.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("Access denied");
        }

        // question check
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalStateException("Question not found"));
        if (!question.getCourse().getId().equals(exam.getCourse().getId())) {
            throw new IllegalStateException("Question not in this course");
        }

        //Repeated question
        if (examQuestionRepository
                .existsByExam_IdAndQuestion_Id(examId, questionId)) {
            throw new IllegalStateException("Question already added to exam");
        }

        ExamQuestion examQuestion = ExamQuestion.builder()
                .exam(exam)
                .question(question)
                .defaultScore(defaultScore)
                .build();

        examQuestionRepository.save(examQuestion);
    }


}

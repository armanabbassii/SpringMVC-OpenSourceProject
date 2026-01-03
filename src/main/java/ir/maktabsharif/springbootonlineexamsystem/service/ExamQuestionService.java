package ir.maktabsharif.springbootonlineexamsystem.service;

public interface ExamQuestionService {
    void addQuestionToExam(Long examId, Long questionId, Double defaultScore);
}

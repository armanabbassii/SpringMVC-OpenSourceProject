package ir.maktabsharif.springbootonlineexamsystem.service;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.bank.DescriptiveQuestionBankDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.bank.MCQQuestionBankDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.question.DescriptiveQuestionCreateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.question.MCQQuestionCreateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Question;

import java.util.List;

public interface QuestionService {
    void createDescriptiveQuestion(Long examId, DescriptiveQuestionCreateDto descriptiveQuestionCreateDto);
    void createMCQQuestion(Long examId, MCQQuestionCreateDto mcqQuestionCreateDto);

    // questionBank
    List<Question> getQuestionBankForExam(Long examId);

}

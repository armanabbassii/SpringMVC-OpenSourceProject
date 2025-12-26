package ir.maktabsharif.springbootonlineexamsystem.service;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.exam.ExamCreateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.exam.ExamEditDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Exam;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;

import java.util.List;

public interface ExamService {
    List<Exam> getMyExamsForCourse(Long courseId);
    void createExam(Long courseId, ExamCreateDto examCreateDto);
    ExamEditDto getExamForEdit(Long examId);
    void updateExam(Long examId, ExamEditDto examEditDto);
    Exam findById(Long id);
    void deleteExam(Long  examId);

}

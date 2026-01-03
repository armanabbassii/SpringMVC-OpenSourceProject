package ir.maktabsharif.springbootonlineexamsystem.service.impl;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.exam.ExamCreateDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.exam.ExamEditDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.question.ExamQuestionViewDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Course;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Exam;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.ExamQuestion;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;
import ir.maktabsharif.springbootonlineexamsystem.repository.ExamQuestionRepository;
import ir.maktabsharif.springbootonlineexamsystem.repository.ExamRepository;
import ir.maktabsharif.springbootonlineexamsystem.repository.UserCourseRoleRepository;
import ir.maktabsharif.springbootonlineexamsystem.service.CourseService;
import ir.maktabsharif.springbootonlineexamsystem.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final UserCourseRoleRepository userCourseRoleRepository;
    private final SecurityService securityService;
    private final CourseService courseService;
    private final ExamQuestionRepository examQuestionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Exam> getMyExamsForCourse(Long courseId) {
        User currentUser = securityService.getCurrentUser();

        //security check ( if evan open manually open url)
        boolean isTeacherOfCourse =
                userCourseRoleRepository.existsByUser_IdAndCourse_IdAndUserRole(
                        currentUser.getId(),
                        courseId,
                        USER_ROLE.TEACHER
                );
        if (!isTeacherOfCourse) {
            throw new IllegalStateException("you aren't teacher of this course");
        }
        return examRepository.findAllByCourse_IdAndCreatedBy_Id(
                courseId,
                currentUser.getId()
        );
    }

    @Override
    @Transactional
    public void createExam(Long courseId, ExamCreateDto examCreateDto) {
        User currentUser = securityService.getCurrentUser();
        boolean isTeacherOfCourse =
                userCourseRoleRepository.existsByUser_IdAndCourse_IdAndUserRole(
                        currentUser.getId(),
                        courseId,
                        USER_ROLE.TEACHER
                );

        if (!isTeacherOfCourse) {
            throw new IllegalStateException("you aren't teacher of this course");
        }

        Course course = courseService.findById(courseId);
        Exam exam = Exam.builder()
                .title(examCreateDto.getTitle())
                .description(examCreateDto.getDescription())
                .duration(examCreateDto.getDuration())
                .course(course)
                .createdBy(currentUser)
                .build();
        examRepository.save(exam);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamEditDto getExamForEdit(Long examId) {
        User currentUser = securityService.getCurrentUser();
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalStateException("exam not found"));
        if (!exam.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("you dont have permission to edit exam");
        }
        return ExamEditDto.builder()
                .title(exam.getTitle())
                .description(exam.getDescription())
                .duration(exam.getDuration())
                .build();
    }

    @Override
    public void updateExam(Long examId, ExamEditDto examEditDto) {
        User currentUser = securityService.getCurrentUser();
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalStateException("exam not found"));
        if (!exam.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("you dont have permission to edit exam");
        }
        exam.setTitle(examEditDto.getTitle());
        exam.setDescription(examEditDto.getDescription());
        exam.setDuration(examEditDto.getDuration());

        examRepository.save(exam);

    }

    @Override
    public Exam findById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("exam not found"));
    }

    @Override
    @Transactional
    public void deleteExam(Long examId) {
        User currentUser = securityService.getCurrentUser();
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new IllegalStateException("exam not found"));
        //if you create an exam can delete it.
        if (!exam.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new IllegalStateException("you dont have permission to edit exam");
        }

        examRepository.delete(exam);
    }

    @Transactional(readOnly = true)
    public double calculateTotalScore(Long examId) {

        List<ExamQuestion> examQuestions =
                examQuestionRepository.findAllByExam_Id(examId);

        return examQuestions.stream()
                .mapToDouble(ExamQuestion::getDefaultScore)
                .sum();
    }
}


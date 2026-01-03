package ir.maktabsharif.springbootonlineexamsystem.model.dto.question;

import ir.maktabsharif.springbootonlineexamsystem.model.enums.QUESTION_TYPE;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ExamQuestionViewDto {
    @NotBlank
    private String title;
    private QUESTION_TYPE questionType;
    private Double defaultScore;
}
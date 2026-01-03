package ir.maktabsharif.springbootonlineexamsystem.model.dto.bank;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.question.QuestionOptionDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MCQQuestionBankDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Valid
    @NotEmpty
    private List<QuestionOptionDto> options;
}

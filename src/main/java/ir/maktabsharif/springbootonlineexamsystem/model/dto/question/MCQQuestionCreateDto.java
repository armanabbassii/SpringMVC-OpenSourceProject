package ir.maktabsharif.springbootonlineexamsystem.model.dto.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MCQQuestionCreateDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Double defaultScore;

    @Size(min = 2)
    private List<QuestionOptionDto> options = new ArrayList<>();


}

package ir.maktabsharif.springbootonlineexamsystem.model.dto.question;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class QuestionOptionDto {
    @NotBlank
    private String text;

    private boolean correctAnswer;
}

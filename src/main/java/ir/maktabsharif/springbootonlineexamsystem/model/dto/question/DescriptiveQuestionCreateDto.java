package ir.maktabsharif.springbootonlineexamsystem.model.dto.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DescriptiveQuestionCreateDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Double defaultScore;

}

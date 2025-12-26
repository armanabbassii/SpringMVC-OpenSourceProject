package ir.maktabsharif.springbootonlineexamsystem.model.dto.exam;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamEditDto {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    @Min(value = 1)
    private Integer duration;
}

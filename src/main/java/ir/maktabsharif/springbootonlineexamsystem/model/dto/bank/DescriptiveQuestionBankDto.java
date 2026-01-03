package ir.maktabsharif.springbootonlineexamsystem.model.dto.bank;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DescriptiveQuestionBankDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}

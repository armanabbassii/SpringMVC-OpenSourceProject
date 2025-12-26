package ir.maktabsharif.springbootonlineexamsystem.model.dto.exam;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamDto {
    private Long id;
    private String title;
    private String description;
    private Integer duration;

}

package ir.maktabsharif.springbootonlineexamsystem.model.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {

    @NotBlank(message = "عنوان دوره الزامی است")
    private String title;

    @NotNull(message = "تاریخ شروع الزامی است")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDateCourse;

    @NotNull(message = "تاریخ پایان الزامی است")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDateCourse;
}
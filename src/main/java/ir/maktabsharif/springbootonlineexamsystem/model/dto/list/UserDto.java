package ir.maktabsharif.springbootonlineexamsystem.model.dto.list;

import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String familyName;

    private String phoneNumber;
    private String address;

    private USER_STATUS userStatus;

    private String dtype; // "STUDENT", "TEACHER", "User" ...

    // ===== Student fields =====
    private String major;

    // ===== Teacher fields =====
    private String specialization;
    private Double yearsOfExperience;
    private String employeeCode;
}


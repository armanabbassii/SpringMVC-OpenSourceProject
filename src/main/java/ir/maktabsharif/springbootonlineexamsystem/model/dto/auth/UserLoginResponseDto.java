package ir.maktabsharif.springbootonlineexamsystem.model.dto.auth;

import ir.maktabsharif.springbootonlineexamsystem.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserLoginResponseDto {
    private Long id;
    private String username;
    private UserType userType;
}

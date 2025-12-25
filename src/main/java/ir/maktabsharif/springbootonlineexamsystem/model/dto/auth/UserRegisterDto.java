package ir.maktabsharif.springbootonlineexamsystem.model.dto.auth;

import ir.maktabsharif.springbootonlineexamsystem.model.enums.REGISTER_TYPE;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDto {

    @NotBlank
    @Size(min = 5)
    private String username;

    @NotBlank
    @Size(min = 5,max = 12)
    private String password;

    @NotBlank
    private String firstName;
    @NotBlank
    private String familyName;

    @NotBlank
    private String phoneNumber;
    private String address;

    @NotNull
    private REGISTER_TYPE registerType;
}

package ir.maktabsharif.springbootonlineexamsystem.model.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private RegisterType registerType;
}

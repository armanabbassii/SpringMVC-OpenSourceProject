package ir.maktabsharif.springbootonlineexamsystem.model.dto;

import ir.maktabsharif.springbootonlineexamsystem.model.enums.Role;
import jakarta.persistence.Enumerated;

public class UserRegisterDto {
    private String username;
    private String password;
    @Enumerated
    private Role role;
}

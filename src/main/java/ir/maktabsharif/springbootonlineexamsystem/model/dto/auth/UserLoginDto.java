package ir.maktabsharif.springbootonlineexamsystem.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginDto {
    @NotNull
    private String username;

    @NotNull
    private String password;
}

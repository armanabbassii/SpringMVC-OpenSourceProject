package ir.maktabsharif.springbootonlineexamsystem.model.dto.list;

import ir.maktabsharif.springbootonlineexamsystem.model.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDto {
    private String firstName;
    private String familyName;
    private UserType userType;
}

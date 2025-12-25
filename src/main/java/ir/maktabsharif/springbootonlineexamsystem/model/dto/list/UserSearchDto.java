package ir.maktabsharif.springbootonlineexamsystem.model.dto.list;

import ir.maktabsharif.springbootonlineexamsystem.model.enums.REGISTER_TYPE;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDto {
    private String firstName;
    private String familyName;
    private REGISTER_TYPE registerType;
    private USER_STATUS userStatus;
}

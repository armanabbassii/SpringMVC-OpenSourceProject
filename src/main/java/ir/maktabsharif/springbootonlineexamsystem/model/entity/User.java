package ir.maktabsharif.springbootonlineexamsystem.model.entity;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.base.BaseEntity;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.REGISTER_TYPE;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.SYSTEM_ROLE;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Table(name = "users")
public class User extends BaseEntity<Long> {

    @Column(unique = true, nullable = false)
    @Size(min = 5)
    private String username;
    @Column(nullable = false)
    @Size(min = 5)
    private String password;
    private String firstName;
    private String familyName;
    @Size(max = 11)
    private String phoneNumber;
    private String address;


    //teacher fields
    private String specialization;
    private Double yearsOfExperience;
    private String employeeCode;

    //student fields
    private String major;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private USER_STATUS userStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private REGISTER_TYPE registerType;

    @Enumerated(EnumType.STRING)
    private SYSTEM_ROLE systemRole;

//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private USER_ROLE userRole;
    //UserCourseRole.userRole

    //role of user in system
//    @Column(nullable = false)
//    private boolean admin = false;


}

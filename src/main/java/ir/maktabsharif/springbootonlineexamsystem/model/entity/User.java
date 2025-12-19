package ir.maktabsharif.springbootonlineexamsystem.model.entity;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.base.BaseEntity;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private USER_STATUS userStatus;

    //    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private USER_ROLE userRole;
    @Column(nullable = false)
    private boolean admin = false;

    @Column(name = "dtype", insertable = false, updatable = false)
    private String dtype;
}

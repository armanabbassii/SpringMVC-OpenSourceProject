package ir.maktabsharif.springbootonlineexamsystem.model.entity;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.base.BaseEntity;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
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
    private Integer phoneNumber;
    private String address;
    //    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private USER_ROLE userRole;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private USER_STATUS userStatus;

    // RELATIONS
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Role> roles = new HashSet<>();
    //each user can have one role (List ❌)
}

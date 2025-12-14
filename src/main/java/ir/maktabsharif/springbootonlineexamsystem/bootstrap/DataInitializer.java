package ir.maktabsharif.springbootonlineexamsystem.bootstrap;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.Authority;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Role;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Authority userApprove = saveAuthority("USER_REGISTER_APPROVE");
        Authority userEdit = saveAuthority("USER_EDIT");
        Authority userView = saveAuthority("USER_VIEW");
        Authority userChangeRole = saveAuthority("USER_CHANGE_ROLE");

        Authority courseCreate = saveAuthority("COURSE_CREATE");
        Authority courseEdit = saveAuthority("COURSE_EDIT");
        Authority courseDelete = saveAuthority("COURSE_DELETE");
        Authority courseView = saveAuthority("COURSE_VIEW");
        Authority courseAssignTeacher = saveAuthority("COURSE_ASSIGN_TEACHER");
        Authority courseAddStudent = saveAuthority("COURSE_ADD_STUDENT");


        // ===== Roles =====
        Role adminRole = saveRole(
                "ROLE_ADMIN",
                Set.of(
                        userApprove, userEdit, userView, userChangeRole,
                        courseCreate, courseEdit, courseDelete,
                        courseView, courseAssignTeacher, courseAddStudent
                )
        );

        Role teacherRole = saveRole(
                "ROLE_TEACHER",
                Set.of(courseView)
        );

        Role studentRole = saveRole(
                "ROLE_STUDENT",
                Set.of(courseView)
        );

        // ===== Default Admin User =====
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .firstName("System")
                    .familyName("Admin")
                    .userStatus(USER_STATUS.APPROVED)
                    .build();

            admin.getRoles().add(adminRole);
            userRepository.save(admin);
    }
}

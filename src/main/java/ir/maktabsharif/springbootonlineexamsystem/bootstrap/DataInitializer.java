package ir.maktabsharif.springbootonlineexamsystem.bootstrap;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_ROLE;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import ir.maktabsharif.springbootonlineexamsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ===== Default Admin User =====
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .firstName("System")
                    .familyName("Admin")
                    .phoneNumber("00000000000")
                    .address("SYSTEM")
                    .userStatus(USER_STATUS.APPROVED)
                    .admin(true)
//                    .userRole(USER_ROLE.ADMIN)
                    .build();

            userRepository.save(admin);
        }
    }
}

package ir.maktabsharif.springbootonlineexamsystem.repository;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    List<User> findByUserStatus(USER_STATUS status);

    @Query("""
    SELECT u FROM User u
    WHERE (:firstName IS NULL OR u.firstName LIKE %:firstName%)
      AND (:familyName IS NULL OR u.familyName LIKE %:familyName%)
""")
    List<User> searchUsers(
            @Param("firstName") String firstName,
            @Param("familyName") String familyName
    );
}
//TYPE(u).name دقیقاً مقدار @DiscriminatorValue را می‌گیرد.
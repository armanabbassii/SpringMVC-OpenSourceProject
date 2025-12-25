package ir.maktabsharif.springbootonlineexamsystem.repository;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.REGISTER_TYPE;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Query("""
            SELECT u FROM User u
            WHERE (:firstName IS NULL OR u.firstName LIKE %:firstName%)
              AND (:familyName IS NULL OR u.familyName LIKE %:familyName%)
              AND (:registerType IS NULL OR u.registerType = :registerType)
              AND (:userStatus IS NULL OR u.userStatus = :userStatus)
            """)
    List<User> searchUsers(
            @Param("firstName") String firstName,
            @Param("familyName") String familyName,
            @Param("registerType") REGISTER_TYPE registerType,
            @Param("userStatus") USER_STATUS userStatus
    );

    List<User> findByUserStatus(USER_STATUS userStatus);

    List<User> findByUserStatusAndRegisterType(USER_STATUS userStatus, REGISTER_TYPE registerType);
}
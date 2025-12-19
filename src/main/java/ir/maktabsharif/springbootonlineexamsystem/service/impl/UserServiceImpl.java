package ir.maktabsharif.springbootonlineexamsystem.service.impl;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.list.UserDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.list.UserSearchDto;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.UserType;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.auth.UserRegisterDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Student;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.Teacher;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import ir.maktabsharif.springbootonlineexamsystem.repository.UserRepository;
import ir.maktabsharif.springbootonlineexamsystem.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserRegisterDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("username already exists");
        }

        if (dto.getUsername() == null || dto.getPassword() == null) {
            throw new IllegalArgumentException("required fields are missing");
        }

        User user;
        if (dto.getUserType() == UserType.STUDENT) {
            user = Student.builder()
                    // ===== fields from User =====
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .firstName(dto.getFirstName())
                    .familyName(dto.getFamilyName())
                    .phoneNumber(dto.getPhoneNumber())
                    .address(dto.getAddress())
                    .userStatus(USER_STATUS.PENDING)
//                    .userRole(USER_ROLE.USER)
                    .build();
        } else {
            user = Teacher.builder()
                    // ===== fields from User =====
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .firstName(dto.getFirstName())
                    .familyName(dto.getFamilyName())
                    .phoneNumber(dto.getPhoneNumber())
                    .address(dto.getAddress())
                    .userStatus(USER_STATUS.PENDING)
//                    .userRole(USER_ROLE.USER)
                    .build();
        }

        userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
//lazy loading need to session + valid in transaction
    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));
    }

    @Override
    public void updateUserStatus(Long userId, USER_STATUS status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        user.setUserStatus(status);
        userRepository.save(user);
    }
//get data for update
    @Transactional(readOnly = true)
    @Override
    public UserDto getUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        UserDto.UserDtoBuilder dtoBuilder = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .familyName(user.getFamilyName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .userStatus(user.getUserStatus())
                .dtype(user.getDtype());

        if ("STUDENT".equalsIgnoreCase(user.getDtype()) && user instanceof Student student) {
            dtoBuilder.major(student.getMajor());
        } else if ("TEACHER".equalsIgnoreCase(user.getDtype()) && user instanceof Teacher teacher) {
            dtoBuilder
                    .specialization(teacher.getSpecialization())
                    .yearsOfExperience(teacher.getYearsOfExperience())
                    .employeeCode(teacher.getEmployeeCode());
        }
        return dtoBuilder.build();
    }


    @Transactional
    @Override
    public void updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        user.setFirstName(userDto.getFirstName());
        user.setFamilyName(userDto.getFamilyName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAddress(userDto.getAddress());
        user.setUserStatus(userDto.getUserStatus());

        if ("STUDENT".equalsIgnoreCase(user.getDtype()) && user instanceof Student student) {
            student.setMajor(userDto.getMajor());
            userRepository.save(student);
            return;
        }

        if ("TEACHER".equalsIgnoreCase(user.getDtype()) && user instanceof Teacher teacher) {
            teacher.setSpecialization(userDto.getSpecialization());
            teacher.setEmployeeCode(userDto.getEmployeeCode());
            teacher.setYearsOfExperience(userDto.getYearsOfExperience());
            userRepository.save(teacher);
            return;
        }
        userRepository.save(user);

    }


    @Transactional
    public void changeUserType(Long id, String targetType) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getDtype().equalsIgnoreCase(targetType)) {
            return;
        }

        String username = user.getUsername();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String familyName = user.getFamilyName();
        String phone = user.getPhoneNumber();
        String address = user.getAddress();
        USER_STATUS status = user.getUserStatus();

        userRepository.delete(user);
        userRepository.flush(); //delete now from database

        if ("STUDENT".equalsIgnoreCase(targetType)) {
            Student student = Student.builder()
                    .username(username)
                    .password(password)
                    .firstName(firstName)
                    .familyName(familyName)
                    .phoneNumber(phone)
                    .address(address)
                    .userStatus(status)
                    .major(null)
                    .build();

            userRepository.save(student);
        }

        if ("TEACHER".equalsIgnoreCase(targetType)) {
            Teacher teacher = Teacher.builder()
                    .username(username)
                    .password(password)
                    .firstName(firstName)
                    .familyName(familyName)
                    .phoneNumber(phone)
                    .address(address)
                    .userStatus(status)
                    .specialization(null)
                    .employeeCode(null)
                    .yearsOfExperience(null)
                    .build();

            userRepository.save(teacher);
        }
    }


    @Override
    public List<User> userSearch(UserSearchDto dto) {

        List<User> users = userRepository.searchUsers(
                emptyToNull(dto.getFirstName()),//ignore, if it firstname == null
                emptyToNull(dto.getFamilyName())
        );

        if (dto.getUserType() == null || dto.getUserType() == UserType.ALL) {
            return users;
        }

        return users.stream()
                .filter(u ->
                        (dto.getUserType() == UserType.STUDENT && u instanceof Student)
                                || (dto.getUserType() == UserType.TEACHER && u instanceof Teacher)
                )
                .toList();
    }
    private String emptyToNull(String value) {
        return (value == null || value.isBlank()) ? null : value;
        // ""," ", "\n"
    }

}
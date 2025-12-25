package ir.maktabsharif.springbootonlineexamsystem.service.impl;

import ir.maktabsharif.springbootonlineexamsystem.model.dto.list.UserDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.list.UserSearchDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.auth.UserRegisterDto;

import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.REGISTER_TYPE;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;
import ir.maktabsharif.springbootonlineexamsystem.repository.UserRepository;
import ir.maktabsharif.springbootonlineexamsystem.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserRegisterDto dto) {
        if (dto.getUsername() == null || dto.getPassword() == null) {
            throw new IllegalArgumentException("required fields are missing");
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("username already exists");
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .firstName(dto.getFirstName())
                .familyName(dto.getFamilyName())
                .phoneNumber(dto.getPhoneNumber())
                .address(dto.getAddress())
                .registerType(dto.getRegisterType())
                .userStatus(USER_STATUS.PENDING)
                .build();
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
    @Transactional(readOnly = true)
    public void updateUserStatus(Long userId, USER_STATUS status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        user.setUserStatus(status);
        userRepository.save(user);
    }

    //get data for update
    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        UserDto.UserDtoBuilder dtoBuilder = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .familyName(user.getFamilyName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .userStatus(user.getUserStatus());

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

        userRepository.save(user);

    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findApprovedUsers() {
        return userRepository.findByUserStatus(USER_STATUS.APPROVED);
    }

    @Override
    public List<User> findApprovedStudents() {
        return userRepository.findByUserStatusAndRegisterType(USER_STATUS.APPROVED, REGISTER_TYPE.STUDENT);
    }

    @Override
    public List<User> findApprovedTeachers() {
        return userRepository.findByUserStatusAndRegisterType(USER_STATUS.APPROVED, REGISTER_TYPE.TEACHER);
    }

    @Override
    public List<User> userSearch(UserSearchDto dto) {

        return userRepository.searchUsers(
                emptyToNull(dto.getFirstName()),//ignore, if it firstname == null
                emptyToNull(dto.getFamilyName()),
                dto.getRegisterType(),
                dto.getUserStatus()
        );
    }

    private String emptyToNull(String value) {
        return (value == null || value.isBlank()) ? null : value;
        // ""," ", "\n"
    }

}
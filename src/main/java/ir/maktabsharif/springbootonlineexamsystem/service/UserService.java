package ir.maktabsharif.springbootonlineexamsystem.service;


import ir.maktabsharif.springbootonlineexamsystem.model.dto.auth.UserRegisterDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.list.UserDto;
import ir.maktabsharif.springbootonlineexamsystem.model.dto.list.UserSearchDto;
import ir.maktabsharif.springbootonlineexamsystem.model.entity.User;
import ir.maktabsharif.springbootonlineexamsystem.model.enums.USER_STATUS;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void register(UserRegisterDto userRegisterDto);

    List<User> findAllUsers();

    void updateUserStatus(Long userId, USER_STATUS status);

    UserDto getUserById(Long id);

    void updateUser(Long id, UserDto userDto);
    User findById(Long id);

    List<User> userSearch(UserSearchDto userSearchDto);

    List<User> findApprovedUsers();
    List<User> findApprovedStudents();
    List<User> findApprovedTeachers();



}

package com.kittens.server.user.service;

import com.kittens.server.dto.UserNicknameDto;
import com.kittens.server.user.entity.User;
import com.kittens.server.security.dto.RegistrationUserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Boolean registerUser(RegistrationUserDto dto);

    Optional<User> getByLogin(String login);

    Boolean existsByLogin(String login);

    User addDynamicPassword(User user, List<Integer> dynamicPasswords);

    Optional<UserNicknameDto> findById(Long id);
}

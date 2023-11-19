package com.kittens.server.user.service;

import com.kittens.server.user.entity.User;
import com.kittens.server.security.dto.RegistrationUserDto;
import com.kittens.server.user.entity.UserDynamicPassword;
import com.kittens.server.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public Boolean registerUser(RegistrationUserDto dto) {
        User user = User.builder()
                .login(dto.getLogin())
                .passwordHash(MD5Encoder.encode(dto.getPassword().getBytes()))
                .dynamicPasswords(new ArrayList<>())
                .build();
        this.addDynamicPasswordWithoutSave(user, dto.getPasswordEnterValueTime());
        this.addDynamicPasswordWithoutSave(user, dto.getPasswordConfirmationEnterValueTime());
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public User addDynamicPassword(User user, List<Integer> dynamicPasswords) {
        this.addDynamicPasswordWithoutSave(user, dynamicPasswords);
        return userRepository.save(user);
    }

    private void addDynamicPasswordWithoutSave(User user, List<Integer> dynamicPasswords) {
        user.getDynamicPasswords().add(UserDynamicPassword.builder()
                .enterDurations(dynamicPasswords)
                .build());
    }
}

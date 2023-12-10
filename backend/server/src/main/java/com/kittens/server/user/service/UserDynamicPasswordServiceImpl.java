package com.kittens.server.user.service;

import com.kittens.server.user.entity.UserDynamicPassword;
import com.kittens.server.user.repository.UserDynamicPasswordRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.query.spi.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserDynamicPasswordServiceImpl implements UserDynamicPasswordService {
    UserDynamicPasswordRepository userDynamicPasswordRepository;

    @Override
    public List<Integer[]> findTopByUserLogin(Integer topAmount, String login) {
        return userDynamicPasswordRepository.findByUserLoginOrderByCreatedDateDesc(login, PageRequest.of(0, topAmount))
                .stream()
                .map(UserDynamicPassword::getEnterDurations)
                .collect(Collectors.toList());
    }
}

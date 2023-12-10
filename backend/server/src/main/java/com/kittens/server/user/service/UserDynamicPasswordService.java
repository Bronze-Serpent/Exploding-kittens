package com.kittens.server.user.service;

import java.util.List;

public interface UserDynamicPasswordService {
    List<Integer[]> findTopByUserLogin(Integer topAmount, String login);
}

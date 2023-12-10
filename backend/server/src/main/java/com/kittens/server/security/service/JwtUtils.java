package com.kittens.server.security.service;

import com.kittens.server.security.JwtAuthentication;
import io.jsonwebtoken.Claims;

public class JwtUtils {

    public static JwtAuthentication generate(Claims claims){
        String login = (String) claims.get("login");
        JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setLogin(login);
        return jwtAuthentication;
    }
}

package com.example.demo.login.dto.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KakaoUserLoginRequest {
    private String email;
    private String password;
}

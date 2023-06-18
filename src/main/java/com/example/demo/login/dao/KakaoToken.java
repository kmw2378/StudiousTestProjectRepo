package com.example.demo.login.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoToken {
    @Id
    @GeneratedValue
    private Long id;

    private String tokenType;
    private String  accessToken;
    private Long expiresIn;
    private String refreshToken;
    private Long refreshTokenExpiresIn;
}

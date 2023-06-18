package com.example.demo.login.repository;

import com.example.demo.login.dao.KakaoToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoTokenRepository extends JpaRepository<KakaoToken, Long> {
}

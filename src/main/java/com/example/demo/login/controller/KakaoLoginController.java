package com.example.demo.login.controller;

import com.example.demo.login.dto.KakaoLoginConst;
import com.example.demo.login.dto.KakaoTokenResponse;
import com.example.demo.login.dto.code.KakaoUserLoginResponse;
import com.example.demo.login.dto.token.kakao.KakaoUserTokenRequest;
import com.example.demo.login.dto.token.kakao.KakaoUserTokenResponse;
import com.example.demo.login.service.KakaoLoginService;
import com.example.demo.login.util.converter.MultiValueMapConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/login")
    public KakaoUserLoginResponse login() {
        return WebClient.create()
                .method(HttpMethod.GET)
                .uri(UriBuilder -> UriBuilder.
                        path(KakaoLoginConst.LOGIN_URI)
                        .queryParam("client_id", KakaoLoginConst.REST_API)
                        .queryParam("redirect_uri", KakaoLoginConst.REDIRECT_URI)
                        .build()
                )
                .retrieve()
                .bodyToMono(KakaoUserLoginResponse.class)
                .block();
    }

    /**
     * 프론트로부터 받은 인가코드를 통해 토큰을 발급받는 메소드
     * @param code 프론트엔드로 부터 전달받은 인가코드
     * @return KakaoUserTokenResponse 객체
     */
    @GetMapping("/get-token")
    public KakaoTokenResponse getKakaoToken(@RequestParam String code) {
        log.info("code = {}", code);
        return kakaoLoginService.login(code);
    }
}

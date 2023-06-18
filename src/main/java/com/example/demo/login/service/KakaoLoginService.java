package com.example.demo.login.service;

import com.example.demo.login.dao.KakaoToken;
import com.example.demo.login.dto.KakaoLoginConst;
import com.example.demo.login.dto.KakaoTokenRequest;
import com.example.demo.login.dto.KakaoTokenResponse;
import com.example.demo.login.repository.KakaoTokenRepository;
import com.example.demo.login.util.converter.MultiValueMapConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {
    private final KakaoTokenRepository kakaoTokenRepository;

    /**
     * 프론트로부터 받은 인가코드를 통해 토큰을 발급받는 메소드
     * 카카오 로그인 서버에 인가코드, 앱 REST API 키, 리다이렉트 URI를 통해 요청하여 토큰값을 받아온다.
     * 추후, KakaoLoginService 에 추가될 예정
     * @param code 프론트엔드로 부터 전달받은 인가코드
     * @return KakaoTokenResponse 객체
     */
    public KakaoTokenResponse login(String code) {
        MultiValueMap<String, String> params = MultiValueMapConverter.convert(
                new ObjectMapper(),
                KakaoTokenRequest.builder()
                        .grant_type(KakaoLoginConst.GRANT_TYPE)
                        .client_id(KakaoLoginConst.REST_API)
                        .redirect_uri(KakaoLoginConst.REDIRECT_URI)
                        .code(code)
                        .build()
        );

        KakaoTokenResponse kakaoTokenResponse = null;
        try {
            kakaoTokenResponse = WebClient.create(KakaoLoginConst.TOKEN_URI)
                    .method(HttpMethod.POST)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .body(BodyInserters.fromFormData(params))
                    .retrieve()
                    .bodyToMono(KakaoTokenResponse.class)
                    .block();

            kakaoTokenRepository.save(
                    KakaoToken.builder()
                            .accessToken(kakaoTokenResponse.getAccess_token())
                            .expiresIn(kakaoTokenResponse.getExpires_in())
                            .refreshToken(kakaoTokenResponse.getRefresh_token())
                            .refreshTokenExpiresIn(kakaoTokenResponse.getRefresh_token_expires_in())
                            .build()
            );

        } catch (WebClientResponseException e) {
            log.error("msg = {}", e.getMessage());
            log.error("status = {}", e.getStatusText());
            log.error("body = {}", e.getResponseBodyAsString());
        }

        return kakaoTokenResponse;
    }
}
// https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}

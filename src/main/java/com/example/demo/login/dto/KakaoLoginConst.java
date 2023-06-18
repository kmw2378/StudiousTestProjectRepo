package com.example.demo.login.dto;

public class KakaoLoginConst {
    public final static String LOGIN_URI = "https://kauth.kakao.com/oauth/authorize";
    public final static String GRANT_TYPE = "authorization_code";
    public final static String REDIRECT_URI = "http://localhost:3000/oauth/kakao";
    public final static String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    public final static String REST_API = "840802008a58093510f5294d2e7c67c9";
    public final static String LOGOUT_URI = "https://kapi.kakao.com/v1/user/logout";
}

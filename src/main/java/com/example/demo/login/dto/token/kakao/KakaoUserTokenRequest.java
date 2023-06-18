package com.example.demo.login.dto.token.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KakaoUserTokenRequest {
    private String grant_type;
    private String client_id;
    private String redirect_uri;
    private String code;
}

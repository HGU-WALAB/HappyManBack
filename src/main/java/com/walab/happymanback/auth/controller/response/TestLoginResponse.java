package com.walab.happymanback.auth.controller.response;

import com.walab.happymanback.auth.dto.AuthDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "테스트용 로그인 응답")
public class TestLoginResponse {
  @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjI5MzIwNzIyLCJleHAiOjE2MjkzMjA3MjJ9.3zv3z")
  private String token;

  public static TestLoginResponse from(AuthDto dto) {
    return TestLoginResponse.builder().token(dto.getToken()).build();
  }
}

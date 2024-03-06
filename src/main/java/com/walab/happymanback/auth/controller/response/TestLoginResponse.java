package com.walab.happymanback.auth.controller.response;

import com.walab.happymanback.auth.dto.AuthDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestLoginResponse {
  private String token;

  public static TestLoginResponse from(AuthDto dto) {
    return TestLoginResponse.builder().token(dto.getToken()).build();
  }
}

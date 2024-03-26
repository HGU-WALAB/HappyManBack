package com.walab.happymanback.auth.controller.response;

import com.walab.happymanback.auth.dto.AuthDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
  private String token;

  public static LoginResponse from(AuthDto authDto) {
    return LoginResponse.builder().token(authDto.getToken()).build();
  }
}

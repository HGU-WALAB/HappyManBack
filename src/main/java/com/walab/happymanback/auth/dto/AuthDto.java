package com.walab.happymanback.auth.dto;

import com.walab.happymanback.auth.controller.request.TestLoginRequest;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AuthDto {
  private String uniqueId;
  private String token;

  public static AuthDto from(TestLoginRequest request) {
    return AuthDto.builder().uniqueId(request.getUniqueId()).build();
  }
}

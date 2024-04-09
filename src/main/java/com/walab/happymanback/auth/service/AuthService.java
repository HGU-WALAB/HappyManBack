package com.walab.happymanback.auth.service;

import com.walab.happymanback.auth.dto.AuthDto;
import com.walab.happymanback.auth.util.JwtUtil;
import com.walab.happymanback.base.exception.DoNotExistException;
import com.walab.happymanback.user.entity.User;
import com.walab.happymanback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  @Value("${custom.jwt.secret}")
  private String SECRET_KEY;

  public User getLoginUser(String uniqueId) {
    return userRepository
        .findById(uniqueId)
        .orElseThrow(() -> new DoNotExistException("해당 유저가 없습니다."));
  }

  public AuthDto login(AuthDto dto) {
    Optional<User> user = userRepository.findById(dto.getUniqueId());
    if (user.isEmpty()) {
      User newUser=User.from(dto);
      userRepository.save(User.from(dto));
        return AuthDto.builder()
                .token(JwtUtil.createToken(newUser.getUniqueId(), newUser.getStatus().name(), newUser.getName() , SECRET_KEY))
                .build();
    }else {
      user.get().update(dto);
      return AuthDto.builder()
              .token(
                      JwtUtil.createToken(
                              user.get().getUniqueId(), user.get().getStatus().name(), user.get().getName() , SECRET_KEY))
              .build();
    }
  }
}

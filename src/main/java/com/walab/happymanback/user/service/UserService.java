package com.walab.happymanback.user.service;

import com.walab.happymanback.user.dto.UserDto;
import com.walab.happymanback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserDto getUser(String uniqueId) {
    return UserDto.from(
        userRepository
            .findById(uniqueId)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다.")));
  }
}

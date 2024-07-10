package com.walab.happymanback.user.service;

import com.walab.happymanback.user.dto.UserDto;
import com.walab.happymanback.user.entity.User;
import com.walab.happymanback.user.entity.enums.UserStatus;
import com.walab.happymanback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

  public List<UserDto> getUsers() {
    return userRepository.findByStatus(UserStatus.USER).stream()
            .map(UserDto::from)
            .collect(Collectors.toList());
  }

  public List<UserDto> getAdmins() {
    return userRepository.findByStatus(UserStatus.ADMIN).stream()
        .map(UserDto::from)
        .collect(Collectors.toList());
  }

  public void changeStatus(List<String> uniqueIds, String status) {
    UserStatus userStatus = UserStatus.valueOf(status);
    uniqueIds.forEach(uniqueId -> {
      User user = userRepository.findById(uniqueId)
          .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
      user.setStatus(userStatus);
    });
  }
}

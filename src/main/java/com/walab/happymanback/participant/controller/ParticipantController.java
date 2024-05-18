package com.walab.happymanback.participant.controller;

import com.walab.happymanback.participant.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipantController {
  private final ParticipantService participantService;

  @PatchMapping("/api/happyman/admin/participants/change-status")
  public ResponseEntity<Void> changeParticipantStatus(
      @RequestParam List<Long> ids, @RequestParam String status) {
    participantService.changeParticipantStatus(ids, status);
    return ResponseEntity.ok().build();
  }
}

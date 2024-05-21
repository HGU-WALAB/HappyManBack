package com.walab.happymanback.participant.controller;

import com.walab.happymanback.participant.controller.response.MyParticipationListResponse;
import com.walab.happymanback.participant.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/api/happyman/my-participation")
  public ResponseEntity<MyParticipationListResponse> getMyParticipationList(
      @AuthenticationPrincipal String uniqueId) {
    return ResponseEntity.ok(
            MyParticipationListResponse.from(participantService.getParticipantsFrom(uniqueId)));
  }

  @DeleteMapping("/api/happyman/participants/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id, @AuthenticationPrincipal String uniqueId) {
        participantService.deleteParticipant(id, uniqueId);
        return ResponseEntity.ok().build();
    }
}

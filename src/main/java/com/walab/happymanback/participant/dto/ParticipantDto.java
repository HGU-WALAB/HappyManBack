package com.walab.happymanback.participant.dto;

import com.walab.happymanback.participant.entity.Participant;
import com.walab.happymanback.program.controller.request.ProgramApplyRequest;
import com.walab.happymanback.program.dto.ProgramDto;
import com.walab.happymanback.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantDto {
  private Long id;
  private UserDto user;
  private ProgramDto program;
  private String applicationForm;
  private String status;
  private Integer semester;

  public static ParticipantDto from(ProgramApplyRequest request, String uniqueId, Long programId) {
    return ParticipantDto.builder()
        .user(UserDto.builder().uniqueId(uniqueId).build())
        .program(ProgramDto.builder().id(programId).build())
        .applicationForm(request.getApplicationForm())
        .build();
  }

  public static ParticipantDto from(Participant participant) {
    return ParticipantDto.builder()
        .id(participant.getId())
        .user(UserDto.from(participant.getUser()))
        .program(ProgramDto.from(participant.getProgram()))
        .applicationForm(participant.getApplicationForm())
        .status(participant.getStatus().getKorean())
        .semester(participant.getSemester())
        .build();
  }

  public static ParticipantDto fromWithoutUser(Participant participant) {
    return ParticipantDto.builder()
        .id(participant.getId())
        .program(ProgramDto.from(participant.getProgram()))
        .applicationForm(participant.getApplicationForm())
        .status(participant.getStatus().getKorean())
        .semester(participant.getSemester())
        .build();
  }
}

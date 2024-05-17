package com.walab.happymanback.program.controller.response;

import com.walab.happymanback.participant.dto.ParticipantDto;
import com.walab.happymanback.program.dto.ProgramDto;
import lombok.Builder;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class ApplicantListResponse {
  private String applicationForm;

  private List<Applicant> applicants;

  public static ApplicantListResponse from(ProgramDto dto) {
    return ApplicantListResponse.builder()
        .applicationForm(dto.getApplicationForm())
        .applicants(
            dto.getParticipants().stream().map(Applicant::from).collect(Collectors.toList()))
        .build();
  }

  @Builder
  @Getter
  private static class Applicant {
    private String uniqueId;

    private String name;

    private String email;

    private Integer semester;

    private String major1;

    private String applicationForm;

    public static Applicant from(ParticipantDto dto) {
      return Applicant.builder()
          .uniqueId(dto.getUser().getUniqueId())
          .name(dto.getUser().getName())
          .email(dto.getUser().getEmail())
          .semester(dto.getUser().getSemester())
          .major1(dto.getUser().getMajor1())
          .applicationForm(dto.getApplicationForm())
          .build();
    }
  }
}

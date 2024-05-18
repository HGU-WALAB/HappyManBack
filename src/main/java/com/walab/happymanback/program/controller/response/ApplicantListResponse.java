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
  private Integer currentQuota;
  private Integer quota;

  private List<Applicant> applicants;

  public static ApplicantListResponse from(ProgramDto dto) {
    return ApplicantListResponse.builder()
        .applicationForm(dto.getApplicationForm())
        .applicants(
            dto.getParticipants().stream()
                .filter(
                    participant ->
                        "대기".equals(participant.getStatus())
                            || "거절".equals(participant.getStatus()))
                .map(Applicant::from)
                .collect(Collectors.toList()))
        .currentQuota(dto.getCurrentQuota())
        .quota(dto.getQuota())
        .build();
  }

  @Builder
  @Getter
  private static class Applicant {
    private Long id;

    private String uniqueId;

    private String name;

    private String email;

    private Integer semester;

    private String major1;

    private String applicationForm;

    private String status;

    public static Applicant from(ParticipantDto dto) {
      return Applicant.builder()
          .id(dto.getId())
          .uniqueId(dto.getUser().getUniqueId())
          .name(dto.getUser().getName())
          .email(dto.getUser().getEmail())
          .semester(dto.getUser().getSemester())
          .major1(dto.getUser().getMajor1())
          .applicationForm(dto.getApplicationForm())
          .status(dto.getStatus())
          .build();
    }
  }
}

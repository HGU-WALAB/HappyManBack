package com.walab.happymanback.participant.controller.response;

import com.walab.happymanback.participant.dto.ParticipantDto;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder
public class MyParticipationListResponse {
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private List<Participation> participations;

  public static MyParticipationListResponse from(List<ParticipantDto> dtos) {
    return MyParticipationListResponse.builder()
        .participations(
            dtos.stream().map(Participation::from).collect(java.util.stream.Collectors.toList()))
        .build();
  }

  @Getter
  @Builder
  private static class Participation {
    private Long id;

    private String programName;

    private String status;

    private String startDate;

    private String endDate;

    public static Participation from(ParticipantDto dto) {
      return Participation.builder()
          .id(dto.getId())
          .programName(dto.getProgram().getName())
          .status(dto.getStatus())
          .startDate(dto.getProgram().getStartDate().format(DATE_TIME_FORMATTER))
          .endDate(dto.getProgram().getEndDate().format(DATE_TIME_FORMATTER))
          .build();
    }
  }
}

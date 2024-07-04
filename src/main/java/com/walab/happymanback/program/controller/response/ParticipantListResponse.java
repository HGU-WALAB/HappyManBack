package com.walab.happymanback.program.controller.response;

import com.walab.happymanback.participant.dto.ParticipantDto;
import com.walab.happymanback.program.dto.ProgramDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ParticipantListResponse {
    private String applicationForm;
    private Integer currentQuota;
    private Integer quota;

    private List<Participant> participants;

    public static ParticipantListResponse from(ProgramDto dto) {
        return ParticipantListResponse.builder()
                .applicationForm(dto.getApplicationForm())
                .participants(
                        dto.getParticipants().stream()
                                .filter(
                                        participant ->
                                                "미수료".equals(participant.getStatus())
                                                        || "수료".equals(participant.getStatus()))
                                .map(Participant::from)
                                .collect(Collectors.toList()))
                .currentQuota(dto.getCurrentQuota())
                .quota(dto.getQuota())
                .build();
    }

    @Builder
    @Getter
    private static class Participant {
        private Long id;

        private String uniqueId;

        private String name;

        private String email;

        private Integer semester;

        private String major1;

        private String applicationForm;

        private String status;

        public static Participant from(ParticipantDto dto) {
            return Participant.builder()
                    .id(dto.getId())
                    .uniqueId(dto.getUser().getUniqueId())
                    .name(dto.getUser().getName())
                    .email(dto.getUser().getEmail())
                    .semester(dto.getSemester())
                    .major1(dto.getUser().getMajor1())
                    .applicationForm(dto.getApplicationForm())
                    .status(dto.getStatus())
                    .build();
        }
    }
}

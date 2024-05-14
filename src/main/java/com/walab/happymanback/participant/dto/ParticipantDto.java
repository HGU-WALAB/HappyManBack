package com.walab.happymanback.participant.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantDto {
    private Long id;
    private Long programId;
    private String userId;
    private String applicationForm;
}

package com.walab.happymanback.participant.service;

import com.walab.happymanback.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepository participantRepository;

    public Boolean isParticipant(Long programId, String userId) {
        return participantRepository.existsByProgramIdAndUserId(programId, userId);
    }
}

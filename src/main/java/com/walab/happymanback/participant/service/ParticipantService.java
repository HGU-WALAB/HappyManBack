package com.walab.happymanback.participant.service;

import com.walab.happymanback.base.exception.DoNotExistException;
import com.walab.happymanback.participant.dto.ParticipantDto;
import com.walab.happymanback.participant.entity.Participant;
import com.walab.happymanback.participant.exception.AlreadyAppliedException;
import com.walab.happymanback.participant.repository.ParticipantRepository;
import com.walab.happymanback.program.entity.Program;
import com.walab.happymanback.program.repository.ProgramRepository;
import com.walab.happymanback.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantService {
  private final ParticipantRepository participantRepository;
  private final ProgramRepository programRepository;
  private final UserRepository userRepository;

  public Boolean isParticipant(Long programId, String userId) {
    return participantRepository.existsByProgramIdAndUserId(programId, userId);
  }

  public void applyProgram(ParticipantDto dto) {
    Program program =
        programRepository
            .findById(dto.getProgram().getId())
            .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다."));

    program.validateApplyDate();
    program.validateCurrentQuota();
    program.addCurrentQuota(1);

    participantRepository.save(
        Participant.apply(
            dto,
            program,
            userRepository
                .findById(dto.getUser().getUniqueId())
                .orElseThrow(() -> new DoNotExistException("해당 유저가 없습니다."))));

    if (isParticipant(dto.getProgram().getId(), dto.getUser().getUniqueId())) {
      throw new AlreadyAppliedException();
    }
  }
}

package com.walab.happymanback.participant.service;

import com.walab.happymanback.base.exception.DoNotExistException;
import com.walab.happymanback.base.exception.UnauthorizedDeletionException;
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

import java.util.List;
import java.util.stream.Collectors;

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
    if (isParticipant(dto.getProgram().getId(), dto.getUser().getUniqueId())) {
      throw new AlreadyAppliedException();
    }
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
  }

  public void changeParticipantStatus(List<Long> ids, String status) {
    for (Long id : ids) {
      Participant participant =
          participantRepository
              .findById(id)
              .orElseThrow(() -> new DoNotExistException("해당하는 참가자가 없습니다."));
      participant.changeStatus(status);
    }
  }

  public List<ParticipantDto> getParticipantsFrom(String uniqueId) {
    return participantRepository.findAllByUniqueId(uniqueId).stream()
        .map(ParticipantDto::fromWithoutUser)
        .collect(Collectors.toList());
  }

  public void deleteParticipant(Long id, String uniqueId) {
    Participant participant =
        participantRepository
            .findById(id)
            .orElseThrow(() -> new DoNotExistException("해당하는 참가자가 없습니다."));
    if (!participant.getUser().getUniqueId().equals(uniqueId)) {
      throw new UnauthorizedDeletionException("본인만 삭제할 수 있습니다.");
    }
    Program program =
        programRepository
            .findById(participant.getProgram().getId())
            .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다."));
    program.addCurrentQuota(-1);
    participantRepository.delete(participant);
  }

    public List<ParticipantDto> getComepletedPrograms(String uniqueId) {
        return participantRepository.findAllByUniqueId(uniqueId).stream()
                .filter(Participant::isCompleted)
                .map(ParticipantDto::withCategory)
                .collect(Collectors.toList());
    }

  public List<ParticipantDto> getParticipants(String uniqueId) {
    return participantRepository.findAllByUniqueId(uniqueId).stream()
        .map(ParticipantDto::from)
        .collect(Collectors.toList());
  }
}

package com.walab.happymanback.program.service;

import com.walab.happymanback.base.exception.DoNotExistException;
import com.walab.happymanback.category.repository.CategoryRepository;
import com.walab.happymanback.program.dto.ProgramDto;
import com.walab.happymanback.program.entity.Program;
import com.walab.happymanback.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgramService {
  private final CategoryRepository categoryRepository;
  private final ProgramRepository programRepository;

  public void createProgram(ProgramDto dto) {
    programRepository.save(
        Program.from(
            dto,
            categoryRepository
                .findById(dto.getCategoryDto().getId())
                .orElseThrow(() -> new DoNotExistException("해당 카테고리가 없습니다."))));
  }

  public List<ProgramDto> getProgramsWithCategory() {
    return programRepository.findAllWithCategory().stream()
        .map(program -> ProgramDto.from(program, program.getCategory()))
        .collect(Collectors.toList());
  }

  public ProgramDto getProgramWithFile(Long id) {
    Program program =
        programRepository
            .findByIdWithProgramFile(id)
            .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다."));
    return ProgramDto.withFile(program);
  }

  public void updateProgram(Long id, ProgramDto dto) {
    Program program =
        programRepository.findById(id).orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다."));
    program.update(dto);
  }

  public ProgramDto getProgram(Long id) {
    return ProgramDto.from(
        programRepository
            .findById(id)
            .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다.")));
  }

  public ProgramDto getProgramWithCategory(Long id) {
      Program program = programRepository.findByIdWithCategory(id)
              .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다."));
    return ProgramDto.from(program, program.getCategory());
  }

  public ProgramDto getProgramWithParticipant(Long id) {
    Program program =
        programRepository
            .findByIdWithParticipant(id)
            .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다."));
    return ProgramDto.withParticipant(program);
  }
}

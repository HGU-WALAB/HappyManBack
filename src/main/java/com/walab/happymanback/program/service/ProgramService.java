package com.walab.happymanback.program.service;

import com.walab.happymanback.base.exception.DoNotExistException;
import com.walab.happymanback.category.dto.CategoryDto;
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

    public List<ProgramDto> getPrograms() {
        return programRepository.findAllWithCategory().stream().map(
                program -> ProgramDto.builder()
                        .categoryDto(CategoryDto.from(program.getCategory()))
                        .id(program.getId())
                        .categoryDto(CategoryDto.from(program.getCategory()))
                        .name(program.getName())
                        .image(program.getImage())
                        .startDate(program.getStartDate())
                        .endDate(program.getEndDate())
                        .quota(program.getQuota())
                        .currentQuota(program.getCurrentQuota())
                        .managerName(program.getManagerName())
                        .applyStartDate(program.getApplyStartDate())
                        .applyEndDate(program.getApplyEndDate())
                        .build()
        ).collect(Collectors.toList());
    }

    public ProgramDto getProgram(Long id) {
        return ProgramDto.from(programRepository.findByIdWithProgramFile(id).orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다.")));
    }

    public void updateProgram(Long id, ProgramDto dto) {
        Program program = programRepository.findById(id).orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다."));
        program.update(dto);
    }
}

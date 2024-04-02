package com.walab.happymanback.program.service;

import com.walab.happymanback.category.repository.CategoryRepository;
import com.walab.happymanback.program.dto.ProgramDto;
import com.walab.happymanback.program.entity.Program;
import com.walab.happymanback.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgramService {
    private final CategoryRepository categoryRepository;
    private final ProgramRepository programRepository;

    public void createProgram(ProgramDto dto) {
        programRepository.save(Program.from(dto, categoryRepository.findById(dto.getCategoryDto().getId()).orElseThrow()));
    }
}

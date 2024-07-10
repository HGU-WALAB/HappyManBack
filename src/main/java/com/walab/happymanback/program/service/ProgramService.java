package com.walab.happymanback.program.service;

import com.walab.happymanback.base.exception.DoNotExistException;
import com.walab.happymanback.category.repository.CategoryRepository;
import com.walab.happymanback.participant.repository.ParticipantRepository;
import com.walab.happymanback.program.dto.ProgramDto;
import com.walab.happymanback.program.entity.Program;
import com.walab.happymanback.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgramService {
  private final CategoryRepository categoryRepository;
  private final ProgramRepository programRepository;
  private final ParticipantRepository participantRepository;

  @PersistenceContext
  private EntityManager entityManager;

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
    if (dto.getCategoryDto().getId() != null) {
      program.setCategory(
          categoryRepository
              .findById(dto.getCategoryDto().getId())
              .orElseThrow(() -> new DoNotExistException("해당 카테고리가 없습니다.")));
    }
    program.update(dto);
  }

  public ProgramDto getProgram(Long id) {
    return ProgramDto.from(
        programRepository
            .findById(id)
            .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다.")));
  }

  public ProgramDto getProgramWithCategory(Long id) {
    Program program =
        programRepository
            .findByIdWithCategory(id)
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

  public void deletePrograms(List<Long> ids) {
    for (Long id : ids) {
      Program program =
          programRepository
              .findById(id)
              .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다."));
      program.clearFiles();
      participantRepository.deleteByProgramId(id);
      programRepository.delete(program);
    }
  }

  public ProgramDto getProgramWithAll(Long id) {
    return ProgramDto.withAll(
        programRepository
            .findByIdWithAll(id)
            .orElseThrow(() -> new DoNotExistException("해당 프로그램이 없습니다.")));
  }

  public List<ProgramDto> getBookmarkedPrograms(String uniqueId) {
    return programRepository.findAllIsBookmarked(uniqueId).stream()
        .map(ProgramDto::from)
        .collect(Collectors.toList());
  }

  public List<ProgramDto> getProgramsFilteredPaged(int page, int size, Long categoryId, String classification) {
    StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Program p");
    boolean hasCategoryId = categoryId != null;
    boolean hasClassification = classification != null;

    if (hasCategoryId) {
      queryBuilder.append(" WHERE p.category.id = :categoryId");
    }

    if (hasClassification) {
      if (hasCategoryId) {
        switch (classification) {
          case "신청 진행":
            queryBuilder.append(" AND p.applyStartDate <= CURRENT_DATE AND p.applyEndDate >= CURRENT_DATE");
            break;
          case "신청 마감":
            queryBuilder.append(" AND p.applyEndDate < CURRENT_DATE");
            break;
          case "신청 대기":
            queryBuilder.append(" AND p.applyStartDate > CURRENT_DATE");
            break;
        }
      } else {
        queryBuilder.append(" WHERE");
        switch (classification) {
          case "신청 진행":
            queryBuilder.append(" p.applyStartDate <= CURRENT_DATE AND p.applyEndDate >= CURRENT_DATE");
            break;
          case "신청 마감":
            queryBuilder.append(" p.applyEndDate < CURRENT_DATE");
            break;
          case "신청 대기":
            queryBuilder.append(" p.applyStartDate > CURRENT_DATE");
            break;
        }
      }
    }

    queryBuilder.append(" ORDER BY p.id DESC");

    TypedQuery<Program> query = entityManager.createQuery(queryBuilder.toString(), Program.class);

    if (hasCategoryId) {
      query.setParameter("categoryId", categoryId);
    }

    query.setFirstResult((page-1) * size);
    query.setMaxResults(size);

    List<Program> programs = query.getResultList();

    return programs.stream().map(ProgramDto::from).collect(Collectors.toList());
  }


  public Integer getTotalPage(int size, Long categoryId, String classification) {
    StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(p) FROM Program p");
    boolean hasCategoryId = categoryId != null;
    boolean hasClassification = classification != null;

    if (hasCategoryId) {
      queryBuilder.append(" WHERE p.category.id = :categoryId");
    }

    if (hasClassification) {
      if (hasCategoryId) {
        switch (classification) {
          case "신청 진행":
            queryBuilder.append(" AND p.applyStartDate <= CURRENT_DATE AND p.applyEndDate >= CURRENT_DATE");
            break;
          case "신청 마감":
            queryBuilder.append(" AND p.applyEndDate < CURRENT_DATE");
            break;
          case "신청 대기":
            queryBuilder.append(" AND p.applyStartDate > CURRENT_DATE");
            break;
        }
      } else {
        queryBuilder.append(" WHERE");
        switch (classification) {
          case "신청 진행":
            queryBuilder.append(" p.applyStartDate <= CURRENT_DATE AND p.applyEndDate >= CURRENT_DATE");
            break;
          case "신청 마감":
            queryBuilder.append(" p.applyEndDate < CURRENT_DATE");
            break;
          case "신청 대기":
            queryBuilder.append(" p.applyStartDate > CURRENT_DATE");
            break;
        }
      }
    }

    TypedQuery<Long> query = entityManager.createQuery(queryBuilder.toString(), Long.class);

    if (hasCategoryId) {
      query.setParameter("categoryId", categoryId);
    }

    Long total = query.getSingleResult();

    return (int) Math.ceil((double) total / size);
  }
}

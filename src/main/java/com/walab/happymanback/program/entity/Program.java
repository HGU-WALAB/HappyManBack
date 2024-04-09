package com.walab.happymanback.program.entity;

import com.walab.happymanback.base.entity.BaseTime;
import com.walab.happymanback.category.entity.Category;
import com.walab.happymanback.program.dto.ProgramDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Program extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "quota")
  private Integer quota;

  @Column(name = "current_quota", nullable = false, columnDefinition = "integer default 0")
  private Integer currentQuota;

  @Column(name = "information", columnDefinition = "TEXT")
  private String information;

  @Column(name = "apply_start_date", nullable = false)
  private LocalDateTime applyStartDate;

  @Column(name = "apply_end_date", nullable = false)
  private LocalDateTime applyEndDate;

  @Column(name = "start_date", nullable = false)
  private LocalDateTime startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDateTime endDate;

  @Column(name = "application_form", columnDefinition = "TEXT")
  private String applicationForm;

  @Column(name = "survey_form", columnDefinition = "TEXT")
  private String surveyForm;

  @Column(name = "manager_name", nullable = false, length = 50)
  private String managerName;

  @Column(name = "manager_contact", nullable = false, length = 50)
  private String managerContact;

  @Column(name = "image", length = 300)
  private String image;

  @OneToMany(
      mappedBy = "program",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<ProgramFile> files;

  public static Program from(ProgramDto dto, Category category) {
    Program newProgram =
        Program.builder()
            .name(dto.getName())
            .quota(dto.getQuota())
            .currentQuota(dto.getCurrentQuota())
            .information(dto.getInformation())
            .applyStartDate(dto.getApplyStartDate())
            .applyEndDate(dto.getApplyEndDate())
            .startDate(dto.getStartDate())
            .endDate(dto.getEndDate())
            .applicationForm(dto.getApplicationForm())
            .surveyForm(dto.getSurveyForm())
            .managerName(dto.getManagerName())
            .managerContact(dto.getManagerContact())
            .image(dto.getImage())
            .category(category)
            .build();
    newProgram.setFiles(
        dto.getProgramFileDtos().stream()
            .map(programFileDto -> ProgramFile.from(newProgram, programFileDto))
            .collect(Collectors.toList()));
    return newProgram;
  }
}

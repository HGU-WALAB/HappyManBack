package com.walab.happymanback.program.entity;

import com.walab.happymanback.base.entity.BaseTime;
import com.walab.happymanback.program.dto.ProgramFileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProgramFile extends BaseTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "program_id", nullable = false)
  private Program program;

  @Column(name = "origin_file_name", nullable = false, length = 260)
  private String originFileName;

  @Column(name = "stored_file_path", nullable = false, length = 500)
  private String storedFilePath;

  public static ProgramFile from(Program program, ProgramFileDto dto) {
    return ProgramFile.builder()
        .program(program)
        .originFileName(dto.getOriginFileName())
        .storedFilePath(dto.getStoredFilePath())
        .build();
  }
}
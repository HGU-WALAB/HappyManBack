package com.walab.happymanback.program.controller.response;

import com.walab.happymanback.bookmark.dto.BookmarkDto;
import com.walab.happymanback.program.dto.ProgramDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class PagedProgramListResponse {
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private List<Program> programs;
  private Integer totalPage;

  public static PagedProgramListResponse from(
      List<ProgramDto> programDtos, List<BookmarkDto> bookmarkDtos, Integer totalPage) {
    return PagedProgramListResponse.builder()
        .programs(
            programDtos.stream()
                .map(
                    programDto -> {
                      Program program = Program.from(programDto);
                      program.setIsBookmarked(
                          bookmarkDtos.stream()
                              .anyMatch(
                                  bookmarkDto ->
                                      bookmarkDto.getProgramId().equals(program.getId())));
                      return program;
                    })
                .collect(Collectors.toList()))
        .totalPage(totalPage)
        .build();
  }

  @NoArgsConstructor
  @Setter
  @Getter
  private static class Program {
    private Long id;
    private String name;
    private String status;
    private String image;
    private String teacher;
    private String applyEndDate;

    private Boolean isBookmarked;

    private static Program from(ProgramDto programDto) {
      Program program = new Program();
      program.id = programDto.getId();
      program.name = programDto.getName();
      program.image = programDto.getImage();
      program.teacher = programDto.getTeacher();
      program.applyEndDate = programDto.getApplyEndDate().format(DATE_TIME_FORMATTER);
      return program;
    }
  }
}

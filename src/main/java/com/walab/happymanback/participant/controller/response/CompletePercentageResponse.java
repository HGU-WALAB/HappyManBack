package com.walab.happymanback.participant.controller.response;

import com.walab.happymanback.participant.dto.ParticipantDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CompletePercentageResponse {
  private List<Semester> semesters;

  public static CompletePercentageResponse from(List<ParticipantDto> participants) {
    List<Semester> semesters = new ArrayList<>();
    for (ParticipantDto participant : participants) {
      boolean isExist = false;
      for (Semester semester : semesters) {
        if (semester.getSemester().equals(participant.getSemester())) {
          isExist = true;
          if (participant.getStatus().equals("수료")) {
            semester.setCompleted(semester.getCompleted() + 1);
          } else {
            semester.setUncompleted(semester.getUncompleted() + 1);
          }
        }
      }
      if (!isExist) {
        Semester newSemester = new Semester();
        newSemester.setSemester(participant.getSemester());
        if (participant.getStatus().equals("수료")) {
          newSemester.setCompleted(1);
          newSemester.setUncompleted(0);
        } else {
          newSemester.setCompleted(0);
          newSemester.setUncompleted(1);
        }
        semesters.add(newSemester);
      }
    }

    // Sort semesters by semester in ascending order
    semesters.sort(new Comparator<Semester>() {
        @Override
        public int compare(Semester s1, Semester s2) {
            return s1.getSemester().compareTo(s2.getSemester());
        }
    });

    return new CompletePercentageResponse(semesters);
  }

  @Getter
  @Setter
  private static class Semester {
    private Integer semester;
    private Integer completed;
    private Integer uncompleted;
  }
}

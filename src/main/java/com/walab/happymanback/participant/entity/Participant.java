package com.walab.happymanback.participant.entity;

import com.walab.happymanback.base.entity.BaseTime;
import com.walab.happymanback.participant.dto.ParticipantDto;
import com.walab.happymanback.participant.entity.enums.ParticipantStatus;
import com.walab.happymanback.program.entity.Program;
import com.walab.happymanback.user.entity.User;

import javax.persistence.*;

@Entity
public class Participant extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParticipantStatus status;

    @Column(name = "application_form", columnDefinition = "TEXT")
    private String applicationForm;

    @Column(name = "survey_form", columnDefinition = "TEXT")
    private String surveyForm;

    public static Participant apply(ParticipantDto dto, Program program, User user) {
        Participant participant = new Participant();
        participant.program = program;
        participant.user = user;
        participant.status = ParticipantStatus.WAITING;
        participant.applicationForm = dto.getApplicationForm();
        return participant;
    }
}

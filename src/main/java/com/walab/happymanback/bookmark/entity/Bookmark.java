package com.walab.happymanback.bookmark.entity;

import com.walab.happymanback.base.entity.BaseTime;
import com.walab.happymanback.program.entity.Program;
import com.walab.happymanback.user.entity.User;

import javax.persistence.*;

@Entity
public class Bookmark extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}

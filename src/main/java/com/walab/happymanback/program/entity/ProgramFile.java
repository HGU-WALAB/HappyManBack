package com.walab.happymanback.program.entity;

import com.walab.happymanback.base.entity.BaseTime;

import javax.persistence.*;

@Entity
public class ProgramFile extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(name = "file_name", nullable = false, length = 260)
    private String fileName;

    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;
}

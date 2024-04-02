package com.walab.happymanback.applicationTemplate.entity;

import com.walab.happymanback.base.entity.BaseTime;

import javax.persistence.*;

@Entity
public class ApplicationTemplate extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
}

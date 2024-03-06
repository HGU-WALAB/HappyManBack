package com.walab.happymanback.user.domain;

import com.walab.happymanback.base.domain.BaseTime;
import jakarta.persistence.*;

@Entity
public class User extends BaseTime {
    @Id
    @Column(nullable = false, length = 50)
    private String uniqueId;

    @Column(nullable = false, length = 50)
    private String name;

    private Integer grade;

    private Integer semester;

    private String department;

    private String major1;

    private String major2;
}

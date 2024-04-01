package com.walab.happymanback.category.domain;

import com.walab.happymanback.base.domain.BaseTime;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Category extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name="is_hidden", nullable = false)
    private Boolean isHidden;
}

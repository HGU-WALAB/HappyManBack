package com.walab.happymanback.category.dto;

import com.walab.happymanback.category.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryDto {
    private Long id;

    private String name;

    private Boolean isHidden;

    public static CategoryDto from(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .isHidden(category.getIsHidden())
                .build();
    }
}

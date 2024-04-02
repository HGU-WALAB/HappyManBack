package com.walab.happymanback.category.controller.response;

import com.walab.happymanback.category.dto.CategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class CategoryListResponse {
    private List<Category> categories;

    public static CategoryListResponse from(List<CategoryDto> dtos) {
        return CategoryListResponse.builder()
                .categories(dtos.stream()
                        .filter(dto -> !dto.getIsHidden())
                        .map(Category::from)
                        .collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Builder
    public static class Category {
        private Long id;
        private String name;

        public static Category from(CategoryDto dto) {
            return Category.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .build();
        }
    }
}

package com.walab.happymanback.category.controller;

import com.walab.happymanback.category.controller.response.CategoryListResponse;
import com.walab.happymanback.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/api/happyman/all/categories")
    public ResponseEntity<CategoryListResponse> getCategories() {
        return ResponseEntity.ok(CategoryListResponse.from(categoryService.getCategories()));
    }
}

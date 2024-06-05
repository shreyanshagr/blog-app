package com.sparrow.blog.service;

import com.sparrow.blog.payload.CategoryDto;
import com.sparrow.blog.payload.UserDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);
    CategoryDto getCategoryById(int categoryId);
    List<CategoryDto> getAllCategory();
    void deleteCategory(int categoryId);
}

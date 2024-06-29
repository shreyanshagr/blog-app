package com.sparrow.blog.service;

import com.sparrow.blog.payload.CategoryDto;
import com.sparrow.blog.payload.CategoryResponse;
import com.sparrow.blog.payload.UserDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);
    CategoryDto getCategoryById(int categoryId);
    CategoryResponse getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir);
    void deleteCategory(int categoryId);
}

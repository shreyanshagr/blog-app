package com.sparrow.blog.service.impl;

import com.sparrow.blog.entity.Category;
import com.sparrow.blog.exception.ResourceNotFoundException;
import com.sparrow.blog.payload.CategoryDto;
import com.sparrow.blog.repository.CategoryRepo;
import com.sparrow.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.categoryDtotoCategory(categoryDto);
        Category savedCategory = this.categoryRepo.save(category);
        return this.categoryToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));;
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepo.save(category);
        return this.categoryToCategoryDto(updatedCategory);
    }

    @Override
    public CategoryDto getCategoryById(int categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        return this.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories= this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos= categories.stream().map(this::categoryToCategoryDto).toList();
        return categoryDtos;
    }

    @Override
    public void deleteCategory(int categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
        this.categoryRepo.delete(category);
    }
    private Category categoryDtotoCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        return category;
    }
    private CategoryDto categoryToCategoryDto(Category savedCategory) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(savedCategory.getCategoryId());
        categoryDto.setCategoryTitle(savedCategory.getCategoryTitle());
        categoryDto.setCategoryDescription(savedCategory.getCategoryDescription());
        return categoryDto;
    }


}

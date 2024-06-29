package com.sparrow.blog.service.impl;

import com.sparrow.blog.entity.Category;
import com.sparrow.blog.exception.ResourceNotFoundException;
import com.sparrow.blog.payload.CategoryDto;
import com.sparrow.blog.payload.CategoryResponse;
import com.sparrow.blog.repository.CategoryRepo;
import com.sparrow.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public CategoryResponse getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> pageCategories = this.categoryRepo.findAll(pageable);

        List<CategoryDto> categoryDtos = pageCategories.stream()
                .map(this::categoryToCategoryDto)
                .toList();

        return getCategoryResponse(pageCategories,categoryDtos);
    }

    private CategoryResponse getCategoryResponse(Page<Category> pageCategories, List<CategoryDto> categoryDtos) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDtos);
        categoryResponse.setPageNumber(pageCategories.getNumber());
        categoryResponse.setPageSize(pageCategories.getSize());
        categoryResponse.setTotalElements(pageCategories.getTotalElements());
        categoryResponse.setCurrentPageElements(pageCategories.getNumberOfElements());
        categoryResponse.setTotalPages(pageCategories.getTotalPages());
        categoryResponse.setLastPage(pageCategories.isLast());
        return categoryResponse;
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

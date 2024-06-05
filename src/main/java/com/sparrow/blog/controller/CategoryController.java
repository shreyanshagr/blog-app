package com.sparrow.blog.controller;

import com.sparrow.blog.payload.ApiResponse;
import com.sparrow.blog.payload.CategoryDto;
import com.sparrow.blog.repository.CategoryRepo;
import com.sparrow.blog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createcategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createcategoryDto, HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable int categoryId){
        CategoryDto updatedcategoryDto = this.categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedcategoryDto,HttpStatus.CREATED);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getUserById(@PathVariable int categoryId){
        CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        return new ResponseEntity<>(this.categoryService.getAllCategory(),HttpStatus.OK);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category Delete Successful",true),HttpStatus.OK);
    }
}

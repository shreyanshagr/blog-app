package com.sparrow.blog.controller;

import com.sparrow.blog.config.AppConstants;
import com.sparrow.blog.payload.ApiResponse;
import com.sparrow.blog.payload.CategoryDto;
import com.sparrow.blog.payload.CategoryResponse;
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
@RequestMapping("/api/category")
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
    public ResponseEntity<CategoryResponse> getAllCategory (@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
                                                            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
                                                            @RequestParam(value = "sortBy",defaultValue = AppConstants.CATEGORY_SORTBY,required = false) String sortBy,
                                                            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ){
        return new ResponseEntity<>(this.categoryService.getAllCategory(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category Delete Successful",true),HttpStatus.OK);
    }
}

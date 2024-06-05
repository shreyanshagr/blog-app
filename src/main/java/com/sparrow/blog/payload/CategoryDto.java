package com.sparrow.blog.payload;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    private int categoryId;
    @NotEmpty
    private String categoryTitle;
    @NotEmpty
    @Size(max = 200)
    private String categoryDescription;
}

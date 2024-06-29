package com.sparrow.blog.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse
{
    private List<UserDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private long currentPageElements;
    private int totalPages;
    private boolean lastPage;

}

package com.sparrow.blog.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> posts;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private long CurrentPageElements;
    private int totalPages;
    private boolean lastPage;

}

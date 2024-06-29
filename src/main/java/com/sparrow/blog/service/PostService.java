package com.sparrow.blog.service;

import com.sparrow.blog.payload.PostDto;
import com.sparrow.blog.payload.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto,int userId,int categoryId);
    PostDto updatePost(PostDto postDto,int postId);
    PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(int postId);
    void deletePost(int postId);
    PostResponse getPostByUser(int userId,int pageNumber, int pageSize, String sortBy, String sortDir);
    PostResponse getPostByCategory(int categoryId,int pageNumber, int pageSize, String sortBy, String sortDir);
    PostResponse searchPosts(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir);
}

package com.sparrow.blog.service;

import com.sparrow.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
     CommentDto createComment(CommentDto commentDto, int postId, int userId);

     void deleteComment(int commentId);

     CommentDto updateComment(CommentDto commentDto, Integer commentId);

     public List<CommentDto> getCommentsByUser(int userId);
}

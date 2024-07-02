package com.sparrow.blog.service.impl;

import com.sparrow.blog.entity.Comment;
import com.sparrow.blog.entity.Post;
import com.sparrow.blog.entity.User;
import com.sparrow.blog.exception.ResourceNotFoundException;
import com.sparrow.blog.payload.CommentDto;
import com.sparrow.blog.repository.CommentRepo;
import com.sparrow.blog.repository.PostRepo;
import com.sparrow.blog.repository.UserRepo;
import com.sparrow.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Override
    public CommentDto createComment(CommentDto commentDto, int postId, int userId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        Comment comment = commentDtoToComment(commentDto);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this.commentRepo.save(comment);
        return commentToCommentDto(savedComment);
    }


    @Override
    public void deleteComment(int commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
        this.commentRepo.delete(comment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
        comment.setContent(commentDto.getContent());
        Comment updatedComment = this.commentRepo.save(comment);
        return commentToCommentDto(updatedComment);
    }

    @Override
    public List<CommentDto> getCommentsByUser(int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Comment> comments = this.commentRepo.findByUser(user);
        return comments.stream().map(this::commentToCommentDto).toList();
    }

    private CommentDto commentToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(comment.getContent());
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setUserId(comment.getUser().getUserId());
        return commentDto;
    }

    private Comment commentDtoToComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCommentId(commentDto.getCommentId());
        return comment;
    }

}

package com.sparrow.blog.controller;

import com.sparrow.blog.payload.ApiResponse;
import com.sparrow.blog.payload.CommentDto;
import com.sparrow.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable("postId") Integer postId,
                                                    @PathVariable("userId") Integer userId)
    {
        CommentDto savedComment = this.commentService.createComment(commentDto,postId,userId);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment Deleted Successfully !! ",true),HttpStatus.OK);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable("commentId") Integer commentId) {
        CommentDto updatedComment = this.commentService.updateComment(commentDto,commentId);
        return new ResponseEntity<>(updatedComment, HttpStatus.CREATED);
    }

    @GetMapping("/comment/user/{userId}")
    public ResponseEntity<List<CommentDto>> getCommentsByUser(@PathVariable int userId) {
        List<CommentDto> comments = commentService.getCommentsByUser(userId);
        return ResponseEntity.ok(comments);
    }
}

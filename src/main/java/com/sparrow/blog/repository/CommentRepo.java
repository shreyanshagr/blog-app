package com.sparrow.blog.repository;

import com.sparrow.blog.entity.Comment;
import com.sparrow.blog.entity.Post;
import com.sparrow.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    List<Comment> findByUser(User user);
    List<Comment> findByPost(Post post);
}
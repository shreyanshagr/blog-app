package com.sparrow.blog.payload;

import com.sparrow.blog.entity.Category;
import com.sparrow.blog.entity.Comment;
import com.sparrow.blog.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class PostDto {

    private int postId;

    private String title;

    private String content;

    private String imageName = "default.png";

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private CategoryDto categoryDto;

    private UserDto userDto;

    private List<CommentDto> commentDtos = new ArrayList<>();
}

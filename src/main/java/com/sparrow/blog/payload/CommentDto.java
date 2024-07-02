package com.sparrow.blog.payload;


import lombok.Data;


@Data
public class CommentDto {

    private int commentId;

    private String content;

    private Integer userId;
}

package com.sparrow.blog.payload;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;

    private String password;
}

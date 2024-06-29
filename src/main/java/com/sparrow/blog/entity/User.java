package com.sparrow.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Slf4j
@NoArgsConstructor
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
//    @GenericGenerator(name = "native",strategy = "native")
    private int userId;
    private String name;
    private String email;
    private String password;
    private String about;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Post> post = new ArrayList<>();



}

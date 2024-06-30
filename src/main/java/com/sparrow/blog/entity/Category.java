package com.sparrow.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Slf4j
@NoArgsConstructor
public class Category {
    @Id
//    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int categoryId;
    private String categoryTitle;
    private String categoryDescription;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<Post> post = new ArrayList<>();

}

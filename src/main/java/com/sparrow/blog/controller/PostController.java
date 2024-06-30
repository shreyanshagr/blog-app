package com.sparrow.blog.controller;

import com.sparrow.blog.config.AppConstants;
import com.sparrow.blog.entity.Post;
import com.sparrow.blog.payload.ApiResponse;
import com.sparrow.blog.payload.ImageResponse;
import com.sparrow.blog.payload.PostDto;
import com.sparrow.blog.payload.PostResponse;
import com.sparrow.blog.service.FileService;
import com.sparrow.blog.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@Slf4j
@RequestMapping("api/")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;
    // createPost
    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable("userId") int userId,
            @PathVariable("categoryId") int categoryId
    ) {
        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    //Update post
    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto,
            @PathVariable("postId") int postId
    )
    {
        PostDto updatedPost = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
    }

    //Delete Post
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") int postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Successfully deleted!!",true),HttpStatus.OK);
    }

    //Get single post
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") int postId){
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    //Get all posts
    @GetMapping("/post")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    )
    {
        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //Get post by Category
    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<PostResponse> getPostByCategory(
            @PathVariable("categoryId") int categoryId,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        PostResponse postResponse = this.postService.getPostByCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //Get post by User
    @GetMapping("/user/{userId}/post")
    public ResponseEntity<PostResponse> getPostByUser(
            @PathVariable("userId") int userId,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        PostResponse postResponse = this.postService.getPostByUser(userId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //Search Post by Keywords
    @GetMapping("/post/search")
    public ResponseEntity<PostResponse> searchPosts(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.POST_SORTBY,required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ) {
        PostResponse postResponse = this.postService.searchPosts(keyword, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // Upload Post Image
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<ImageResponse> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("postId") Integer postId
    ) {

        // Check if the uploaded file is an image
        String contentType = image.getContentType();
        if (contentType == null ||
                !(contentType.equals(MediaType.IMAGE_JPEG_VALUE) ||
                        contentType.equals(MediaType.IMAGE_PNG_VALUE) ||
                        contentType.equals(MediaType.IMAGE_GIF_VALUE))) {
            return new ResponseEntity<>(new ImageResponse(null, "Invalid file type. Only image files are allowed."), HttpStatus.BAD_REQUEST);
        }

        PostDto postdto = this.postService.getPostById(postId);
      try{
          String savedFile = this.fileService.uploadImage(path,image);
            postdto.setImageName(savedFile);
            PostDto updatedPost = this.postService.updatePost(postdto,postId);
          return new ResponseEntity<>(new ImageResponse(savedFile,"is saved Successfully"),HttpStatus.OK);
      }
      catch (IOException e){
          return new ResponseEntity<>(new ImageResponse(null,"File is not saved due to Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        log.info("Attempting to fetch image: {}", imageName);
        InputStream resource = this.fileService.getResource(path, imageName);
        if (resource == null) {
            log.error("Image not found: {}", imageName);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.getWriter().write("Image not found");
            return;
        }
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }



}


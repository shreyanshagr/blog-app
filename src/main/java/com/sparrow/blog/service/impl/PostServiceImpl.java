package com.sparrow.blog.service.impl;

import com.sparrow.blog.entity.Category;
import com.sparrow.blog.entity.Post;
import com.sparrow.blog.entity.User;
import com.sparrow.blog.exception.ResourceNotFoundException;
import com.sparrow.blog.payload.CategoryDto;
import com.sparrow.blog.payload.PostDto;
import com.sparrow.blog.payload.PostResponse;
import com.sparrow.blog.payload.UserDto;
import com.sparrow.blog.repository.CategoryRepo;
import com.sparrow.blog.repository.PostRepo;
import com.sparrow.blog.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements com.sparrow.blog.service.PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, int userId, int categoryId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","userId",userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        UserDto userDto = userToUserDto(user);
        CategoryDto categoryDto = categoryToCategoryDto(category);
        postDto.setUserDto(userDto);
        postDto.setCategoryDto(categoryDto);
        postDto.setCreatedAt(LocalDateTime.now());
        Post post = postDtoToPost(postDto);
        Post savedPost = this.postRepo.save(post);
        return postToPostDto(savedPost);
    }


    private Post postDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setPostId(postDto.getPostId());
        post.setImageName(postDto.getImageName());
        post.setCreatedAt(postDto.getCreatedAt());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User user = userDtoToUser(postDto.getUserDto());
        Category category = categoryDtotoCategory(postDto.getCategoryDto());
        post.setUser(user);
        post.setCategory(category);
        return post;
    }

    @Override
    public PostDto updatePost(PostDto postDto, int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setUpdatedAt(LocalDateTime.now());
        Post updatedPost = this.postRepo.save(post);
        return postToPostDto(updatedPost);
    }

    @Override
    public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Determine sort direction based on sortDir parameter
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // Create pageable object with page number, page size, and sort direction
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Fetch paginated posts
        Page<Post> pagePost = this.postRepo.findAll(pageable);

        // Map Post entities to PostDto objects
        List<PostDto> postDtos = pagePost.getContent().stream()
                .map(this::postToPostDto)
                .toList();

        // Create and return PostResponse object with pagination details and postDtos
        return getPostResponse(pagePost, postDtos);
    }




    @Override
    public PostDto getPostById(int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        return postToPostDto(post);
    }

    @Override
    public void deletePost(int postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getPostByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Find the user by ID, throw an exception if not found
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        // Determine sort direction based on sortDir parameter
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // Create pageable object with page number, page size, and sort direction
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Fetch paginated posts by user
        Page<Post> pagePost = this.postRepo.findByUser(user, pageable);

        // Map Post entities to PostDto objects
        List<PostDto> postDtos = pagePost.getContent().stream()
                .map(this::postToPostDto)
                .toList();

        // Create and return PostResponse object with pagination details and postDtos
        return getPostResponse(pagePost, postDtos);
    }



    @Override
    public PostResponse getPostByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = this.postRepo.findByCategory(category, pageable);
        List<PostDto> postDtos = pagePost.getContent().stream()
                .map(this::postToPostDto)
                .toList();

        return getPostResponse(pagePost, postDtos);
    }

    private PostResponse getPostResponse(Page<Post> pagePost, List<PostDto> postDtos) {
        PostResponse postResponse = new PostResponse();
        postResponse.setPosts(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setCurrentPageElements(pagePost.getNumberOfElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }
    @Override
    public PostResponse searchPosts(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepo.findByKeyword(keyword, pageable);

        List<PostDto> postDtos = pagePost.getContent().stream()
                .map(this::postToPostDto)
                .toList();

        return getPostResponse(pagePost, postDtos);
    }

    private UserDto userToUserDto(User user){
        return getUserDto(user);
    }

    static UserDto getUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setUserId(user.getUserId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());
        return userDto;
    }

    private CategoryDto categoryToCategoryDto(Category savedCategory) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(savedCategory.getCategoryId());
        categoryDto.setCategoryTitle(savedCategory.getCategoryTitle());
        categoryDto.setCategoryDescription(savedCategory.getCategoryDescription());
        return categoryDto;
    }
    private User userDtoToUser(UserDto userDto){
        return getUser(userDto);
    }

    static User getUser(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        return user;
    }

    private Category categoryDtotoCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        return category;
    }
    private PostDto postToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getPostId());
        postDto.setContent(post.getContent());
        postDto.setTitle(post.getTitle());
        postDto.setImageName(post.getImageName());
        postDto.setCreatedAt(post.getCreatedAt());
        postDto.setUpdatedAt(post.getUpdatedAt());
        postDto.setUserDto(userToUserDto(post.getUser()));
        postDto.setCategoryDto(categoryToCategoryDto(post.getCategory()));
        return postDto;
    }

}

package com.sparrow.blog.service.impl;

import com.sparrow.blog.entity.User;
import com.sparrow.blog.exception.ResourceNotFoundException;
import com.sparrow.blog.payload.UserDto;
import com.sparrow.blog.payload.UserResponse;
import com.sparrow.blog.repository.UserRepo;
import com.sparrow.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparrow.blog.service.impl.PostServiceImpl.getUser;
import static com.sparrow.blog.service.impl.PostServiceImpl.getUserDto;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(user.getAbout());
        User updatedUser = this.userRepo.save(user);
        return this.userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        return this.userToUserDto(user);
    }

    @Override
    public UserResponse getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Determine sort direction based on sortDir parameter
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // Create pageable object with page number, page size, and sort direction
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Fetch paginated users
        Page<User> pageUser = this.userRepo.findAll(pageable);

        // Map User entities to UserDto objects
        List<UserDto> userDtos = pageUser.getContent().stream()
                .map(this::userToUserDto)
                .collect(Collectors.toList());

        // Create and return UserResponse object with pagination details and userDtos
        return getUserResponse(pageUser, userDtos);
    }


    @Override
    public void deleteUser(int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        this.userRepo.delete(user);
    }
    private User dtoToUser(UserDto userDto){
        return getUser(userDto);
    }

    private UserDto userToUserDto(User user){
        return getUserDto(user);
    }

    private UserResponse getUserResponse(Page<User> pageUser, List<UserDto> userDtos) {
        UserResponse userResponse = new UserResponse();
        userResponse.setContent(userDtos);
        userResponse.setPageNumber(pageUser.getNumber());
        userResponse.setPageSize(pageUser.getSize());
        userResponse.setTotalElements(pageUser.getTotalElements());
        userResponse.setCurrentPageElements(pageUser.getNumberOfElements());
        userResponse.setTotalPages(pageUser.getTotalPages());
        userResponse.setLastPage(pageUser.isLast());
        return userResponse;
    }


}
package com.sparrow.blog.service.impl;

import com.sparrow.blog.entity.User;
import com.sparrow.blog.exception.ResourceNotFoundException;
import com.sparrow.blog.payload.UserDto;
import com.sparrow.blog.repository.UserRepo;
import com.sparrow.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public UserDto updateUser(UserDto userDto, int user_id) {
        User user = this.userRepo.findById(user_id).orElseThrow(()-> new ResourceNotFoundException("User","id",user_id));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(user.getAbout());
        User updatedUser = this.userRepo.save(user);
        return this.userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(int user_id) {
        User user = this.userRepo.findById(user_id).orElseThrow(()-> new ResourceNotFoundException("User","id",user_id));
        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(this::userToUserDto).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(int user_id) {
        User user = this.userRepo.findById(user_id).orElseThrow(()->new ResourceNotFoundException("User","Id",user_id));
        this.userRepo.delete(user);
    }
    private User dtoToUser(UserDto userDto){
        User user = new User();
        user.setUser_id(userDto.getUser_id());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        return user;
    }
    private UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setUser_id(user.getUser_id());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());
        return userDto;
    }

}
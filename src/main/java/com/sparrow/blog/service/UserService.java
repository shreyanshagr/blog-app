package com.sparrow.blog.service;

import com.sparrow.blog.payload.UserDto;
import com.sparrow.blog.payload.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user,int user_id);
    UserDto getUserById(int user_id);
    UserResponse getAllUser(int pageNumber,int pageSize, String sortBy,String sortDir);
    void deleteUser(int user_id);


}

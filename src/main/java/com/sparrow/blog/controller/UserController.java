package com.sparrow.blog.controller;

import com.sparrow.blog.payload.ApiResponse;
import com.sparrow.blog.payload.UserDto;
import com.sparrow.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        log.info(userDto.getName());
        log.info(userDto.getEmail());
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
    @PutMapping("/{user_id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable int user_id){
        UserDto updatedUser = this.userService.updateUser(userDto,user_id);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{user_id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int user_id){
        this.userService.deleteUser(user_id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",true), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable int user_id) {
        return ResponseEntity.ok(this.userService.getUserById(user_id));
    }
}

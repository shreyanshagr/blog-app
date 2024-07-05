package com.sparrow.blog.security;

import com.sparrow.blog.entity.User;
import com.sparrow.blog.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user =  this.userRepo.findByEmail(username).orElseThrow(()->new com.sparrow.blog.exception.UsernameNotFoundException("User","email",username));
       return user;
    }
}

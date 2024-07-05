//package com.sparrow.blog.controller;
//
//import com.sparrow.blog.payload.JwtAuthRequest;
//import com.sparrow.blog.payload.JwtAuthResponse;
//import com.sparrow.blog.security.JwtTokenHelper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@Slf4j
//@RequestMapping("/api/v1/auth/")
//public class AuthController {
//
//    @Autowired
//    private JwtTokenHelper jwtTokenHelper;
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @PostMapping("/login")
//    public ResponseEntity<JwtAuthResponse>  createToken(@RequestBody JwtAuthRequest request){
//
//        log.info("JwtAuthRequest" , request.getUsername());
//
//        this.authenticate(request.getUsername(),request.getPassword());
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
//
//        String token = this.jwtTokenHelper.generateToken(userDetails);
//
//        JwtAuthResponse authResponse = new JwtAuthResponse();
//        authResponse.setToken(token);
//
//        return new ResponseEntity<>(authResponse, HttpStatus.OK);
//    }
//
//    private void authenticate(String username, String password) {
//
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
//        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//    }
//}
package com.sparrow.blog.controller;

import com.sparrow.blog.payload.JwtAuthRequest;
import com.sparrow.blog.payload.JwtAuthResponse;
import com.sparrow.blog.security.JwtTokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    // Inject BCryptPasswordEncoder for debugging purposes
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        JwtAuthResponse authResponse = new JwtAuthResponse();
        authResponse.setToken(token);

//        // Log encrypted password for debugging
//        String encryptedPassword = passwordEncoder.encode(request.getPassword());
//        log.debug("Encrypted Password: {}", encryptedPassword);
//        System.out.println("Encrypted Password: " + encryptedPassword);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception{
//        System.out.println("pointer here -> " + password);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        try{
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e){
            System.out.println("Invalid Details !!");
            throw new BadCredentialsException("Invalid username or password !!");
        }
    }
}

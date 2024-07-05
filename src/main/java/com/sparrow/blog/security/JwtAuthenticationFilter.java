//package com.sparrow.blog.security;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtTokenHelper jwtTokenHelper;
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        // Step 1 : Get Token
//        String requestToken = request.getHeader("Authorization");
//        System.out.println("REQ TOKEN " + requestToken);
//
//        /*
//        * Generated token is a String of form Bearer 2342314sfsefs
//        * String token = 2342314sfsefs i.e. extract the token from above
//        * String username = get from customUserDetailService
//        * */
//
//        String token = null;
//        String username = null;
//
//        if(requestToken != null && requestToken.startsWith("Bearer")){
//            token = requestToken.substring(7);
//
//            try {
//                username = this.jwtTokenHelper.getUsernameFromToken(token);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Unable to get JWT Token");
//            } catch (ExpiredJwtException e){
//                System.out.println("Token expired !!");
//            } catch (MalformedJwtException e){
//                System.out.println("Invalid JWT Token");
//            }
//        } else{
//            System.out.println("Token does not begin with Bearer");
//        }
//
//        // Step 2 :  Validate Token
//
//        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//            if(this.jwtTokenHelper.validateToken(token,userDetails)){
//
//                // Perform Authentication
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            } else{
//                System.out.println("Invalid JWT Token");
//            }
//        } else{
//            System.out.println("Username is null OR Context is not NULL");
//        }
//
//        filterChain.doFilter(request,response);
//
//    }
//
//}
package com.sparrow.blog.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }
        String requestToken = request.getHeader("Authorization");

        String token = null;
        String username = null;

        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            token = requestToken.substring(7);

            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("Token expired !!");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT Token");
            }
        } else {
            System.out.println("Token does not begin with Bearer");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                System.out.println("Invalid JWT Token");
            }
        } else {
            System.out.println("Username is null OR Context is not NULL");
        }

        filterChain.doFilter(request, response);
    }
}

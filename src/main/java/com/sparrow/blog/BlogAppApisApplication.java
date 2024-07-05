package com.sparrow.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = "com.sparrow.blog")
public class BlogAppApisApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	public void run(String... args) throws Exception{
		System.out.println("hii");
		System.out.println(this.passwordEncoder.encode("B0bSecure#45"));
	}
}

package com.sparrow.blog.payload;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class UserDto {
    private int userId;
    @NotEmpty
    private String name;

    @Email(message =  "Email is invalid !!")
    private String email;

    @NotEmpty
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;
    @NotEmpty
    @Size(max = 200)
    private String about;
}

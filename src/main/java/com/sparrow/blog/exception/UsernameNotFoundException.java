package com.sparrow.blog.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsernameNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    String fieldValue;
    public UsernameNotFoundException(String resourceName, String fieldName, String fieldValue){
        super(String.format("%s not found with %s : %s" ,resourceName, fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.resourceName = resourceName;
    }
}

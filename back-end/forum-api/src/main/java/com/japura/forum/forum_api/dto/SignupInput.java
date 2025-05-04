package com.japura.forum.forum_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupInput {
    
    private String firstName;
    private String lastName;
    private String faculty;
    private String email;
    private String password;
    private String confirmPassword;
}

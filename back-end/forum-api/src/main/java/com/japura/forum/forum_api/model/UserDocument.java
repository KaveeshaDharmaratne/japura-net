package com.japura.forum.forum_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDocument {
    
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String faculty;
}

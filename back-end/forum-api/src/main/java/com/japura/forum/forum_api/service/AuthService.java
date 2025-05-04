package com.japura.forum.forum_api.service;

import com.japura.forum.forum_api.dto.AuthPayload;
import com.japura.forum.forum_api.dto.SignupInput;
import com.japura.forum.forum_api.dto.User;

public interface AuthService {
    
    AuthPayload signUp(SignupInput input);

    User getUserById(String firebaseUid);
}

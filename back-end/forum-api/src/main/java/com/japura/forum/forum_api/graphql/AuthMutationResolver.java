package com.japura.forum.forum_api.graphql;

import org.springframework.beans.factory.annotation.Autowired;

import com.japura.forum.forum_api.dto.AuthPayload;
import com.japura.forum.forum_api.dto.SignupInput;
import com.japura.forum.forum_api.service.AuthService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;

@DgsComponent
public class AuthMutationResolver {

    @Autowired
    private AuthService authService;

    // Data fetcher for the signup mutation
    @DgsMutation
    public AuthPayload signup(SignupInput input) {
        return authService.signUp(input);
    }
    
}

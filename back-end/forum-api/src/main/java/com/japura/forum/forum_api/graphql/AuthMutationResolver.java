package com.japura.forum.forum_api.graphql;

import com.japura.forum.forum_api.dto.AuthPayload;
import com.japura.forum.forum_api.dto.SignupInput;
import com.japura.forum.forum_api.service.AuthService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;

@DgsComponent
public class AuthMutationResolver {

    private final AuthService authService;

    public AuthMutationResolver(AuthService authService) {
        this.authService = authService;
    }

    // Data fetcher for the signup mutation
    @DgsMutation
    public AuthPayload signup(SignupInput input) {
        return authService.signUp(input);
    }
    
}

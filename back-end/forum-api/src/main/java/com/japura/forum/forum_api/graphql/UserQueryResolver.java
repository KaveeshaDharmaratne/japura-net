package com.japura.forum.forum_api.graphql;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.japura.forum.forum_api.dto.User;
import com.japura.forum.forum_api.service.AuthService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;

@DgsComponent
public class UserQueryResolver {

    private final AuthService authService;

    public UserQueryResolver(AuthService authService) {
        this.authService = authService;
    }

    @DgsData(parentType = "Query", field = "me")
    public User me(DgsDataFetchingEnvironment dfe) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails userDetails) {

                String firebaseUid = userDetails.getUsername();
                return authService.getUserById(firebaseUid);
            }
        }

        return null;
    }
}

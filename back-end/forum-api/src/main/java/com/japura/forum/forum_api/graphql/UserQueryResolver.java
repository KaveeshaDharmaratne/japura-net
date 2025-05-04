package com.japura.forum.forum_api.graphql;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.japura.forum.forum_api.dto.User;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class UserQueryResolver {

    @DgsQuery
    public User me() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails userDetails) {

                String firebaseUid = userDetails.getUsername();

                // Fetch additional user details (like email) from your database
                // or Firebase Authentication if needed.
                // For now, we'll just return a User object with the UID.
                // If you stored email in the Authentication object in the filter, retrieve it
                // here.

                // Example: If you stored FirebaseToken directly in the principal (less common
                // but possible)
                // if (principal instanceof FirebaseToken) {
                // FirebaseToken firebaseToken = (FirebaseToken) principal;
                // return new User(firebaseToken.getUid(), firebaseToken.getEmail());
                // }

                // Assuming we only have UID from UserDetails for now:
                // Note: In a real application, you'd likely fetch the full User object
                // from your database using the firebaseUid.
                User authenticatedUser = new User();
                authenticatedUser.setUid(firebaseUid);
                authenticatedUser.setEmail("lahirucweducatiuon@gmail.com");

                return authenticatedUser;
            }
        }

        return null;
    }
}

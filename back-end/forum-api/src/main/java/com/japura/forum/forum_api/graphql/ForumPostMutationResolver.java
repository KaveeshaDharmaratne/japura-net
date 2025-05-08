package com.japura.forum.forum_api.graphql;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.japura.forum.forum_api.dto.CreatePostInput;
import com.japura.forum.forum_api.dto.ForumPost;
import com.japura.forum.forum_api.service.PostService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsMutation;

@DgsComponent
public class ForumPostMutationResolver {

    private final PostService postService;

    public ForumPostMutationResolver(PostService postService) {
        this.postService = postService;
    }

    @DgsMutation
    @PreAuthorize("isAuthenticated()")
    public ForumPost createPost(CreatePostInput input, DgsDataFetchingEnvironment dfe) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated.");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetails)) {
            throw new RuntimeException("Principal is not UserDetails.");
        }

        UserDetails userDetails = (UserDetails) principal;
        String authorId = userDetails.getUsername();
        
        return postService.createPost(input, authorId);
    }
}

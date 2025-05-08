package com.japura.forum.forum_api.graphql;

import java.util.List;

import com.japura.forum.forum_api.dto.ForumPost;
import com.japura.forum.forum_api.dto.User;
import com.japura.forum.forum_api.service.PostService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;

@DgsComponent
public class ForumPostQueryResolver {

    private final PostService postService;

    public ForumPostQueryResolver(PostService postService) {
        this.postService = postService;
    }

    @DgsQuery
    public ForumPost getPostById(String id) {
        return postService.getPostById(id);
    }

    @DgsQuery
    public List<ForumPost> getAllPosts() {
        return postService.getAllPosts();
    }

    @DgsData(parentType = "ForumPost", field = "author")
    public User author(DgsDataFetchingEnvironment dfe) {
        ForumPost forumPost = dfe.getSource();
        return forumPost != null ? forumPost.getAuthor() : null;
    }
}

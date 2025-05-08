package com.japura.forum.forum_api.service;

import java.util.List;

import com.japura.forum.forum_api.dto.CreatePostInput;
import com.japura.forum.forum_api.dto.ForumPost;

public interface PostService {

    ForumPost createPost(CreatePostInput input, String authorId);

    ForumPost getPostById(String id);

    List<ForumPost> getAllPosts();
}

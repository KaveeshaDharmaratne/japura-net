package com.japura.forum.forum_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumPost {

    private String id;
    private String title;
    private String content;
    private User author;
    private String createdAt;
    private String updatedAt;
}

package com.japura.forum.forum_api.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.japura.forum.forum_api.dto.CreatePostInput;
import com.japura.forum.forum_api.dto.ForumPost;
import com.japura.forum.forum_api.dto.ForumPostDocument;
import com.japura.forum.forum_api.dto.User;
import com.japura.forum.forum_api.service.AuthService;
import com.japura.forum.forum_api.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    private final Firestore firestore;
    private final AuthService authService;

    public PostServiceImpl(Firestore firestore, AuthService authService) {
        this.firestore = firestore;
        this.authService = authService;
    }

    @Override
    public ForumPost createPost(CreatePostInput input, String authorId) {
        try {
            ForumPostDocument postDocument = new ForumPostDocument();
            postDocument.setTitle(input.getTitle());
            postDocument.setContent(input.getContent());
            postDocument.setAuthorId(authorId);

            ApiFuture<DocumentReference> addedDocRef = firestore.collection("posts").add(postDocument);
            DocumentReference docRef = addedDocRef.get();

            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                 ForumPostDocument createdPostDocument = document.toObject(ForumPostDocument.class);

                 if (createdPostDocument == null) {
                      System.err.println("Failed to convert created post document to object for ID: " + document.getId());
                      throw new RuntimeException("Failed to process created post data.");
                 }

                 createdPostDocument.setId(document.getId());

                 User author = authService.getUserById(createdPostDocument.getAuthorId());
                 if (author == null) {
                     System.err.println("Author not found for post: " + createdPostDocument.getId() + " with authorId: " + createdPostDocument.getAuthorId());
                 }

                 return mapDocumentToDto(createdPostDocument, author);

            } else {

                throw new RuntimeException("Failed to retrieve created post document from Firestore.");
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error creating post in Firestore: " + e.getMessage());
            throw new RuntimeException("Failed to create forum post.", e);
        } catch (RuntimeException e) {
             throw e;
        } catch (Exception e) {
             System.err.println("An unexpected error occurred during post creation: " + e.getMessage());
             throw new RuntimeException("Failed to create forum post.", e);
        }
    }

    @Override
    public ForumPost getPostById(String id) {
        try {
            DocumentReference docRef = firestore.collection("posts").document(id);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {

                ForumPostDocument postDocument = document.toObject(ForumPostDocument.class);

                 if (postDocument == null) {
                      System.err.println("Failed to convert post document to object for ID: " + document.getId());
                      return null;
                 }

                postDocument.setId(document.getId());
                User author = authService.getUserById(postDocument.getAuthorId());
                 if (author == null) {
                     System.err.println("Author not found for post: " + postDocument.getId() + " with authorId: " + postDocument.getAuthorId());
                 }

                return mapDocumentToDto(postDocument, author);
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching post from Firestore: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve forum post.", e);
        } catch (Exception e) {
             System.err.println("An unexpected error occurred fetching post: " + e.getMessage());
             throw new RuntimeException("Failed to retrieve forum post.", e);
        }
    }

    @Override
    public List<ForumPost> getAllPosts() {
        try {
            ApiFuture<QuerySnapshot> future = firestore.collection("posts").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            List<ForumPost> posts = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents) {
                ForumPostDocument postDocument = document.toObject(ForumPostDocument.class);
                 postDocument.setId(document.getId());

                User author = authService.getUserById(postDocument.getAuthorId());
                 if (author == null) {
                     System.err.println("Author not found for post: " + postDocument.getId() + " with authorId: " + postDocument.getAuthorId());
                 }

                posts.add(mapDocumentToDto(postDocument, author));
            }

            return posts;

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error fetching all posts from Firestore: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve forum posts.", e);
        } catch (Exception e) {
             System.err.println("An unexpected error occurred fetching all posts: " + e.getMessage());
             throw new RuntimeException("Failed to retrieve forum posts.", e);
        }
    }

    private ForumPost mapDocumentToDto(ForumPostDocument document, User author) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        String createdAtString = document.getCreatedAt() != null ? formatter.format(document.getCreatedAt()) : null;
        String updatedAtString = document.getUpdatedAt() != null ? formatter.format(document.getUpdatedAt()) : null;

        return new ForumPost(
                document.getId(),
                document.getTitle(),
                document.getContent(),
                author,
                createdAtString,
                updatedAtString
        );
    }

}

package com.konada.Konada.service;

import com.konada.Konada.entity.Post;
import com.konada.Konada.exception.DataAlreadyExistsException;
import com.konada.Konada.exception.DataNotFoundException;
import com.konada.Konada.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAllActivePosts();
    }

    public Post getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Post not found"));
        if (post.getDeleteFlag() == 1) {
            throw new DataNotFoundException("Post Id is deleted");
        }
        return post;
    }

    public Post savePost(Post post) {
//        post.setCreatedAt(LocalDateTime.now());
//        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Post postDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        post = Post.builder()
                .postId(post.getPostId()) // Assuming user remains unchanged
                .user(post.getUser()) // Assuming user remains unchanged
                .topic(postDetails.getTopic())
                .title(postDetails.getTitle())
                .content(postDetails.getContent())
                .tags(postDetails.getTags())
                .createdAt(post.getCreatedAt()) // Assuming createdAt remains unchanged
                .updatedAt(LocalDateTime.now())
                .views(post.getViews())  // Assuming createdAt remains unchanged
                .voteLike(post.getVoteLike()) // Assuming createdAt remains unchanged
                .voteDislike(post.getVoteDislike()) // Assuming createdAt remains unchanged
                .deleteFlag(post.getDeleteFlag()) // Assuming createdAt remains unchanged
                .build();

        return postRepository.save(post);
    }

    public Post deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        if (post == null) {
            throw new DataNotFoundException("Cannot delete post because there are votes associated with it");
        }
        post.setDeleteFlag(1);
        return postRepository.save(post);
    }


}

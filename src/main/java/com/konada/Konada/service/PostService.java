package com.konada.Konada.service;

import com.konada.Konada.entity.Post;
import com.konada.Konada.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, Post postDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        post = Post.builder()
                .postId(post.getPostId())
                .user(post.getUser()) // Assuming user remains unchanged
                .topic(post.getTopic()) // Assuming topic remains unchanged
                .title(postDetails.getTitle())
                .content(postDetails.getContent())
                .tags(postDetails.getTags())
                .createdAt(post.getCreatedAt()) // Assuming createdAt remains unchanged
                .updatedAt(postDetails.getUpdatedAt())
                .views(postDetails.getViews())
                .voteLike(postDetails.getVoteLike())
                .voteDislike(postDetails.getVoteDislike())
                .deleteFlag(postDetails.getDeleteFlag())
                .build();

        return postRepository.save(post);

    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}

package com.konada.Konada.controller;

import com.konada.Konada.entity.Post;
import com.konada.Konada.response.ResponseService;
import com.konada.Konada.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts_read")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/post_read")
    public Post getPostById(@RequestParam("post_id") Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping("/post_create")
    public Post createPost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    @PutMapping("/post_update")
    public Post updatePost(@RequestParam("post_id") Long postId, @RequestBody Post post) {
        return postService.updatePost(postId, post);
    }
    @DeleteMapping("/post_delete")
    public void deletePost(@RequestParam("post_id") Long postId) {
        postService.deletePost(postId);
    }

}

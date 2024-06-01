package com.konada.Konada.controller;

import com.konada.Konada.entity.Post;
import com.konada.Konada.exception.DataAlreadyExistsException;
import com.konada.Konada.exception.DataNotFoundException;
import com.konada.Konada.response.*;
import com.konada.Konada.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    @GetMapping("/posts_read")
    public ResponseEntity<?> getAllPosts() {
        try {
            List<Post> posts = postService.getAllPosts();
            if (posts != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.value(), "SUCCESS", posts));
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ApiResponse<>(false, HttpStatus.NO_CONTENT.value(), "FAIL", "NO_CONTENT"));
            }
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, HttpStatus.CONFLICT.value(), "FAIL", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorApiResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR"));
        }
    }

    @GetMapping("/post_read")
    public ResponseEntity<?> getPostById(@RequestParam("post_id") String postIdStr) {
        try {
            Long postId = Long.parseLong(postIdStr);
            Post post = postService.getPostById(postId);
            if (post != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.value(), "SUCCESS", post));
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ApiResponse<>(false, HttpStatus.NO_CONTENT.value(), "FAIL", null));
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(), "FAIL", "Invalid postId format"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, HttpStatus.CONFLICT.value(), "FAIL", e.getMessage()));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorApiResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR"));
        }
    }

    @PostMapping("/post_create")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        try {
            Post createdPost = postService.savePost(post);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, HttpStatus.CREATED.value(), "SUCCESS", createdPost));
        } catch (DataAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, HttpStatus.CONFLICT.value(), "FAIL", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorApiResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR"));
        }
    }

    @PutMapping("/post_update")
    public ResponseEntity<?> updatePost(@RequestParam("post_id") Long postId, @RequestBody Post post) {
        try {
            Post updatedPost = postService.updatePost(postId, post);
            if (updatedPost != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.value(), "SUCCESS", updatedPost));
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ApiResponse<>(false, HttpStatus.NO_CONTENT.value(), "FAIL", "NO_CONTENT"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorApiResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR"));
        }
    }

    @PutMapping("/post_delete")
    public ResponseEntity<?> deletePost(@RequestParam("post_id") Long postId){
        try {
                Post deletedPost = postService.deletePost(postId);
            if (deletedPost != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.value(), "SUCCESS", deletedPost));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ApiResponse<>(false, HttpStatus.CONFLICT.value(), "FAIL", "NO_CONTENT"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorApiResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR"));
        }
    }

}

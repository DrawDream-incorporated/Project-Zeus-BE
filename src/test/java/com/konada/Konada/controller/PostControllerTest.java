package com.konada.Konada.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konada.Konada.entity.Post;
import com.konada.Konada.entity.Topic;
import com.konada.Konada.entity.User;
import com.konada.Konada.exception.DataNotFoundException;
import com.konada.Konada.repository.PostRepository;
import com.konada.Konada.repository.VoteRepository;
import com.konada.Konada.service.PostService;
import com.konada.Konada.service.VoteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(PostController.class)
@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc
class PostControllerTest {


    @Autowired
    private MockMvc mockMvc;

//    @InjectMocks
//    private PostService postService;
//
//    @Mock
//    private PostRepository postRep2ository;
//
    @InjectMocks
    private PostController postController;

    @Mock
    private PostService postService;



    private Post savedPostNotDeleted;
    private Post savedPostDeleted;

    @BeforeEach
    public void setup() {
        System.out.println("실행");
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
        Post postNotDeleted = Post.builder()
                .title("Test Not Deleted")
                .content("Test Not Delete")
                .user(User.builder().userId("user001").build())
                .topic(Topic.builder().topicId("topic001").build())
                .title("Test Title")
                .content("This is the content of the new post.")
                .tags("tag1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .views(0)
                .voteLike(0)
                .voteDislike(0)
                .deleteFlag(0)
                .build();

        when(postService.savePost(any(Post.class))).thenReturn(postNotDeleted);
        savedPostNotDeleted = postService.savePost(postNotDeleted);

        Post postDeleted = Post.builder()
                .title("Test Deleted")
                .content("Test Delete")
                .user(User.builder().userId("user002").build())
                .topic(Topic.builder().topicId("topic001").build())
                .title("Test Delete Title")
                .content("This is the content deleted")
                .tags("tag2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .views(0)
                .voteLike(0)
                .voteDislike(0)
                .deleteFlag(1)
                .build();

        when(postService.savePost(any(Post.class))).thenReturn(postDeleted);
        savedPostDeleted = postService.savePost(postDeleted);
    }


    @DisplayName("포스트 생성")
    @Test
    void testCreatePost_Success() throws Exception {

        Post post = Post.builder()
                .title("Create Post Test")
                .content("Test Content1")
                .user(User.builder().userId("user001").build())
                .topic(Topic.builder().topicId("topic001").build())
                .title("Test Title 2 ")
                .content("This is the content of the new post.")
                .tags("tag2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .views(0)
                .voteLike(0)
                .voteDislike(0)
                .deleteFlag(0)
                .build();

        when(postService.savePost(any(Post.class))).thenReturn(post);

        mockMvc.perform(post("/api/post_create")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"title\": \"New Post\"}")
                ).andExpect(status().isCreated());
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.data.title").value("Create Post"));
//        verify(postService, times(5)).savePost(any(Post.class));

    }



    @DisplayName("postid로 해당 정의된 데이터가 잘 조회되는지 ")
    @Test
    void testGetPostById_Success() throws Exception {
        when(postService.getPostById(savedPostNotDeleted.getPostId())).thenReturn(savedPostNotDeleted);

        mockMvc.perform(get("/api/post_read")
                        .param("post_id", String.valueOf(savedPostNotDeleted.getPostId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Test Title"))
                .andExpect(jsonPath("$.data.content").value("This is the content of the new post."))
                .andExpect(jsonPath("$.data.user.userId").value("user001"))
                .andExpect(jsonPath("$.data.topic.topicId").value("topic001"))
                .andExpect(jsonPath("$.data.tags").value("tag1"))
                .andExpect(jsonPath("$.data.views").value(0))
                .andExpect(jsonPath("$.data.voteLike").value(0))
                .andExpect(jsonPath("$.data.voteDislike").value(0))
                .andExpect(jsonPath("$.data.deleteFlag").value(0));

        verify(postService, times(1)).getPostById(savedPostNotDeleted.getPostId());
    }

    @DisplayName("postid -> deleted일때  ")
    @Test
    void testGetPostById_Fail() throws Exception {
        when(postService.getPostById(savedPostDeleted.getPostId())).thenReturn(savedPostDeleted);

        mockMvc.perform(get("/api/post_read")
                        .param("post_id", String.valueOf(savedPostDeleted.getPostId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Post Id is deleted"));


        verify(postService, times(1)).getPostById(savedPostDeleted.getPostId());
    }



    @DisplayName("post_id가 문자열인 경우")
    @Test
    void testGetPostById_InvalidFormat() throws Exception {
        mockMvc.perform(get("/api/post_read")
                        .param("post_id", "invalid") // 문자열
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("FAIL"));

        verify(postService, times(0)).getPostById(anyLong());
    }

    @DisplayName("존재하지 않는 포스트 ID 조회 시 데이터 없음 응답")
    @Test
    void testGetPostById_NotFound() throws Exception {
        when(postService.getPostById(1000L)).thenThrow(new DataNotFoundException("Post not found"));

        mockMvc.perform(get("/api/post_read")
                        .param("post_id", "1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Post not found"));

        verify(postService, times(1)).getPostById(1000L);
    }


    @AfterEach
    void tearDown() {
        postService.deletePost(savedPostNotDeleted.getPostId());
        postService.deletePost(savedPostDeleted.getPostId());
    }


}
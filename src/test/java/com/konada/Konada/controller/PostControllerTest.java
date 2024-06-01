
package com.konada.Konada.controller;

import com.konada.Konada.entity.Post;
import com.konada.Konada.entity.User;
import com.konada.Konada.entity.Topic;
import com.konada.Konada.exception.DataNotFoundException;
import com.konada.Konada.service.PostService;
import com.konada.Konada.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PostController.class)
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private PostController postController;

    @MockBean
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    private Post savedPostNotDeleted;
    private Post savedPostDeleted;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User user = User.builder()
                .userId("user1")
                .userName("testuser")
                .userEmail("testuser@example.com")
                .password("password")
                .firstName("First")
                .lastName("Last")
                .preferredName("Test User")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isActive(true)
                .build();

        Topic topic = Topic.builder()
                .topicId("topic1")
                .topicName("Test Topic")
                .build();

        savedPostNotDeleted = Post.builder()
                .user(user)
                .topic(topic)
                .title("Title 1 ㅋㅋㅋㅋ")
                .content("Content 1")
                .tags("Tag1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .views(0)
                .voteLike(0)
                .voteDislike(0)
                .deleteFlag(0)
                .build();

        savedPostDeleted = Post.builder()
                .user(user)
                .topic(topic)
                .title("Title 2")
                .content("Content 2")
                .tags("Tag2")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .views(0)
                .voteLike(0)
                .voteDislike(0)
                .deleteFlag(1)
                .build();

        when(postService.savePost(any(Post.class))).thenReturn(savedPostNotDeleted);
        when(postService.savePost(any(Post.class))).thenReturn(savedPostDeleted);

    }

    @DisplayName("포스트 생성")
    @Test
    void testCreatePost_Success() throws Exception {
        Post post = Post.builder()
                .title("Create Post Test")
                .content("Test Content1")
                .user(User.builder().userId("user1").build())
                .topic(Topic.builder().topicId("topic1").build())
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
                        .content("{\"title\": \"Create Post Test\", \"content\": \"Test Content1\", \"user\": {\"userId\": \"user1\"}, \"topic\": {\"topicId\": \"topic1\"}, \"tags\": \"tag2\", \"createdAt\": \"" + LocalDateTime.now() + "\", \"updatedAt\": \"" + LocalDateTime.now() + "\", \"views\": 0, \"voteLike\": 0, \"voteDislike\": 0, \"deleteFlag\": 0 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Create Post Test"));
        verify(postService, times(1)).savePost(any(Post.class));
    }

    @DisplayName("postid로 해당 정의된 데이터가 잘 조회되는지")
    @Test
    void testGetPostById_Success() throws Exception {
        //when(postService.getPostById(savedPostNotDeleted.getPostId())).thenReturn(savedPostNotDeleted);

        mockMvc.perform(get("/api/post_read")
                        .param("post_id", String.valueOf(savedPostNotDeleted.getPostId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.title").value("Title 1 ㅋㅋㅋㅋ"))
                .andExpect(jsonPath("$.data.content").value("Content 1"))
                .andExpect(jsonPath("$.data.user.userId").value("user1"))
                .andExpect(jsonPath("$.data.topic.topicId").value("topic1"))
                .andExpect(jsonPath("$.data.tags").value("Tag1"))
                .andExpect(jsonPath("$.data.views").value(0))
                .andExpect(jsonPath("$.data.voteLike").value(0))
                .andExpect(jsonPath("$.data.voteDislike").value(0))
                .andExpect(jsonPath("$.data.deleteFlag").value(0));

        verify(postService, times(1)).getPostById(savedPostNotDeleted.getPostId());
    }

    @DisplayName("postid가 deleted일때")
    @Test
    void testGetPostById_Fail() throws Exception {
        //when(postService.getPostById(savedPostDeleted.getPostId())).thenThrow(new RuntimeException("Post Id is deleted"));

        mockMvc.perform(get("/api/post_read")
                        .param("post_id", String.valueOf(savedPostDeleted.getPostId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
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
        //when(postService.getPostById(1000L)).thenThrow(new DataNotFoundException("Post not found"));

        mockMvc.perform(get("/api/post_read")
                        .param("post_id", "1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Post not found"));

        verify(postService, times(1)).getPostById(1000L);
    }
}
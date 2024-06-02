package com.konada.Konada.controller;

import com.konada.Konada.entity.Post;
import com.konada.Konada.entity.User;
import com.konada.Konada.entity.Topic;
import com.konada.Konada.entity.Vote;
import com.konada.Konada.entity.VoteId;
import com.konada.Konada.exception.DataAlreadyExistsException;
import com.konada.Konada.exception.DataNotFoundException;
import com.konada.Konada.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoteController.class)
@ExtendWith(MockitoExtension.class)
public class VoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoteService voteService;

    private Vote vote;
    private Post post;

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

        post = Post.builder()
                .user(user)
                .topic(topic)
                .title("Title 1")
                .content("Content 1")
                .tags("Tag1")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .views(0)
                .voteLike(0)
                .voteDislike(0)
                .deleteFlag(0)
                .build();

        vote = Vote.builder()
                .id(new VoteId("user1", 1L))
                .voteFlag(1)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @DisplayName("UT-4-005 : Read a vote by Post ID and User ID with not existed Post ID")
    @Test
    void testGetVoteById_NotFound() throws Exception {
        when(voteService.getVoteById(anyString(), anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/vote_read")
                        .param("post_id", "1")
                        .param("user_id", "user1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("No Content"));

        verify(voteService, times(1)).getVoteById(anyString(), anyLong());
    }

    @DisplayName("UT-3-002 : Create a new vote that exists")
    @Test
    void testCreateVote_AlreadyExists() throws Exception {
        when(voteService.createVote(any(Vote.class))).thenThrow(new DataAlreadyExistsException("Vote already exists"));

        mockMvc.perform(post("/api/vote_create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": {\"userId\": \"user1\", \"postId\": 1}, \"voteFlag\": 1}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Vote already exists"));

        verify(voteService, times(1)).createVote(any(Vote.class));
    }

    @DisplayName("UT-3-003 : Create vote with wrong Post ID")
    @Test
    void testCreateVote_PostNotFound() throws Exception {
        when(voteService.createVote(any(Vote.class))).thenThrow(new RuntimeException("Post not found"));

        mockMvc.perform(post("/api/vote_create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": {\"userId\": \"user1\", \"postId\": 999}, \"voteFlag\": 1}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Post not found"));

        verify(voteService, times(1)).createVote(any(Vote.class));
    }

    @DisplayName("UT-6-003 : Delete a vote, but user doesn't exists in DB")
    @Test
    void testDeleteVote_NotFound() throws Exception {
        doThrow(new DataNotFoundException("Vote not found")).when(voteService).deleteVote(any(Vote.class));

        mockMvc.perform(delete("/api/vote_delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": {\"userId\": \"user1\", \"postId\": 1}, \"voteFlag\": 1}"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Vote not found"));

        verify(voteService, times(1)).deleteVote(any(Vote.class));
    }

    @DisplayName("UT-6-004 : Delete a vote, but Post ID doesn't exists in DB")
    @Test
    void testDeleteVote_PostNotFound() throws Exception {
        doThrow(new RuntimeException("Post not found")).when(voteService).deleteVote(any(Vote.class));

        mockMvc.perform(delete("/api/vote_delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": {\"userId\": \"user1\", \"postId\": 999}, \"voteFlag\": 1}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Post not found"));

        verify(voteService, times(1)).deleteVote(any(Vote.class));
    }
}
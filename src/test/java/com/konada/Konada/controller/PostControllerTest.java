package com.konada.Konada.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konada.Konada.entity.Post;
import com.konada.Konada.entity.Topic;
import com.konada.Konada.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void testCreatePost() throws Exception {
//        LocalDateTime now = LocalDateTime.now();
//        String createdAt = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//        String updatedAt = createdAt;

        Post post = Post.builder()
                .user(User.builder().userId("user001").build())
                .topic(Topic.builder().topicId("topic001").build())
                .title("New Post Title22")
                .content("This is the content of the new post.")
                .tags("tag1, tag2")
//                .createdAt(now)
//                .updatedAt(now)
                .views(0)
                .voteLike(0)
                .voteDislike(0)
                .deleteFlag(0)
                .build();


        mockMvc.perform(post("/api/post_create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.user.userId").value("user001"))
                .andExpect(jsonPath("$.data.topic.topicId").value("topic001"))
                .andExpect(jsonPath("$.data.title").value("New Post Title22"))
                .andExpect(jsonPath("$.data.content").value("This is the content of the new post."))
                .andExpect(jsonPath("$.data.tags").value("tag1, tag2"))
//                .andExpect(jsonPath("$.data.createdAt").value(createdAt))
//                .andExpect(jsonPath("$.data.updatedAt").value(updatedAt))
                .andExpect(jsonPath("$.data.views").value(0))
                .andExpect(jsonPath("$.data.voteLike").value(0))
                .andExpect(jsonPath("$.data.voteDislike").value(0))
                .andExpect(jsonPath("$.data.deleteFlag").value(0));
    }


    @Test
    public void testGetAllPosts() throws Exception {
        mockMvc.perform(get("/api/posts_read"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

}
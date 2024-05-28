package com.konada.Konada.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TestController.class)
class PostingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1() throws Exception {
        mockMvc.perform(get("/api/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"))
                .andDo(print());
    }

    @Test
    public void test2() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("name", "LISA");
        info.add("id", "youjeongpark");

        mockMvc.perform(get("/api/name")
                        .params(info))
                .andExpect(status().isOk())
                .andExpect(content().string("LISA-youjeongpark"))
                .andDo(print());
    }
}
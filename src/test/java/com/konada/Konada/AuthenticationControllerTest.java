package com.konada.Konada;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konada.Konada.controller.AuthenticationController;
import com.konada.Konada.dto.UserDto;
import com.konada.Konada.entity.User;
import com.konada.Konada.service.UserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    private MockMvc mockMvc;

//    @Autowired
//    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private UserDetailsService userDetailsService;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    @DisplayName("when post request to api/register is successful, it should return UserDto")
    public void whenPostRequestToRegisterAndValidUser_thenCorrectResponse() throws Exception {
        UserDto userDto = new UserDto(); // Assuming UserDto has appropriate fields
        userDto.setUsername("testUser");
        userDto.setPassword("testPass");
        User user = UserDto.toUser(userDto);

        given(userDetailsService.createUser(any(User.class))).willReturn(user);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userDto.getUsername()));

        verify(userDetailsService).createUser(any(User.class));
    }
}

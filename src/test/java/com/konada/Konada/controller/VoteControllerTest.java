package com.konada.Konada.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.konada.Konada.entity.Vote;
import com.konada.Konada.entity.VoteId;
import com.konada.Konada.exception.DataAlreadyExistsException;
import com.konada.Konada.repository.PostRepository;
import com.konada.Konada.repository.VoteRepository;
import com.konada.Konada.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class VoteControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private VoteService voteService;
    @Mock
    private VoteRepository voteRepository;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void testCreateVote_Success() {
        // 준비: 테스트용 데이터 생성
        VoteId voteId = new VoteId("user001", 1L);
        Vote vote = new Vote(voteId, 1, null);

        // Mock 설정
        when(voteRepository.findById(voteId)).thenReturn(Optional.empty());
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);

        // 실행: 투표 생성
        Vote createdVote = voteService.createVote(vote);

        // 검증: 반환된 투표가 예상과 일치하는지 확인
        assertEquals(vote, createdVote);
        verify(voteRepository, times(1)).findById(voteId);
        verify(voteRepository, times(1)).save(any(Vote.class));
    }


    @Test
    public void testCreateVote_AlreadyExists() {
        VoteId voteId = new VoteId("user001", 1L);
        Vote vote = new Vote(voteId, 1, LocalDateTime.now());

        when(voteRepository.findById(voteId)).thenReturn(Optional.of(vote));

        // 실행 및 검증: 예외가 발생하는지 확인
        assertThrows(DataAlreadyExistsException.class, () -> voteService.createVote(vote));
        verify(voteRepository, times(1)).findById(voteId);
        verify(voteRepository, never()).save(any(Vote.class));

        // 데이터 지우기 ->
    }

}
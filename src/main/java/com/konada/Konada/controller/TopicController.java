package com.konada.Konada.controller;

import com.konada.Konada.entity.Post;
import com.konada.Konada.entity.Topic;
import com.konada.Konada.response.ApiResponse;
import com.konada.Konada.response.ErrorApiResponse;
import com.konada.Konada.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TopicController {


    private final TopicService topicService;

    @GetMapping("/topics_read")
    public ResponseEntity<?> getAllTopics() {
        try {
            List<Topic> topics = topicService.getAllTopics();
            if (topics != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.value(), "SUCCESS", topics));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(true, HttpStatus.OK.value(), "FAIL", "DATA NOT FOUND"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorApiResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR"));
        }
    }

}

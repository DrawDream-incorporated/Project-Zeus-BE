package com.konada.Konada.controller;

import com.konada.Konada.entity.Vote;
import com.konada.Konada.exception.DataAlreadyExistsException;
import com.konada.Konada.exception.DataNotFoundException;
import com.konada.Konada.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import com.konada.Konada.response.ApiResponse;
import com.konada.Konada.response.ErrorApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VoteController {

    private final VoteService voteService;

    @GetMapping("/votes_read")
    public ResponseEntity<?> getAllVotes() {
        try {
            Optional<List<Vote>> voteOptional = voteService.getAllVotes();
            if (voteOptional.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.value(), "SUCCESS", voteOptional.get()));
            }else {
                return ResponseEntity.ok(new ApiResponse<>(false, HttpStatus.NO_CONTENT.value(), "FAIL", "NOT CONTENT"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorApiResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR"));
        }
    }

    @GetMapping("/vote_read")
    public ResponseEntity<?> getVoteById(@RequestParam("user_id") String userId, @RequestParam("post_id") Long postId) {
        try {
            Optional<Vote> voteOptional = voteService.getVoteById(userId, postId);
            if (voteOptional.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.value(), "SUCCESS", voteOptional.get()));
            } else {
                return ResponseEntity.ok(new ApiResponse<>(false, HttpStatus.NO_CONTENT.value(), "FAIL", "NOT CONTENT"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorApiResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR"));
        }
    }

    @PostMapping("/vote_create")
    public ResponseEntity<?> createVote(@RequestBody Vote vote) {
        try {
            Vote createdVote = voteService.createVote(vote);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, HttpStatus.CREATED.value(), "SUCCESS", createdVote));
        } catch (DataAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, HttpStatus.CONFLICT.value(), "FAIL", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorApiResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR"));
        }
    }

    @DeleteMapping("/vote_delete")
    public ResponseEntity<?> deleteVote(@RequestBody Vote vote) {
        try {
            voteService.deleteVote(vote);
            return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.value(), "SUCCESS", "Delete Vote Successfully"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ErrorApiResponse(false, HttpStatus.NO_CONTENT.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorApiResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL SERVER ERROR"));
        }
    }
}
package com.konada.Konada.controller;

import com.konada.Konada.entity.Vote;
import com.konada.Konada.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @GetMapping
    public List<Vote> getAllVotes() {
        return voteService.getAllVotes();
    }

    @GetMapping("/{id}")
    public Vote getVoteById(@PathVariable Long id) {
        return voteService.getVoteById(id);
    }

    @PostMapping
    public Vote createVote(@RequestBody Vote vote) {
        return voteService.saveVote(vote);
    }

    @DeleteMapping("/{id}")
    public void deleteVote(@PathVariable Long id) {
        voteService.deleteVote(id);
    }
}


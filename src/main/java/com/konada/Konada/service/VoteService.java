package com.konada.Konada.service;

import com.konada.Konada.entity.Vote;
import com.konada.Konada.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public Vote getVoteById(Long postId) {
        return voteRepository.findById(postId).orElse(null);
    }

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public void deleteVote(Long voteId) {
        voteRepository.deleteById(voteId);
    }
}

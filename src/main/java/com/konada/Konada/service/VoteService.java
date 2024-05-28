package com.konada.Konada.service;

import com.konada.Konada.entity.Post;
import com.konada.Konada.entity.Vote;
import com.konada.Konada.entity.VoteId;
import com.konada.Konada.exception.DataAlreadyExistsException;
import com.konada.Konada.repository.VoteRepository;
import com.konada.Konada.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PostRepository postRepository;

    public Optional<List<Vote>> getAllVotes() {
        return Optional.of(voteRepository.findAll());
    }

    @Transactional
    public Vote createVote(Vote vote) {

        vote.setCreatedAt(LocalDateTime.now());

        // Check if the vote already exists
        if (getVoteById(vote.getId().getUserId(), vote.getId().getPostId()).isPresent()) {
            throw new DataAlreadyExistsException("Vote already exists for userId: " + vote.getId().getUserId() +
                    " and postId: " + vote.getId().getPostId());
        }


        Vote savedVote = voteRepository.save(vote);

        // 투표에 대한 게시물 조회
        VoteId voteId = vote.getId();
        Post post = postRepository.findById(voteId.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 좋아요/싫어요 수 업데이트
        if (vote.getVoteFlag() == 1) {
            post.setVoteLike(post.getVoteLike() + 1);
        } else if (vote.getVoteFlag() == -1) {
            post.setVoteDislike(post.getVoteDislike() + 1);
        }

        // 게시물 업데이트
        postRepository.save(post);

        return savedVote;
    }

    public Optional<Vote> getVoteById(String userId, Long postId){
        VoteId voteId = new VoteId(userId, postId);
        return voteRepository.findById(voteId);
    }

    public void deleteVote(Vote vote) {
        String userId = vote.getId().getUserId();
        Long postId = vote.getId().getPostId();

        // Check if the vote already exists
        if (getVoteById(userId, postId).isEmpty()) {
            throw new DataAlreadyExistsException("Vote not already exists for userId: " + vote.getId().getUserId() +
                    " and postId: " + vote.getId().getPostId());
        }

        VoteId voteId = new VoteId(userId, postId);//  VoteId voteId = vote.getId();
        voteRepository.deleteById(voteId);

        Post post = postRepository.findById(voteId.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // update like and dislike
        if (vote.getVoteFlag() == 1) {
            post.setVoteLike(post.getVoteLike() - 1);
        } else if (vote.getVoteFlag() == -1) {
            post.setVoteDislike(post.getVoteDislike() - 1);
        }

        // posting update
        postRepository.save(post);
    }
}

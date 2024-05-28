package com.konada.Konada.repository;

import com.konada.Konada.entity.Vote;
import com.konada.Konada.entity.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {
}

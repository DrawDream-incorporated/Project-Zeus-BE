package com.konada.Konada.entity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vote")
public class Vote {

    @EmbeddedId
    private VoteId id;

    @Column(name = "vote_flag", nullable = false)
    private int voteFlag;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}

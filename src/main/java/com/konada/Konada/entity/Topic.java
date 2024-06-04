package com.konada.Konada.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @Column(name = "topic_id", nullable = false)
    private String topicId;

    @Column(name = "topic_name", nullable = false)
    private String topicName;

}
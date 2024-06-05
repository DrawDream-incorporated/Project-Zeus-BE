package com.konada.Konada.repository;

import com.konada.Konada.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}

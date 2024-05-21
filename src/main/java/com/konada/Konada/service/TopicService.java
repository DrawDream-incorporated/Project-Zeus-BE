package com.konada.Konada.service;

import com.konada.Konada.entity.Topic;
import com.konada.Konada.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic getTopicById(Long topicId) {
        return topicRepository.findById(topicId).orElse(null);
    }

    public Topic saveTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public void deleteTopic(Long topicId) {
        topicRepository.deleteById(topicId);
    }
}

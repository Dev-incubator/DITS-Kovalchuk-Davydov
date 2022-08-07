package com.example.dits.service;

import com.example.dits.dto.TopicDTO;
import com.example.dits.entity.Topic;

import java.util.List;

public interface TopicService {
    void create(Topic topic);
    void update(Topic topic, int id);
    void delete(Topic topic);
    void save(Topic topic);
    List<TopicDTO> findAll();
    List<TopicDTO> getTopicsWithQuestions();
    Topic getTopicByName(String name);
    Topic getTopicByTopicId(int topicId);
    void removeTopicByTopicId(int topicId);
    void updateTopicName(int topicId, String name);
}

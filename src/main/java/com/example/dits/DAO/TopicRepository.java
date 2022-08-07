package com.example.dits.DAO;

import com.example.dits.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic,Integer> {
    Topic getTopicByName(String name);
    Topic getTopicByTopicId(int topicId);
    void removeTopicByTopicId(int topicId);
}

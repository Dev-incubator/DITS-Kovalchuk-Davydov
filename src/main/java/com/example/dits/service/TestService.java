package com.example.dits.service;

import com.example.dits.dto.TestInfoDTO;
import com.example.dits.entity.Test;
import com.example.dits.entity.Topic;

import java.util.List;

public interface TestService {
     void create(Test test);
     void update(Test test, int id);
     void delete(Test test);
     void save(Test test);
     List<Test> findAll();
     List<Test> getTestsByTopic(Topic topic);
     List<TestInfoDTO> getTestsByTopicName(String name);
     List<Test> getTestsByTopic_TopicId(int ID);
     List<TestInfoDTO> getTestInfoDTO (List<Test> tests);
     Test getTestByTestId(int id);
     void removeTestByTestId(int id);
     void update(int id, String name, String description);
}

package com.example.dits.service.impl;

import com.example.dits.DAO.TestRepository;
import com.example.dits.dto.TestInfoDTO;
import com.example.dits.entity.Test;
import com.example.dits.entity.Topic;
import com.example.dits.service.TestService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

   private final TestRepository repository;
   private final ModelMapper modelMapper;

   @Transactional
   public void create(Test test) {
      repository.save(test);
   }

   @Transactional
   public void update(Test test, int id) {
      Optional<Test> t = repository.findById(id);
      if(t.isEmpty())
         return;
      else
         repository.save(test);
   }

   @Transactional
   public void delete(Test test) {
      repository.delete(test);
   }

   @Transactional
   public void save(Test test) {
      repository.save(test);
   }

   @Transactional
   public List<Test> findAll() {
      return repository.findAll();
   }

   @Transactional
   @Override
   public List<Test> getTestsByTopic(Topic topic) {
      return repository.getTestsByTopic(topic);
   }

   @Transactional
   @Override
   public List<TestInfoDTO> getTestsByTopicName(String name) {
      return repository.getTestsByTopicName(name).stream()
              .map(f -> modelMapper.map(f, TestInfoDTO.class))
              .collect(Collectors.toList());
   }

   @Override
   public List<Test> getTestsByTopic_TopicId(int ID) {
      return repository.getTestsByTopic_TopicId(ID);
   }

   @Override
   public List<TestInfoDTO> getTestInfoDTO(List<Test> tests) {
      return null;
   }

   @Transactional
   @Override
   public Test getTestByTestId(int id) {
      return repository.getTestByTestId(id);
   }

   @Transactional
   @Override
   public void removeTestByTestId(int id) {
      repository.removeTestByTestId(id);
   }

   @Transactional
   @Override
   public void update(int id, String name, String description) {
      Test testByTestId = repository.getTestByTestId(id);
      testByTestId.setName(name);
      testByTestId.setDescription(description);
   }


}

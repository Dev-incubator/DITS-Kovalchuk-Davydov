package com.example.dits.service;

import com.example.dits.dto.QuestionDTO;
import com.example.dits.dto.QuestionEditModel;
import com.example.dits.entity.Question;
import com.example.dits.entity.Test;

import java.util.List;

public interface QuestionService {
    void addQuestion(String description, int testId);
    void editQuestion(String description, int questionId);
    void create(Question q);
    void update(Question q, int id);
    void delete(Question q);
    void save(Question q);
    List<Question> findAll();
    List<Question> getQuestionsByTestName(String name);
    List<Question> getQuestionsByTest_TestId(int id);
    List<QuestionDTO> getQuestionsByTest(Test test);
    Question getQuestionById(int id);
    String getDescriptionFromQuestionList(List<QuestionDTO> questionList, int index);
    void editQuestion(QuestionEditModel questionEditModel);
    void addQuestion(QuestionEditModel questionEditModel);
    void removeQuestionById(int id);
}

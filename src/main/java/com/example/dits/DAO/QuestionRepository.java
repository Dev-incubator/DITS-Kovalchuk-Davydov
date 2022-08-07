package com.example.dits.DAO;

import com.example.dits.entity.Question;
import com.example.dits.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Integer> {

    List<Question> getQuestionsByTestName(String name);
    List<Question> getQuestionsByTest_TestId(int id);
    List<Question> getQuestionsByTest(Test test);
    Question getQuestionByQuestionId(int id);
    void removeQuestionByQuestionId(int id);
}

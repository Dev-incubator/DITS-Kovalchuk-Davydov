package com.example.dits.DAO;

import com.example.dits.entity.Answer;
import com.example.dits.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {

    List<Answer> getAnswersByQuestion(Question question);
    void removeAnswerByQuestion(Question question);
}

package com.example.dits.service;

import com.example.dits.dto.AnswerDTO;
import com.example.dits.dto.QuestionDTO;
import com.example.dits.entity.Answer;
import com.example.dits.entity.Question;

import java.util.List;

public interface AnswerService {

    void create(Answer a);
    void update(Answer a, int id);
    void delete(Answer a);
    void save(Answer a);
    List<Answer> findAll();
    List<Answer> getAnswersByQuestion(Question question);
    List<AnswerDTO> getAnswersFromQuestionList(List<QuestionDTO> questionList, int index);
    boolean isRightAnswer(List<Integer> answeredQuestion, List<QuestionDTO> questionList, int questionNumber);

}

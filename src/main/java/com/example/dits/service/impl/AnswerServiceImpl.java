package com.example.dits.service.impl;

import com.example.dits.DAO.AnswerRepository;
import com.example.dits.dto.AnswerDTO;
import com.example.dits.dto.QuestionDTO;
import com.example.dits.entity.Answer;
import com.example.dits.entity.Question;
import com.example.dits.service.AnswerService;
import com.example.dits.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository repo;
    private final QuestionService questionService;
    private final ModelMapper modelMapper;

    @Transactional
    public void create(Answer a) {
        repo.save(a);
    }

    @Transactional
    public void update(Answer a, int id) {
        Optional<Answer> answer = repo.findById(id);
        if(answer.isEmpty())
            return;
        else
            repo.save(a);
    }

    @Transactional
    public void delete(Answer a) {
        repo.delete(a);
    }

    @Transactional
    public void save(Answer a) {
        repo.save(a);
    }

    @Transactional
    public List<Answer> findAll() {
        return repo.findAll();
    }

    @Transactional
    public List<Answer> getAnswersByQuestion(Question question){
        return repo.getAnswersByQuestion(question);
    }

    @Override
    public List<AnswerDTO> getAnswersFromQuestionList(List<QuestionDTO> questionList, int index) {
        Question question = questionService.getQuestionById(questionList.get(index).getQuestionId());
        return getAnswersByQuestion(question).stream()
                .map(f -> modelMapper.map(f, AnswerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isRightAnswer(List<Integer> answeredQuestion, List<QuestionDTO> questionList, int questionNumber) {
        List<AnswerDTO> prevAnswer = getPreviousAnswers(questionList, questionNumber);
        List<Integer> rightIndexesList = getListOfIndexesOfRightAnswers(prevAnswer);
        if(answeredQuestion == null && rightIndexesList.isEmpty()) {
            return true;
        } else if(!rightIndexesList.isEmpty() && answeredQuestion == null) {
            return false;
        } else {
            return answeredQuestion.equals(rightIndexesList);
        }
    }

    private List<Integer> getListOfIndexesOfRightAnswers(List<AnswerDTO> prevAnswer) {
        List<Integer> rightAnswers = new ArrayList<>();
        for (int i = 0; i < prevAnswer.size(); i++) {
            if (prevAnswer.get(i).isCorrect()){
                rightAnswers.add(i);
            }
        }
        return rightAnswers;
    }

    private List<AnswerDTO> getPreviousAnswers(List<QuestionDTO> questionList, int questionNumber) {
        Question question = questionService.getQuestionById(questionList.get(questionNumber - 1).getQuestionId());
        return getAnswersByQuestion(question).stream()
                .map(f -> modelMapper.map(f, AnswerDTO.class))
                .collect(Collectors.toList());
    }

}

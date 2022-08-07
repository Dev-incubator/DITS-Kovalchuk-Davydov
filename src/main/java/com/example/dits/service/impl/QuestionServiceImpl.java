package com.example.dits.service.impl;

import com.example.dits.DAO.AnswerRepository;
import com.example.dits.DAO.QuestionRepository;
import com.example.dits.DAO.TestRepository;
import com.example.dits.dto.AnswerEditModel;
import com.example.dits.dto.QuestionDTO;
import com.example.dits.dto.QuestionEditModel;
import com.example.dits.entity.Answer;
import com.example.dits.entity.Question;
import com.example.dits.entity.Test;
import com.example.dits.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<Question> getQuestionsByTestName(String name){
        return questionRepository.getQuestionsByTestName(name);
    }

    @Transactional
    @Override
    public List<Question> getQuestionsByTest_TestId(int id) {
        return questionRepository.getQuestionsByTest_TestId(id);
    }

    @Transactional
    @Override
    public List<QuestionDTO> getQuestionsByTest(Test test) {
        return questionRepository.getQuestionsByTest(test).stream()
                .map(f -> modelMapper.map(f, QuestionDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Question getQuestionById(int id) {
        return questionRepository.getQuestionByQuestionId(id);
    }

    @Override
    public String getDescriptionFromQuestionList(List<QuestionDTO> questionList, int index) {
        return questionList.get(index).getDescription();
    }

    @Transactional
    @Override
    public void editQuestion(QuestionEditModel questionEditModel) {
        Question question = questionRepository.getQuestionByQuestionId(questionEditModel.getQuestionId());
        question.setDescription(questionEditModel.getQuestionName());
        answerRepository.removeAnswerByQuestion(question);
        List<AnswerEditModel> answerEditModels = questionEditModel.getAnswersData();
        saveAnswers(answerEditModels, question);
    }

    @Transactional
    @Override
    public void addQuestion(QuestionEditModel questionEditModel) {
        Question question = new Question(questionEditModel.getQuestionName(),
                testRepository.getTestByTestId(questionEditModel.getTestId()));
        questionRepository.save(question);
        List<AnswerEditModel> answerEditModels = questionEditModel.getAnswersData();
        saveAnswers(answerEditModels, question);
    }

    @Transactional
    @Override
    public void removeQuestionById(int id) {
        questionRepository.removeQuestionByQuestionId(id);
    }

    private void saveAnswers(List<AnswerEditModel> answerEditModels, Question question) {
         answerEditModels.stream().forEach(x->answerRepository.save(new Answer(
                x.getAnswer(),x.isCorrect(),question)));
    }

    @Transactional
    @Override
    public void addQuestion(String description, int testId) {
        Test test = testRepository.getTestByTestId(testId);
        new Question(description).setTest(test);
    }

    @Transactional
    @Override
    public void editQuestion(String description, int questionId) {
        Question questionByQuestionId = questionRepository.getQuestionByQuestionId(questionId);
        questionByQuestionId.setDescription(description);
    }

    @Transactional
    public void create(Question question) {
        questionRepository.save(question);
    }

    @Transactional
    public void update(Question question, int id) {
        Optional<Question> q = questionRepository.findById(id);
        if(q.isEmpty())
            return;
        else
            questionRepository.save(question);
    }

    @Transactional
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    @Transactional
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Transactional
    public List<Question> findAll() {
        return questionRepository.findAll();
    }


}

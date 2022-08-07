package com.example.dits.service.impl;

import com.example.dits.DAO.StatisticRepository;
import com.example.dits.dto.*;
import com.example.dits.entity.*;
import com.example.dits.service.QuestionService;
import com.example.dits.service.StatisticService;
import com.example.dits.service.TopicService;
import com.example.dits.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository repository;
    private final TopicService topicService;
    private final QuestionService questionService;
    private final UserService userService;

    @Transactional
    public void saveMapOfStat(Map<String, Statistic> map, String endTest) {
        for (Statistic st : map.values()) {
            st.setDate(new Date());
        }
    }

    @Transactional
    @Override
    public List<Statistic> getStatisticsByUser(User user) {
        return repository.getStatisticsByUser(user);
    }

    @Transactional
    @Override
    public List<Statistic> getStatisticByQuestion(Question question) {
        return repository.getStatisticByQuestion(question);
    }

    @Transactional
    @Override
    public void saveListOfStatisticsToDB(List<StatisticDTO> statistics) {
        Date date = new Date();
        List<Statistic> statisticList = statistics.stream().map(this::mapToStatistic).collect(Collectors.toList());
        for (Statistic statistic : statisticList) {
            statistic.setDate(date);
            repository.save(statistic);
        }
    }

    @Transactional
    @Override
    public void create(Statistic statistic) {
        repository.save(statistic);
    }

    @Transactional
    @Override
    public void update(Statistic statistic, int id) {
        Optional<Statistic> st = repository.findById(id);
        if (st.isPresent())
            repository.save(statistic);
    }

    @Transactional
    @Override
    public void delete(Statistic statistic) {
        repository.delete(statistic);
    }

    @Transactional
    @Override
    public void save(Statistic statistic) {
        repository.save(statistic);
    }

    @Transactional
    @Override
    public List<Statistic> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void removeStatisticByUserId(int userId) {
        repository.removeStatisticByUser_UserId(userId);
    }

    @Transactional
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Transactional
    @Override
    public List<TestStatistic> getListOfTestsWithStatisticsByTopic(int topicId) {
        Topic topic = topicService.getTopicByTopicId(topicId);
        return getTestStatistics(topic);
    }

    private List<TestStatistic> getTestStatistics(Topic topic) {
        List<Test> testLists = topic.getTestList();
        List<TestStatistic> testStatistics = new ArrayList<>();

        setTestLists(testLists, testStatistics);
        Collections.sort(testStatistics);
        return testStatistics;
    }

    private void setTestLists(List<Test> testLists, List<TestStatistic> testStatistics) {
        for (Test test : testLists) {

            List<Question> questionList = test.getQuestions();
            List<QuestionStatistic> questionStatistics = new ArrayList<>();
            QuestionStatisticAttempts statisticAttempts = new QuestionStatisticAttempts(0, 0, 0);
            setQuestionStatistics(questionList, questionStatistics, statisticAttempts);
            Collections.sort(questionStatistics);

            int testAverage = calculateTestAverage(statisticAttempts.getTestSumAvg(), questionStatistics.size());
            testStatistics.add(new TestStatistic(test.getName(), statisticAttempts.getNumberOfAttempts(),
                    testAverage, questionStatistics));
        }
    }

    private void setQuestionStatistics(List<Question> questionList, List<QuestionStatistic> questionStatistics,
                                       QuestionStatisticAttempts statisticAttempts) {
        for (Question question : questionList) {

            List<Statistic> statisticList = getStatisticByQuestion(question);
            statisticAttempts.setNumberOfAttempts(statisticList.size());
            int rightAnswers = numberOfRightAnswers(statisticList);
            if (statisticAttempts.getNumberOfAttempts() != 0)
                statisticAttempts.setQuestionAvg(calculateAvg(statisticAttempts.getNumberOfAttempts(), rightAnswers));

            statisticAttempts.setTestSumAvg(statisticAttempts.getTestSumAvg() + statisticAttempts.getQuestionAvg());
            questionStatistics.add(new QuestionStatistic(question.getDescription(),
                    statisticAttempts.getNumberOfAttempts(), statisticAttempts.getQuestionAvg()));
        }
    }

    private int numberOfRightAnswers(List<Statistic> statisticList) {
        int rightAnswer = 0;
        rightAnswer = getRightAnswer(statisticList, rightAnswer);
        return rightAnswer;
    }

    private int getRightAnswer(List<Statistic> statisticList, int rightAnswer) {
        for (Statistic statistic : statisticList) {
            if (statistic.isCorrect())
                rightAnswer++;
        }
        return rightAnswer;
    }

    private int calculateTestAverage(int testSumAvg, int questionStatisticsSize) {
        if (questionStatisticsSize != 0)
            return testSumAvg / questionStatisticsSize;
        else
            return testSumAvg;
    }

    private int calculateAvg(int count, double rightAnswer) {
        return (int) (rightAnswer / count * 100);
    }

    private Statistic mapToStatistic(StatisticDTO statisticDTO) {
        return Statistic.builder()
                .question(questionService.getQuestionById(statisticDTO.getQuestionId()))
                .user(userService.getUserByLogin(statisticDTO.getUsername()))
                .correct(statisticDTO.isCorrect())
                .build();
    }
}

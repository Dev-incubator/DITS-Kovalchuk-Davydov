package com.example.dits.service;

import com.example.dits.dto.StatisticDTO;
import com.example.dits.dto.TestStatistic;
import com.example.dits.entity.*;

import java.util.List;
import java.util.Map;

public interface StatisticService {
    void create(Statistic st);
    void update(Statistic st, int id);
    void delete(Statistic st);
    void save(Statistic st);
    List<Statistic> findAll();
    void saveMapOfStat(Map<String, Statistic> map, String endTest);
    List<Statistic> getStatisticsByUser(User user);
    List<Statistic> getStatisticByQuestion(Question question);
    List<TestStatistic> getListOfTestsWithStatisticsByTopic(int  topicId);
    void saveListOfStatisticsToDB(List<StatisticDTO> statistics);
    void removeStatisticByUserId(int userid);
    void deleteAll();
}

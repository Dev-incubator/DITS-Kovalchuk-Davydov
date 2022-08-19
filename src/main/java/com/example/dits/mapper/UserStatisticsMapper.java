package com.example.dits.mapper;

import com.example.dits.dto.TestStatisticByUser;
import com.example.dits.entity.Question;
import com.example.dits.entity.Statistic;
import com.example.dits.entity.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserStatisticsMapper {
    public List<TestStatisticByUser> statisticToDto(List<Statistic> statistics, List<Test> tests) {
        List<TestStatisticByUser> testStatisticByUsers = new ArrayList<>();

        for (Test test : tests) {
            int correctAns = 0;
            int incorrectAns = 0;

            for (Question question : test.getQuestions()) {
                int id = question.getQuestionId();

                for (Statistic statistic : statistics) {
                    if (statistic.getQuestion().getQuestionId() == id) {
                        if (statistic.isCorrect()) {
                            correctAns++;
                        } else {
                            incorrectAns++;
                        }
                    }
                }
            }

            if (correctAns + incorrectAns != 0) {
                testStatisticByUsers.add(new TestStatisticByUser(test.getName(),
                        ((correctAns + incorrectAns) / test.getQuestions().size()),
                        (correctAns * 100 / (correctAns + incorrectAns))));
            }
        }

        return testStatisticByUsers;
    }
}

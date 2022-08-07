package com.example.dits.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestStatistic implements Comparable<TestStatistic>{
    private String testName;
    private int count;
    private int avgProc;
    private List<QuestionStatistic> questionStatistics;

    public TestStatistic(String testName, int count, int avgProc) {
        this.testName = testName;
        this.count = count;
        this.avgProc = avgProc;
    }

    @Override
    public int compareTo(TestStatistic o) {
        return o.avgProc-this.avgProc;
    }
}

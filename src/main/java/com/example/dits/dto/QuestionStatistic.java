package com.example.dits.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionStatistic implements Comparable<QuestionStatistic>{
    private String questionDescription;
    private int count;
    private int avgProc;

    @Override
    public int compareTo(QuestionStatistic o) {
        return o.getAvgProc()-this.avgProc;
    }
}

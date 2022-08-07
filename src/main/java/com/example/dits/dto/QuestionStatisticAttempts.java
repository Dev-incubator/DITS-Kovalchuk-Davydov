package com.example.dits.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuestionStatisticAttempts {
    private int numberOfAttempts;
    private int questionAvg;
    private int testSumAvg;
}

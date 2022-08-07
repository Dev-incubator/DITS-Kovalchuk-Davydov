package com.example.dits.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class QuestionEditModel {
    private String questionName;
    private int topicId;
    private int questionId;
    private int testId;
    private List<AnswerEditModel> answersData;
}

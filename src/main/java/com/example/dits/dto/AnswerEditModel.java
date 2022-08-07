package com.example.dits.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AnswerEditModel {
    private boolean correct;
    private String answer;
}

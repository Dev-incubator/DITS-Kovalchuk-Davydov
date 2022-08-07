package com.example.dits.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWithAnswersDTO {
    private int questionId;
    private String description;
    private List<AnswerDTO> answerDTOList;
}

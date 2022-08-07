package com.example.dits.dto;

import com.example.dits.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestWithQuestionsDTO {
    private String name;
    private String description;
    private int testId;
    private List<QuestionDTO> questions;
}

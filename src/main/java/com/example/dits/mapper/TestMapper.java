package com.example.dits.mapper;

import com.example.dits.dto.QuestionDTO;
import com.example.dits.dto.TestWithQuestionsDTO;
import com.example.dits.entity.Question;
import com.example.dits.entity.Test;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TestMapper {

    private final ModelMapper modelMapper;

    public TestWithQuestionsDTO convertToTestDTO(Test test){
        List<QuestionDTO> questionDTOList;
        if (test.getQuestions() == null) {
            questionDTOList = new ArrayList<>();
        }
        else {
            questionDTOList = test.getQuestions().stream().map(this::convertToQuestionDTO)
                    .collect(Collectors.toList());
        }
        return TestWithQuestionsDTO.builder()
                .testId(test.getTestId())
                .description(test.getDescription())
                .name(test.getName())
                .questions(questionDTOList)
                .build();
    }

    private QuestionDTO convertToQuestionDTO(Question question){
       return modelMapper.map(question,QuestionDTO.class);
    }

}

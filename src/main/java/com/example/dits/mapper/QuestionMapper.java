package com.example.dits.mapper;

import com.example.dits.dto.AnswerDTO;
import com.example.dits.dto.QuestionWithAnswersDTO;
import com.example.dits.entity.Answer;
import com.example.dits.entity.Question;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

    private final ModelMapper modelMapper;

    public QuestionWithAnswersDTO convertToQuestionWithAnswersDTO(Question question){
        List<AnswerDTO> answerDTOList = question.getAnswers().stream().map(this::convertToAnswerDTO)
                .collect(Collectors.toList());

        return QuestionWithAnswersDTO.builder()
                .questionId(question.getQuestionId())
                .description(question.getDescription())
                .answerDTOList(answerDTOList)
                .build();

    }

    private AnswerDTO convertToAnswerDTO (Answer answer){
        return modelMapper.map(answer,AnswerDTO.class);
    }
}

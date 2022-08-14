package com.example.dits.controllers;

import com.example.dits.dto.AnswerDTO;
import com.example.dits.dto.QuestionDTO;
import com.example.dits.dto.StatisticDTO;
import com.example.dits.dto.UserInfoDTO;
import com.example.dits.service.AnswerService;
import com.example.dits.service.QuestionService;
import com.example.dits.service.StatisticService;
import com.example.dits.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "user", authorities = {"ROLE_USER"})
public class TestPageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StatisticService statisticService;
    @MockBean
    private TestService testService;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private QuestionService questionService;

    @Test
    public void testUserChoseTestPage() throws Exception {
        int testId = 1;
        com.example.dits.entity.Test test = new com.example.dits.entity.Test();
        List<QuestionDTO> questions = new ArrayList<>();
        List<AnswerDTO> answers = new ArrayList<>();
        String description = "Test";

        when(testService.getTestByTestId(testId)).thenReturn(test);
        when(questionService.getQuestionsByTest(test)).thenReturn(questions);
        when(answerService.getAnswersFromQuestionList(questions, 0)).thenReturn(answers);
        when(questionService.getDescriptionFromQuestionList(questions, 0)).thenReturn(description);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/goTest")
                .contentType(MediaType.TEXT_HTML)
                .param("testId", "1")
                .param("theme", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUserNextTestPage() throws Exception {
        List<QuestionDTO> questions = List.of(new QuestionDTO());
        Integer questionNumber = 1;
        UserInfoDTO user = new UserInfoDTO();
        List<Integer> answeredQuestion = new ArrayList<>();
        List<AnswerDTO> answers = List.of(new AnswerDTO());
        String description = "Test";
        List<StatisticDTO> statisticList = new ArrayList<>();

        when(answerService.isRightAnswer(answeredQuestion, questions, questionNumber)).thenReturn(true);
        when(answerService.getAnswersFromQuestionList(questions, questionNumber)).thenReturn(answers);
        when(questionService.getDescriptionFromQuestionList(questions, questionNumber)).thenReturn(description);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/nextTestPage")
                .sessionAttr("questions", questions)
                .sessionAttr("questionNumber", questionNumber)
                .sessionAttr("user", user)
                .sessionAttr("statistics", statisticList))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testResultPage() throws Exception {
        List<QuestionDTO> questions = List.of(new QuestionDTO());
        int questionNumber = questions.size();
        List<Integer> answers = List.of(1);
        UserInfoDTO user = new UserInfoDTO();
        List<StatisticDTO> statisticList = List.of(new StatisticDTO());

        when(answerService.isRightAnswer(answers, questions, questionNumber)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/resultPage")
                .sessionAttr("questions", questions)
                .sessionAttr("questionSize", questionNumber)
                .sessionAttr("user", user)
                .sessionAttr("statistics", statisticList))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

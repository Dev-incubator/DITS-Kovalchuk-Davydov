package com.example.dits.controllers;

import com.example.dits.DAO.QuestionRepository;
import com.example.dits.DAO.TestRepository;
import com.example.dits.DAO.TopicRepository;
import com.example.dits.dto.QuestionEditModel;
import com.example.dits.dto.TopicDTO;
import com.example.dits.entity.Question;
import com.example.dits.entity.Topic;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = "ADMIN")
public class AdminTestControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TestRepository testRepository;

    @MockBean
    TopicRepository topicRepository;

    @MockBean
    QuestionRepository questionRepository;


    @Test
    public void shouldReturnModelAndStatus200() throws Exception {
        mockMvc.perform(get("/admin/testBuilder").with(csrf())
                .contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("topicLists"))
                .andExpect(model().attribute("title", "Test editor"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTopicListAndStatus200AfterAddingTopic() throws Exception {
        TopicDTO topicDTO = new TopicDTO("aa", 1);
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/admin/addTopic").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(topicDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTopicListAndStatus200AfterEditingTopic() throws Exception {
        given(topicRepository.getTopicByTopicId(1)).willReturn(new Topic(1,"a", "a", new ArrayList<>()));

        ObjectMapper objectMapper = new ObjectMapper();
        TopicDTO topicDTO = new TopicDTO("aa", 1);

        mockMvc.perform(put("/admin/editTopic").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(topicDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTopicListAndStatus200AfterRemovingTopic() throws Exception {
        mockMvc.perform(delete("/admin/removeTopic?topicId=1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTopicListAndStatus200() throws Exception {
        mockMvc.perform(get("/admin/getTopics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnQuestionListAndStatus200AfterAddingQuestion() throws Exception {
        Topic topic = new Topic(1,"a", "a", new ArrayList<>());
        com.example.dits.entity.Test test = new com.example.dits.entity.Test(1, "a", "a", topic, new ArrayList<>());

        given(topicRepository.getTopicByTopicId(1)).willReturn(topic);
        given(testRepository.getTestByTestId(1)).willReturn(test);

        QuestionEditModel questionEditModel = new QuestionEditModel("asa", 1, 1, 1, new ArrayList<>());
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/admin/addQuestion").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(questionEditModel)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnQuestionListAndStatus200AfterEditingQuestion() throws Exception {
        Topic topic = new Topic(1,"a", "a", new ArrayList<>());
        com.example.dits.entity.Test test = new com.example.dits.entity.Test(1, "a", "a", topic,
                new ArrayList<>());

        given(topicRepository.getTopicByTopicId(1)).willReturn(topic);
        given(testRepository.getTestByTestId(1)).willReturn(test);
        given(questionRepository.getQuestionByQuestionId(1)).willReturn(new Question(1, "a",
                new ArrayList<>(), test, new ArrayList<>()));

        ObjectMapper objectMapper = new ObjectMapper();
        QuestionEditModel questionEditModel = new QuestionEditModel("asa", 1, 1, 1,
                new ArrayList<>());

        mockMvc.perform(put("/admin/editQuestionAnswers").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(questionEditModel)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnQuestionListAndStatus200AfterRemovingQuestion() throws Exception {
        Topic topic = new Topic(1,"a", "a", new ArrayList<>());
        given(topicRepository.getTopicByTopicId(1)).willReturn(topic);

        mockMvc.perform(delete("/admin/removeQuestion?questionId=1&topicId=1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}

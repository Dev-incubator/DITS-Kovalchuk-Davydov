package com.example.dits.controllers;

import com.example.dits.DAO.StatisticRepository;
import com.example.dits.DAO.TopicRepository;
import com.example.dits.DAO.UserRepository;
import com.example.dits.entity.Role;
import com.example.dits.entity.Topic;
import com.example.dits.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = "ADMIN")
public class AdminStatisticControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    StatisticRepository statisticRepository;
    @MockBean
    TopicRepository topicRepository;
    @MockBean
    UserRepository userRepository;

    @Test
    public void shouldReturnModelAndStatus200() throws Exception {
        mockMvc.perform(get("/admin/adminStatistic").with(csrf())
                        .contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("topicList"))
                .andExpect(model().attribute("title", "Statistic"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatisticListAndStatus200() throws Exception {
        Topic topic = new Topic(1 , "aa", "aa", new ArrayList<>());
        given(topicRepository.getTopicByTopicId(1)).willReturn(topic);

        mockMvc.perform(get("/admin/getTestsStatistic?id=1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnUserStatisticListAndStatus200() throws Exception {
        Role userRole = new Role(1,"ROLE_USER", new ArrayList<>());
        User user = new User();
        user.setRole(userRole);
        user.setStatistics(new ArrayList<>());
        Topic topic = new Topic(1 , "aa", "aa", new ArrayList<>());

        given(topicRepository.getTopicByTopicId(1)).willReturn(topic);
        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(statisticRepository.getStatisticsByUser(user)).willReturn(new ArrayList<>());

        mockMvc.perform(get("/admin/getTestsStatistic?id=1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatus200AfterDeletingStatistic() throws Exception {
        mockMvc.perform(get("/admin/adminStatistic/removeStatistic/byId?id=1").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatus3xxAfterDeletingAllStatistic() throws Exception {
        mockMvc.perform(get("/admin/adminStatistic/removeStatistic/all").with(csrf()))
                .andExpect(status().is3xxRedirection());
    }
}

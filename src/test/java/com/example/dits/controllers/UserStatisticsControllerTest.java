package com.example.dits.controllers;

import com.example.dits.dto.UserInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class UserStatisticsControllerTest {
    @Autowired
    MockMvc mockMvc;

    @WithMockUser(username = "user",
            authorities = {"ROLE_USER"})

    @Test
    public void testUserStatisticsPage() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO();

        mockMvc.perform(MockMvcRequestBuilders.get("/user/statistics").sessionAttr("user", userInfoDTO))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

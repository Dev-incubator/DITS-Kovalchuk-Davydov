package com.example.dits.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChooseTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "user",
            authorities = {"ROLE_USER"})

    @Test
    public void testUserChoseTestPage() throws Exception {
        mockMvc.perform(get("/user/chooseTest"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user",
            authorities = {"ROLE_USER"})

    @Test
    public void testUserThemePage() throws Exception {
        mockMvc.perform(get("/user/chooseTheme"))
                .andExpect(status().isOk());
    }
}

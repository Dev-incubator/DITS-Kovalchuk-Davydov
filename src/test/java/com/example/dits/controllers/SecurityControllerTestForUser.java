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
public class SecurityControllerTestForUser {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(username = "user",
            authorities = {"ROLE_USER"})

    @Test
    public void testAuthenticatedOnUser() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }
}

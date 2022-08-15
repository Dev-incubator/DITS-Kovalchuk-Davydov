package com.example.dits.controllers;

import com.example.dits.DAO.RoleRepository;
import com.example.dits.DAO.UserRepository;
import com.example.dits.dto.UserInfoDTO;
import com.example.dits.entity.Role;
import com.example.dits.entity.User;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = "ADMIN")
public class AdminUserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @Test
    public void shouldReturnUserListAndStatus200AfterDeletingUser() throws Exception {
        mockMvc.perform(delete("/admin/deleteUser?userId=160")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnUserListAndStatus200AfterAddingUser() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO(160, "aaa", "aaa", "aaa", "ROLE_USER", "aaaa");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/admin/addUser").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userInfoDTO)))
        .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnUserListAndStatus200AfterEditingUser() throws Exception {
        Role userRole = new Role(1,"ROLE_USER", new ArrayList<>());
        User user = new User();
        user.setUserId(160);
        user.setFirstName("a");
        user.setLastName("a");
        user.setRole(userRole);
        user.setLogin("aaaaa");
        user.setPassword("aaaaa");

        given(userRepository.getById(160)).willReturn(user);
        given(roleRepository.getRoleByRoleName("ROLE_USER")).willReturn(new Role(1, "USER", new ArrayList<>()));

        UserInfoDTO userInfoDTO = new UserInfoDTO(160, "aaa", "aaa", "aaa", "ROLE_USER", "aaaa");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/admin/editUser").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInfoDTO)))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldReturnUserListAndStatus200() throws Exception {
        mockMvc.perform(get("/admin/getUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

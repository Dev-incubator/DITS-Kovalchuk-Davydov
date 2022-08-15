package com.example.dits.service.impl;

import com.example.dits.DAO.UserRepository;
import com.example.dits.entity.Role;
import com.example.dits.entity.User;
import com.example.dits.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    private User user = new User();

    {
        user.setPassword("aa");
        user.setUserId(0);
        user.setFirstName("aa");
        user.setRole(new Role(1, "aa", new ArrayList<>()));
        user.setLogin("aa");
        user.setLastName("aa");
        user.setStatistics(new ArrayList<>());
    }

    @Test
    void shouldInvokeMethodSaveFromRepository() {
        userService.save(user);
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldReturnFromUpdateWhenThereIsNoUserById() {
        given(userRepository.getById(anyInt())).willReturn(user);
        userService.update(user,anyInt());
        verify(userRepository,times(1)).getById(anyInt());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldInvokeSaveOnRepositoryWhenThereIsUserById(){
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
        userService.update(user,anyInt());
        verify(userRepository,times(1)).findById(anyInt());
        verify(userRepository,times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldInvokeDeleteOnRepository(){
        userService.removeUser(user.getUserId());
        verify(userRepository,times(1)).deleteById(user.getUserId());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldInvokeFindAllOnRepository(){
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        userService.getAllUsers();
        verify(userRepository,times(1)).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldInvokeGetUserByLoginInRepository(){
        String anyString = anyString();
        userService.getUserByLogin(anyString);
        verify(userRepository,times(1)).getUserByLogin(anyString);
        verifyNoMoreInteractions(userRepository);
    }
}
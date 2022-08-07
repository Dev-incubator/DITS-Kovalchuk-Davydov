package com.example.dits.service.impl;

import com.example.dits.DAO.StatisticRepository;
import com.example.dits.DAO.TestRepository;
import com.example.dits.entity.Statistic;
import com.example.dits.entity.Topic;
import com.example.dits.service.StatisticService;
import com.example.dits.service.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
@SpringBootTest
class TestServiceImplTest {

    @Autowired
    private TestService service;
    @MockBean
    private TestRepository repository;

    private com.example.dits.entity.Test test;

    @Test
    void shouldInvokeMethodSaveFromRepository() {;
        service.save(test);
        verify(repository, times(1)).save(test);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnFromUpdateWhenThereIsNoTestById() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        service.update(test,anyInt());
        verify(repository,times(1)).findById(anyInt());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeSaveOnRepositoryWhenThereIsTestById(){
        when(repository.findById(anyInt())).thenReturn(Optional.of(new com.example.dits.entity.Test()));
        service.update(test,anyInt());
        verify(repository,times(1)).findById(anyInt());
        verify(repository,times(1)).save(test);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeDeleteOnRepository(){
        service.delete(test);
        verify(repository,times(1)).delete(test);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeFindAllOnRepository(){
        service.findAll();
        verify(repository,times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeGetTestsByTopic(){
        Topic anyTopic = any(Topic.class);
        service.getTestsByTopic(anyTopic);
        verify(repository,times(1)).getTestsByTopic(anyTopic);
        verifyNoMoreInteractions(repository);
    }
}
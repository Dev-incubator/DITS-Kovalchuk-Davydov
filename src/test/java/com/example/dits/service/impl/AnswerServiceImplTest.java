package com.example.dits.service.impl;

import com.example.dits.DAO.AnswerRepository;
import com.example.dits.entity.Answer;
import com.example.dits.entity.Question;
import com.example.dits.entity.Topic;
import com.example.dits.service.AnswerService;
import com.example.dits.service.impl.AnswerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class AnswerServiceImplTest {

    @Autowired
    private AnswerService service;
    @MockBean
    private AnswerRepository repository;

    private Answer answer;

    @Test
    void shouldInvokeMethodSaveFromRepository() {;
        service.save(answer);
        verify(repository).save(answer);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnFromUpdateWhenThereIsNoAnswerById() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        service.update(answer,anyInt());
        verify(repository,times(1)).findById(anyInt());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeSaveOnRepositoryWhenThereIsAnswerById(){
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Answer()));
        service.update(answer,anyInt());
        verify(repository,times(1)).findById(anyInt());
        verify(repository,times(1)).save(answer);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeDeleteOnRepository(){
        service.delete(answer);
        verify(repository,times(1)).delete(answer);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeFindAllOnRepository(){
        service.findAll();
        verify(repository,times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }
}
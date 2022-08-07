package com.example.dits.service.impl;

import com.example.dits.DAO.AnswerRepository;

import com.example.dits.DAO.QuestionRepository;
import com.example.dits.entity.Question;
import com.example.dits.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@DataJpaTest
class QuestionServiceImplTest {

    @Autowired
    private QuestionService service;
    @MockBean
    private QuestionRepository repository;

    private Question question;

    @Test
    void shouldInvokeMethodSaveFromRepository() {;
        service.save(question);
        verify(repository, times(1)).save(question);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnFromUpdateWhenThereIsNoQuestionById() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        service.update(question,anyInt());
        verify(repository,times(1)).findById(anyInt());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeSaveOnRepositoryWhenThereIsQuestionById(){
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Question()));
        service.update(question,anyInt());
        verify(repository,times(1)).findById(anyInt());
        verify(repository,times(1)).save(question);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeDeleteOnRepository(){
        service.delete(question);
        verify(repository,times(1)).delete(question);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeFindAllOnRepository(){
        service.findAll();
        verify(repository,times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }
}
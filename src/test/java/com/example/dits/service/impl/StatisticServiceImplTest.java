package com.example.dits.service.impl;

import com.example.dits.DAO.RoleRepository;
import com.example.dits.DAO.StatisticRepository;
import com.example.dits.entity.Role;
import com.example.dits.entity.Statistic;
import com.example.dits.service.RoleService;
import com.example.dits.service.StatisticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class StatisticServiceImplTest {
    @Autowired
    private StatisticService service;
    @MockBean
    private StatisticRepository repository;

    private Statistic statistic;

    @Test
    void shouldInvokeMethodSaveFromRepository() {;
        service.save(statistic);
        verify(repository, times(1)).save(statistic);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldReturnFromUpdateWhenThereIsNoStatisticById() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        service.update(statistic,anyInt());
        verify(repository,times(1)).findById(anyInt());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeSaveOnRepositoryWhenThereIsStatisticById(){
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Statistic()));
        service.update(statistic,anyInt());
        verify(repository,times(1)).findById(anyInt());
        verify(repository,times(1)).save(statistic);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeDeleteOnRepository(){
        service.delete(statistic);
        verify(repository,times(1)).delete(statistic);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void shouldInvokeFindAllOnRepository(){
        service.findAll();
        verify(repository,times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

}
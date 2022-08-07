//package com.example.dits.service.impl;
//
//import com.example.dits.DAO.TopicRepository;
//import com.example.dits.DAO.UserRepository;
//import com.example.dits.entity.Topic;
//import com.example.dits.entity.User;
//import com.example.dits.service.TopicService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class UserServiceImplTest {
//    @Autowired
//    private UserService service;
//    @MockBean
//    private UserRepository repository;
//
//    private User user;
//
//    @Test
//    void shouldInvokeMethodSaveFromRepository() {;
//        service.save(user);
//        verify(repository, times(1)).save(user);
//        verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    void shouldReturnFromUpdateWhenThereIsNoUserById() {
//        when(repository.findById(anyInt())).thenReturn(Optional.empty());
//        service.update(user,anyInt());
//        verify(repository,times(1)).findById(anyInt());
//        verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    void shouldInvokeSaveOnRepositoryWhenThereIsUserById(){
//        when(repository.findById(anyInt())).thenReturn(Optional.of(new User()));
//        service.update(user,anyInt());
//        verify(repository,times(1)).findById(anyInt());
//        verify(repository,times(1)).save(user);
//        verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    void shouldInvokeDeleteOnRepository(){
//        service.delete(user);
//        verify(repository,times(1)).delete(user);
//        verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    void shouldInvokeFindAllOnRepository(){
//        service.findAll();
//        verify(repository,times(1)).findAll();
//        verifyNoMoreInteractions(repository);
//    }
//
//    @Test
//    void shouldInvokeGetUserByLoginInRepository(){
//        String anyString = anyString();
//       service.getUserByLogin(anyString);
//       verify(repository,times(1)).getUserByLogin(anyString);
//       verifyNoMoreInteractions(repository);
//    }
//}
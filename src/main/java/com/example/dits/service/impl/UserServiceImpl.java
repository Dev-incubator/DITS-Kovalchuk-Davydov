package com.example.dits.service.impl;

import com.example.dits.DAO.UserRepository;
import com.example.dits.dto.UserInfoDTO;
import com.example.dits.entity.User;
import com.example.dits.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper modelMapper;

    @Transactional
    public UserInfoDTO getUserInfoByLogin(String login){
        return modelMapper.map(repository.getUserByLogin(login), UserInfoDTO.class);
    }

    @Override
    public User getUserByLogin(String login) {
        return repository.getUserByLogin(login);
    }

    @Override
    public List<UserInfoDTO> getAllUsers() {
        return repository.findAll().stream().map(f -> modelMapper.map(f, UserInfoDTO.class)).collect(Collectors.toList());
    }
}

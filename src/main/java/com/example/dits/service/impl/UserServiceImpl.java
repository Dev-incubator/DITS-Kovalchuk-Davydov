package com.example.dits.service.impl;

import com.example.dits.DAO.UserRepository;
import com.example.dits.dto.UserInfoDTO;
import com.example.dits.entity.User;
import com.example.dits.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(User user) {
        repository.save(user);
    }

    @Transactional
    @Override
    public void update(User user, int id) {
            User userById = repository.getById(id);
            userById.setFirstName(user.getFirstName());
            userById.setLastName(user.getLastName());
            userById.setRole(user.getRole());
            userById.setLogin(user.getLogin());
            userById.setPassword(user.getPassword());
    }

    @Transactional
    @Override
    public void save(User user) {
        if (repository.getUserByLogin(user.getLogin()) == null && user.getRole() != null) {
            repository.save(user);
        }
    }

    @Transactional
    public User getUserByLogin(String login) {
        return repository.getUserByLogin(login);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void removeUser(int userId) {
        repository.deleteById(userId);
    }

    @Transactional
    @Override
    public User getUserById(int userId) {
        return repository.findById(userId).orElse(null);
    }

    @Transactional
    public UserInfoDTO getUserInfoByLogin(String login){
        return modelMapper.map(repository.getUserByLogin(login), UserInfoDTO.class);
    }
}

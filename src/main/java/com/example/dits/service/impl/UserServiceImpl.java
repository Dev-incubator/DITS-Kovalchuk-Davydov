package com.example.dits.service.impl;

import com.example.dits.DAO.UserRepository;
import com.example.dits.entity.User;
import com.example.dits.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
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
        repository.save(user);
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
}

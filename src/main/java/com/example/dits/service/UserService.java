package com.example.dits.service;

import com.example.dits.entity.User;

import java.util.List;

public interface UserService {
    void create(User user);
    void update(User user, int id);
    void save(User user);
    User getUserByLogin(String login);
    List<User> getAllUsers();
    void removeUser(int userId);
    User getUserById(int userId);
}

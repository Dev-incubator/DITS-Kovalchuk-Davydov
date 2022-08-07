package com.example.dits.service;

import com.example.dits.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    void create(Role r);
    void update(Role r, int id);
    void delete(Role r);
    void save(Role r);
    List<Role> findAll();
    List<String> getAllRoles();
    Role getRoleByRoleName(String roleName);
}

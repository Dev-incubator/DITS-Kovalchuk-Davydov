package com.example.dits.service.impl;

import com.example.dits.DAO.RoleRepository;
import com.example.dits.entity.Role;
import com.example.dits.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repo;

    @Transactional
    public void create(Role role) {
        repo.save(role);
    }

    @Transactional
    public void update(Role role, int id) {
        Optional<Role> r = repo.findById(id);
        if(r.isEmpty())
            return;
        else
            repo.save(role);
    }

    @Transactional
    public void delete(Role role) {
        repo.delete(role);
    }
    @Transactional
    public void save(Role role) {
        repo.save(role);
    }

    @Transactional
    public List<Role> findAll() {
        return repo.findAll();
    }

    @Override
    public List<String> getAllRoles() {
        List<Role> rolesList = repo.findAll();
        List<String> roles = new ArrayList<>();
        for (Role role:rolesList){
            roles.add(role.getRoleName().replaceAll("ROLE_",""));
        }
        return roles;
    }

    @Transactional
    @Override
    public Role getRoleByRoleName(String roleName) {
        return repo.getRoleByRoleName(roleName);
    }

}

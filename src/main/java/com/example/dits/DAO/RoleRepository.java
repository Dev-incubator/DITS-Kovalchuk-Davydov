package com.example.dits.DAO;

import com.example.dits.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer>, CrudRepository<Role,Integer> {
    Role getRoleByRoleName(String roleName);
    List<Role> findAll();
}

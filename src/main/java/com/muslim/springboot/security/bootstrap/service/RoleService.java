package com.muslim.springboot.security.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.muslim.springboot.security.bootstrap.model.Role;
import com.muslim.springboot.security.bootstrap.repository.RoleRepository;

import java.util.List;
@Service
public class RoleService {

    public final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }
    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    public List<Role> getByName(String name) {
        return repository.findRoleByName(name);
    }
}

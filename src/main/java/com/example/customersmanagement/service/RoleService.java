package com.example.customersmanagement.service;

import com.example.customersmanagement.entity.Role;
import com.example.customersmanagement.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role createRole(String name, String description) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        return roleRepository.save(role);
    }

    public void createDefaultRoles() {
        if (!roleRepository.existsByName("ADMIN")) {
            createRole("ADMIN", "Administrator role with full access");
        }
        if (!roleRepository.existsByName("USER")) {
            createRole("USER", "Standard user role");
        }
    }
}

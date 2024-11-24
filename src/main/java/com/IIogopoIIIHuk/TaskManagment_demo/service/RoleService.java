package com.IIogopoIIIHuk.TaskManagment_demo.service;

import com.IIogopoIIIHuk.TaskManagment_demo.repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.IIogopoIIIHuk.TaskManagment_demo.entity.Role;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole(){
        return roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NoSuchElementException("Role not found in database"));
    }
}

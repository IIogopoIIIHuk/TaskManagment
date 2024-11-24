package com.IIogopoIIIHuk.TaskManagment_demo.repo;

import com.IIogopoIIIHuk.TaskManagment_demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}

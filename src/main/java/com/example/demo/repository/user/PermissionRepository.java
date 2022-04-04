package com.example.demo.repository.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.user.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
	List<Permission> findByNameContaining(String name);
	Page<Permission> findByNameContaining(String name, Pageable pageable);
}

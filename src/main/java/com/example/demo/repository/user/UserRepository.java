package com.example.demo.repository.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long>{
	List<User> findByUsernameContaining(String username);
	Optional<User> findByUsernameIgnoreCase(String username);
	Page<User> findByUsernameContaining(String username, Pageable pageable);
}

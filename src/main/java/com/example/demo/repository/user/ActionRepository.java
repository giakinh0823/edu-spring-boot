package com.example.demo.repository.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.user.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long>{
	List<Action> findByNameContaining(String name);
	Page<Action> findByNameContaining(String name, Pageable pageable);
}

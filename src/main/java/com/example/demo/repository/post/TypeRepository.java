package com.example.demo.repository.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.post.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
	List<Type> findByNameContaining(String name);
	Page<Type> findByNameContaining(String name, Pageable pageable);
}

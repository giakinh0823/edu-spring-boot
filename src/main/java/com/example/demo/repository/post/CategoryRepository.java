package com.example.demo.repository.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.post.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	List<Category> findByNameContaining(String name);
	@Query(value="SELECT * FROM [category] WHERE [slug] LIKE %?1%",  nativeQuery = true)
	List<Category> findBySlugContaining(String slug);
	Page<Category> findByNameContaining(String name, Pageable pageable);
}

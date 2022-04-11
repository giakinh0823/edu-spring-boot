package com.example.demo.repository.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.post.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
	List<Chapter> findByNameContaining(String name);

	@Query(value = "SELECT * FROM [chapter] WHERE [slug] LIKE %?1%", nativeQuery = true)
	List<Chapter> findBySlugContaining(String slug);
	Page<Chapter> findByNameContaining(String name, Pageable pageable);
	Page<Chapter> findByNameContainingAndCategoryId(String name, Long categoryId, Pageable pageable);
	Page<Chapter> findByCategoryId(Long categoryId, Pageable pageable);
}

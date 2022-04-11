package com.example.demo.repository.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.post.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>{
	List<Lesson> findByNameContaining(String name);
	@Query(value="SELECT * FROM [lesson] WHERE [slug] LIKE %?1%",  nativeQuery = true)
	List<Lesson> findBySlugContaining(String slug);
	Page<Lesson> findByNameContaining(String name, Pageable pageable);
	Page<Lesson> findByNameContainingAndChapterId(String name, Long chapterId, Pageable pageable);
	Page<Lesson> findByChapterId(Long chapterId, Pageable pageable);
}

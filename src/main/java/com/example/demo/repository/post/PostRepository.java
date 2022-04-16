package com.example.demo.repository.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.post.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	@Query(value = "SELECT * FROM [post] WHERE [slug] LIKE %?1%", nativeQuery = true)
	List<Post> findBySlugContaining(String slug);

	Page<Post> findByTitleContaining(String title, Pageable pageable);

	Page<Post> findByTitleContainingAndLessonId(String title, Long lessonId, Pageable pageable);

	Page<Post> findByLessonId(Long lessonId, Pageable pageable);
	
	Post findByLessonId(Long lessonId);
}

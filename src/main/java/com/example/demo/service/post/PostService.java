package com.example.demo.service.post;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.example.demo.domain.post.Post;

public interface PostService {

	<S extends Post> List<S> findAll(Example<S> example, Sort sort);

	<S extends Post> List<S> findAll(Example<S> example);

	Post getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Post> entities);

	Post getOne(Long id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	<S extends Post, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	void delete(Post entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteById(Long id);

	<S extends Post> boolean exists(Example<S> example);

	long count();

	void deleteAllInBatch(Iterable<Post> entities);

	<S extends Post> long count(Example<S> example);

	void deleteInBatch(Iterable<Post> entities);

	<S extends Post> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Post> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Post> S saveAndFlush(S entity);

	void flush();

	<S extends Post> List<S> saveAll(Iterable<S> entities);

	Optional<Post> findById(Long id);

	List<Post> findAllById(Iterable<Long> ids);

	List<Post> findAll(Sort sort);

	Page<Post> findAll(Pageable pageable);

	List<Post> findAll();

	<S extends Post> Optional<S> findOne(Example<S> example);

	<S extends Post> S save(S entity);

	Page<Post> findByTitleContaining(String title, Pageable pageable);

	List<Post> findBySlugContaining(String slug);

	Page<Post> findByLessonId(Long lessonId, Pageable pageable);

	Page<Post> findByTitleContainingAndLessonId(String title, Long lessonId, Pageable pageable);

	Post findByLessonId(Long lessonId);

}

package com.example.demo.service.post;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.example.demo.domain.post.Lesson;

public interface LessonService {

	<S extends Lesson> List<S> findAll(Example<S> example, Sort sort);

	<S extends Lesson> List<S> findAll(Example<S> example);

	Lesson getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Lesson> entities);

	Lesson getOne(Long id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	<S extends Lesson, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	void delete(Lesson entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteById(Long id);

	<S extends Lesson> boolean exists(Example<S> example);

	long count();

	void deleteAllInBatch(Iterable<Lesson> entities);

	<S extends Lesson> long count(Example<S> example);

	void deleteInBatch(Iterable<Lesson> entities);

	<S extends Lesson> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Lesson> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Lesson> S saveAndFlush(S entity);

	void flush();

	<S extends Lesson> List<S> saveAll(Iterable<S> entities);

	Optional<Lesson> findById(Long id);

	List<Lesson> findAllById(Iterable<Long> ids);

	List<Lesson> findAll(Sort sort);

	Page<Lesson> findAll(Pageable pageable);

	List<Lesson> findAll();

	<S extends Lesson> Optional<S> findOne(Example<S> example);

	<S extends Lesson> S save(S entity);

	Page<Lesson> findByNameContaining(String name, Pageable pageable);

	List<Lesson> findBySlugContaining(String slug);

	List<Lesson> findByNameContaining(String name);

	Page<Lesson> findByChapterId(Long chapterId, Pageable pageable);

	Page<Lesson> findByNameContainingAndChapterId(String name, Long chapterId, Pageable pageable);

}

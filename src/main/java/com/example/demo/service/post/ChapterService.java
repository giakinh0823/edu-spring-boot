package com.example.demo.service.post;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.example.demo.domain.post.Chapter;

public interface ChapterService {

	<S extends Chapter> List<S> findAll(Example<S> example, Sort sort);

	<S extends Chapter> List<S> findAll(Example<S> example);

	Chapter getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Chapter> entities);

	Chapter getOne(Long id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	<S extends Chapter, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	void delete(Chapter entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteById(Long id);

	<S extends Chapter> boolean exists(Example<S> example);

	long count();

	void deleteAllInBatch(Iterable<Chapter> entities);

	<S extends Chapter> long count(Example<S> example);

	void deleteInBatch(Iterable<Chapter> entities);

	<S extends Chapter> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Chapter> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Chapter> S saveAndFlush(S entity);

	void flush();

	<S extends Chapter> List<S> saveAll(Iterable<S> entities);

	Optional<Chapter> findById(Long id);

	List<Chapter> findAllById(Iterable<Long> ids);

	List<Chapter> findAll(Sort sort);

	Page<Chapter> findAll(Pageable pageable);

	List<Chapter> findAll();

	<S extends Chapter> Optional<S> findOne(Example<S> example);

	<S extends Chapter> S save(S entity);

	Page<Chapter> findByNameContaining(String name, Pageable pageable);

	List<Chapter> findBySlugContaining(String slug);

	List<Chapter> findByNameContaining(String name);

	Page<Chapter> findByNameContainingAndCategoryId(String name, Long categoryId,Pageable pageable);

	Page<Chapter> findAllByCategoryId(Long categoryId, Pageable pageable);

}

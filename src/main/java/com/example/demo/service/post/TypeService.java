package com.example.demo.service.post;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.example.demo.domain.post.Type;

public interface TypeService {

	<S extends Type> List<S> findAll(Example<S> example, Sort sort);

	<S extends Type> List<S> findAll(Example<S> example);

	Type getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Type> entities);

	Type getOne(Long id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	<S extends Type, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	void delete(Type entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteById(Long id);

	<S extends Type> boolean exists(Example<S> example);

	long count();

	void deleteAllInBatch(Iterable<Type> entities);

	<S extends Type> long count(Example<S> example);

	void deleteInBatch(Iterable<Type> entities);

	<S extends Type> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Type> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Type> S saveAndFlush(S entity);

	void flush();

	<S extends Type> List<S> saveAll(Iterable<S> entities);

	Optional<Type> findById(Long id);

	List<Type> findAllById(Iterable<Long> ids);

	List<Type> findAll(Sort sort);

	Page<Type> findAll(Pageable pageable);

	List<Type> findAll();

	<S extends Type> Optional<S> findOne(Example<S> example);

	<S extends Type> S save(S entity);

	Page<Type> findByNameContaining(String name, Pageable pageable);

	List<Type> findByNameContaining(String name);

}

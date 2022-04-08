package com.example.demo.service.post;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.example.demo.domain.post.Group;

public interface GroupService {

	<S extends Group> List<S> findAll(Example<S> example, Sort sort);

	<S extends Group> List<S> findAll(Example<S> example);

	Group getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Group> entities);

	Group getOne(Long id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	<S extends Group, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	void delete(Group entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteById(Long id);

	<S extends Group> boolean exists(Example<S> example);

	long count();

	void deleteAllInBatch(Iterable<Group> entities);

	<S extends Group> long count(Example<S> example);

	void deleteInBatch(Iterable<Group> entities);

	<S extends Group> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Group> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Group> S saveAndFlush(S entity);

	void flush();

	<S extends Group> List<S> saveAll(Iterable<S> entities);

	Optional<Group> findById(Long id);

	List<Group> findAllById(Iterable<Long> ids);

	List<Group> findAll(Sort sort);

	Page<Group> findAll(Pageable pageable);

	List<Group> findAll();

	<S extends Group> Optional<S> findOne(Example<S> example);

	<S extends Group> S save(S entity);

	Page<Group> findByNameContaining(String name, Pageable pageable);

	List<Group> findByNameContaining(String name);

	List<Group> findBySlugContaining(String slug);

}

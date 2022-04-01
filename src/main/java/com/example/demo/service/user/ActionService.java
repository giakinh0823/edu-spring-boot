package com.example.demo.service.user;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.example.demo.domain.user.Action;

public interface ActionService {

	<S extends Action> List<S> findAll(Example<S> example, Sort sort);

	<S extends Action> List<S> findAll(Example<S> example);

	Action getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Action> entities);

	Action getOne(Long id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	<S extends Action, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	void delete(Action entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteById(Long id);

	<S extends Action> boolean exists(Example<S> example);

	long count();

	void deleteAllInBatch(Iterable<Action> entities);

	<S extends Action> long count(Example<S> example);

	void deleteInBatch(Iterable<Action> entities);

	<S extends Action> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Action> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Action> S saveAndFlush(S entity);

	void flush();

	<S extends Action> List<S> saveAll(Iterable<S> entities);

	Optional<Action> findById(Long id);

	List<Action> findAllById(Iterable<Long> ids);

	List<Action> findAll(Sort sort);

	Page<Action> findAll(Pageable pageable);

	List<Action> findAll();

	<S extends Action> Optional<S> findOne(Example<S> example);

	<S extends Action> S save(S entity);

	Page<Action> findByNameContaining(String name, Pageable pageable);

	List<Action> findByNameContaining(String name);

}

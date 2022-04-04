package com.example.demo.service.user;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.example.demo.domain.user.Permission;

public interface PermissionService {

	<S extends Permission> List<S> findAll(Example<S> example, Sort sort);

	<S extends Permission> List<S> findAll(Example<S> example);

	Permission getById(Long id);

	void deleteAll();

	void deleteAll(Iterable<? extends Permission> entities);

	Permission getOne(Long id);

	void deleteAllInBatch();

	void deleteAllById(Iterable<? extends Long> ids);

	<S extends Permission, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	void delete(Permission entity);

	void deleteAllByIdInBatch(Iterable<Long> ids);

	void deleteById(Long id);

	<S extends Permission> boolean exists(Example<S> example);

	long count();

	void deleteAllInBatch(Iterable<Permission> entities);

	<S extends Permission> long count(Example<S> example);

	void deleteInBatch(Iterable<Permission> entities);

	<S extends Permission> Page<S> findAll(Example<S> example, Pageable pageable);

	<S extends Permission> List<S> saveAllAndFlush(Iterable<S> entities);

	boolean existsById(Long id);

	<S extends Permission> S saveAndFlush(S entity);

	void flush();

	<S extends Permission> List<S> saveAll(Iterable<S> entities);

	Optional<Permission> findById(Long id);

	List<Permission> findAllById(Iterable<Long> ids);

	List<Permission> findAll(Sort sort);

	Page<Permission> findAll(Pageable pageable);

	List<Permission> findAll();

	<S extends Permission> Optional<S> findOne(Example<S> example);

	<S extends Permission> S save(S entity);

	Page<Permission> findByNameContaining(String name, Pageable pageable);

	List<Permission> findByNameContaining(String name);

}

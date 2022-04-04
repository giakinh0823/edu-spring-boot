package com.example.demo.service.user.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.example.demo.domain.user.Permission;
import com.example.demo.repository.user.PermissionRepository;
import com.example.demo.service.user.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public List<Permission> findByNameContaining(String name) {
		return permissionRepository.findByNameContaining(name);
	}
	
	@Override
	public Page<Permission> findByNameContaining(String name, Pageable pageable) {
		return permissionRepository.findByNameContaining(name, pageable);
	}

	@Override
	public <S extends Permission> S save(S entity) {
		return permissionRepository.save(entity);
	}

	@Override
	public <S extends Permission> Optional<S> findOne(Example<S> example) {
		return permissionRepository.findOne(example);
	}

	@Override
	public List<Permission> findAll() {
		return permissionRepository.findAll();
	}

	@Override
	public Page<Permission> findAll(Pageable pageable) {
		return permissionRepository.findAll(pageable);
	}

	@Override
	public List<Permission> findAll(Sort sort) {
		return permissionRepository.findAll(sort);
	}

	@Override
	public List<Permission> findAllById(Iterable<Long> ids) {
		return permissionRepository.findAllById(ids);
	}

	@Override
	public Optional<Permission> findById(Long id) {
		return permissionRepository.findById(id);
	}

	@Override
	public <S extends Permission> List<S> saveAll(Iterable<S> entities) {
		return permissionRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		permissionRepository.flush();
	}

	@Override
	public <S extends Permission> S saveAndFlush(S entity) {
		return permissionRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return permissionRepository.existsById(id);
	}

	@Override
	public <S extends Permission> List<S> saveAllAndFlush(Iterable<S> entities) {
		return permissionRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Permission> Page<S> findAll(Example<S> example, Pageable pageable) {
		return permissionRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Permission> entities) {
		permissionRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Permission> long count(Example<S> example) {
		return permissionRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Permission> entities) {
		permissionRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return permissionRepository.count();
	}

	@Override
	public <S extends Permission> boolean exists(Example<S> example) {
		return permissionRepository.exists(example);
	}

	@Override
	public void deleteById(Long id) {
		permissionRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		permissionRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Permission entity) {
		permissionRepository.delete(entity);
	}

	@Override
	public <S extends Permission, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return permissionRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		permissionRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		permissionRepository.deleteAllInBatch();
	}

	@Override
	public Permission getOne(Long id) {
		return permissionRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Permission> entities) {
		permissionRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		permissionRepository.deleteAll();
	}

	@Override
	public Permission getById(Long id) {
		return permissionRepository.getById(id);
	}

	@Override
	public <S extends Permission> List<S> findAll(Example<S> example) {
		return permissionRepository.findAll(example);
	}

	@Override
	public <S extends Permission> List<S> findAll(Example<S> example, Sort sort) {
		return permissionRepository.findAll(example, sort);
	}
	
	
}

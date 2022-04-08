package com.example.demo.service.post.impl;

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

import com.example.demo.service.post.TypeService;
import com.example.demo.domain.post.Type;
import com.example.demo.repository.post.TypeRepository;

@Service
public class TypeServiceImpl implements TypeService {
	@Autowired
	private TypeRepository typeRepository;

	@Override
	public List<Type> findByNameContaining(String name) {
		return typeRepository.findByNameContaining(name);
	}

	@Override
	public Page<Type> findByNameContaining(String name, Pageable pageable) {
		return typeRepository.findByNameContaining(name, pageable);
	}

	@Override
	public <S extends Type> S save(S entity) {
		return typeRepository.save(entity);
	}

	@Override
	public <S extends Type> Optional<S> findOne(Example<S> example) {
		return typeRepository.findOne(example);
	}

	@Override
	public List<Type> findAll() {
		return typeRepository.findAll();
	}

	@Override
	public Page<Type> findAll(Pageable pageable) {
		return typeRepository.findAll(pageable);
	}

	@Override
	public List<Type> findAll(Sort sort) {
		return typeRepository.findAll(sort);
	}

	@Override
	public List<Type> findAllById(Iterable<Long> ids) {
		return typeRepository.findAllById(ids);
	}

	@Override
	public Optional<Type> findById(Long id) {
		return typeRepository.findById(id);
	}

	@Override
	public <S extends Type> List<S> saveAll(Iterable<S> entities) {
		return typeRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		typeRepository.flush();
	}

	@Override
	public <S extends Type> S saveAndFlush(S entity) {
		return typeRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return typeRepository.existsById(id);
	}

	@Override
	public <S extends Type> List<S> saveAllAndFlush(Iterable<S> entities) {
		return typeRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Type> Page<S> findAll(Example<S> example, Pageable pageable) {
		return typeRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Type> entities) {
		typeRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Type> long count(Example<S> example) {
		return typeRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Type> entities) {
		typeRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return typeRepository.count();
	}

	@Override
	public <S extends Type> boolean exists(Example<S> example) {
		return typeRepository.exists(example);
	}

	@Override
	public void deleteById(Long id) {
		typeRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		typeRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Type entity) {
		typeRepository.delete(entity);
	}

	@Override
	public <S extends Type, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return typeRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		typeRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		typeRepository.deleteAllInBatch();
	}

	@Override
	public Type getOne(Long id) {
		return typeRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Type> entities) {
		typeRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		typeRepository.deleteAll();
	}

	@Override
	public Type getById(Long id) {
		return typeRepository.getById(id);
	}

	@Override
	public <S extends Type> List<S> findAll(Example<S> example) {
		return typeRepository.findAll(example);
	}

	@Override
	public <S extends Type> List<S> findAll(Example<S> example, Sort sort) {
		return typeRepository.findAll(example, sort);
	}
	
	
	
}

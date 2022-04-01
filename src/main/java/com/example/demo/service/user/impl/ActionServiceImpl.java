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

import com.example.demo.domain.user.Action;
import com.example.demo.repository.user.ActionRepository;
import com.example.demo.service.user.ActionService;

@Service
public class ActionServiceImpl implements ActionService{
	@Autowired
	private ActionRepository actionRepository;

	@Override
	public List<Action> findByNameContaining(String name) {
		return actionRepository.findByNameContaining(name);
	}

	@Override
	public Page<Action> findByNameContaining(String name, Pageable pageable) {
		return actionRepository.findByNameContaining(name, pageable);
	}

	@Override
	public <S extends Action> S save(S entity) {
		return actionRepository.save(entity);
	}

	@Override
	public <S extends Action> Optional<S> findOne(Example<S> example) {
		return actionRepository.findOne(example);
	}

	@Override
	public List<Action> findAll() {
		return actionRepository.findAll();
	}

	@Override
	public Page<Action> findAll(Pageable pageable) {
		return actionRepository.findAll(pageable);
	}

	@Override
	public List<Action> findAll(Sort sort) {
		return actionRepository.findAll(sort);
	}

	@Override
	public List<Action> findAllById(Iterable<Long> ids) {
		return actionRepository.findAllById(ids);
	}

	@Override
	public Optional<Action> findById(Long id) {
		return actionRepository.findById(id);
	}

	@Override
	public <S extends Action> List<S> saveAll(Iterable<S> entities) {
		return actionRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		actionRepository.flush();
	}

	@Override
	public <S extends Action> S saveAndFlush(S entity) {
		return actionRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return actionRepository.existsById(id);
	}

	@Override
	public <S extends Action> List<S> saveAllAndFlush(Iterable<S> entities) {
		return actionRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Action> Page<S> findAll(Example<S> example, Pageable pageable) {
		return actionRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Action> entities) {
		actionRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Action> long count(Example<S> example) {
		return actionRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Action> entities) {
		actionRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return actionRepository.count();
	}

	@Override
	public <S extends Action> boolean exists(Example<S> example) {
		return actionRepository.exists(example);
	}

	@Override
	public void deleteById(Long id) {
		actionRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		actionRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Action entity) {
		actionRepository.delete(entity);
	}

	@Override
	public <S extends Action, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return actionRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		actionRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		actionRepository.deleteAllInBatch();
	}

	@Override
	public Action getOne(Long id) {
		return actionRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Action> entities) {
		actionRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		actionRepository.deleteAll();
	}

	@Override
	public Action getById(Long id) {
		return actionRepository.getById(id);
	}

	@Override
	public <S extends Action> List<S> findAll(Example<S> example) {
		return actionRepository.findAll(example);
	}

	@Override
	public <S extends Action> List<S> findAll(Example<S> example, Sort sort) {
		return actionRepository.findAll(example, sort);
	}
	
	
	
}

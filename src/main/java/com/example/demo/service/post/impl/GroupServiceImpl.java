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

import com.example.demo.domain.post.Group;
import com.example.demo.repository.post.GroupRepository;
import com.example.demo.service.post.GroupService;
import com.example.demo.utils.SlugUtils;

@Service
public class GroupServiceImpl implements GroupService{
	
	@Autowired
	private GroupRepository groupRepository;
	

	@Override
	public List<Group> findBySlugContaining(String slug) {
		return groupRepository.findBySlugContaining(slug);
	}

	@Override
	public List<Group> findByNameContaining(String name) {
		return groupRepository.findByNameContaining(name);
	}

	@Override
	public Page<Group> findByNameContaining(String name, Pageable pageable) {
		return groupRepository.findByNameContaining(name, pageable);
	}

	@Override
	public <S extends Group> S save(S entity) {
		if(entity.getSlug()==null || entity.getSlug().isEmpty() 
				|| !entity.getSlug().replaceFirst("(?s)"+"-[0-9]"+"(?!.*?"+"-[0-9]"+")", "").equalsIgnoreCase(SlugUtils.toSlug(entity.getName()))) {
			try {
				entity.setSlug(SlugUtils.toSlug(entity.getName()));
				return groupRepository.save(entity);
			} catch (Exception e) {
				String slug = SlugUtils.toSlug(entity.getName());
				List<Group> groups = findBySlugContaining(slug);
				int count = groups.size();
				System.out.print(count);
				entity.setSlug(SlugUtils.toSlug(entity.getName())+"-"+count);
				return groupRepository.save(entity);
				// TODO: handle exception
			}
		}
		return groupRepository.save(entity);
	}

	@Override
	public <S extends Group> Optional<S> findOne(Example<S> example) {
		return groupRepository.findOne(example);
	}

	@Override
	public List<Group> findAll() {
		return groupRepository.findAll();
	}

	@Override
	public Page<Group> findAll(Pageable pageable) {
		return groupRepository.findAll(pageable);
	}

	@Override
	public List<Group> findAll(Sort sort) {
		return groupRepository.findAll(sort);
	}

	@Override
	public List<Group> findAllById(Iterable<Long> ids) {
		return groupRepository.findAllById(ids);
	}

	@Override
	public Optional<Group> findById(Long id) {
		return groupRepository.findById(id);
	}

	@Override
	public <S extends Group> List<S> saveAll(Iterable<S> entities) {
		return groupRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		groupRepository.flush();
	}

	@Override
	public <S extends Group> S saveAndFlush(S entity) {
		return groupRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return groupRepository.existsById(id);
	}

	@Override
	public <S extends Group> List<S> saveAllAndFlush(Iterable<S> entities) {
		return groupRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Group> Page<S> findAll(Example<S> example, Pageable pageable) {
		return groupRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Group> entities) {
		groupRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Group> long count(Example<S> example) {
		return groupRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Group> entities) {
		groupRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return groupRepository.count();
	}

	@Override
	public <S extends Group> boolean exists(Example<S> example) {
		return groupRepository.exists(example);
	}

	@Override
	public void deleteById(Long id) {
		groupRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		groupRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Group entity) {
		groupRepository.delete(entity);
	}

	@Override
	public <S extends Group, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return groupRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		groupRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		groupRepository.deleteAllInBatch();
	}

	@Override
	public Group getOne(Long id) {
		return groupRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Group> entities) {
		groupRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		groupRepository.deleteAll();
	}

	@Override
	public Group getById(Long id) {
		return groupRepository.getById(id);
	}

	@Override
	public <S extends Group> List<S> findAll(Example<S> example) {
		return groupRepository.findAll(example);
	}

	@Override
	public <S extends Group> List<S> findAll(Example<S> example, Sort sort) {
		return groupRepository.findAll(example, sort);
	}
	
	
}

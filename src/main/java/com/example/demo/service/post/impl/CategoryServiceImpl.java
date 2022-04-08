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

import com.example.demo.domain.post.Category;
import com.example.demo.repository.post.CategoryRepository;
import com.example.demo.service.post.CategoryService;
import com.example.demo.utils.SlugUtils;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> findByNameContaining(String name) {
		return categoryRepository.findByNameContaining(name);
	}

	@Override
	public List<Category> findBySlugContaining(String slug) {
		return categoryRepository.findBySlugContaining(slug);
	}

	@Override
	public Page<Category> findByNameContaining(String name, Pageable pageable) {
		return categoryRepository.findByNameContaining(name, pageable);
	}

	@Override
	public <S extends Category> S save(S entity) {
		try {
			String slug = SlugUtils.toSlug(entity.getName());
			if (entity.getId()!= null && slug.replaceFirst("(?s)" + "-[0-9]" + "(?!.*?" + "-[0-9]" + ")", "")
					.equalsIgnoreCase(SlugUtils.toSlug(entity.getName()))) {
				return categoryRepository.save(entity);
			}
			entity.setSlug(SlugUtils.toSlug(entity.getName()));
			return categoryRepository.save(entity);
		} catch (Exception e) {
			String slug = SlugUtils.toSlug(entity.getName());
			List<Category> categories = findBySlugContaining(slug);
			if ((categories.size() == 1 && entity.getId() != null)
					|| (entity.getId() != null && slug.replaceFirst("(?s)" + "-[0-9]" + "(?!.*?" + "-[0-9]" + ")", "")
							.equalsIgnoreCase(SlugUtils.toSlug(entity.getName())))) {
				return categoryRepository.save(entity);
			}
			Long count = categories.get(categories.size() - 1).getId();
			entity.setSlug(SlugUtils.toSlug(entity.getName()) + "-" + count);
			return categoryRepository.save(entity);
			// TODO: handle exception
		}
	}

	@Override
	public <S extends Category> Optional<S> findOne(Example<S> example) {
		return categoryRepository.findOne(example);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public List<Category> findAll(Sort sort) {
		return categoryRepository.findAll(sort);
	}

	@Override
	public List<Category> findAllById(Iterable<Long> ids) {
		return categoryRepository.findAllById(ids);
	}

	@Override
	public Optional<Category> findById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public <S extends Category> List<S> saveAll(Iterable<S> entities) {
		return categoryRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		categoryRepository.flush();
	}

	@Override
	public <S extends Category> S saveAndFlush(S entity) {
		return categoryRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return categoryRepository.existsById(id);
	}

	@Override
	public <S extends Category> List<S> saveAllAndFlush(Iterable<S> entities) {
		return categoryRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Category> Page<S> findAll(Example<S> example, Pageable pageable) {
		return categoryRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Category> entities) {
		categoryRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Category> long count(Example<S> example) {
		return categoryRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Category> entities) {
		categoryRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return categoryRepository.count();
	}

	@Override
	public <S extends Category> boolean exists(Example<S> example) {
		return categoryRepository.exists(example);
	}

	@Override
	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		categoryRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Category entity) {
		categoryRepository.delete(entity);
	}

	@Override
	public <S extends Category, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return categoryRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		categoryRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		categoryRepository.deleteAllInBatch();
	}

	@Override
	public Category getOne(Long id) {
		return categoryRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Category> entities) {
		categoryRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		categoryRepository.deleteAll();
	}

	@Override
	public Category getById(Long id) {
		return categoryRepository.getById(id);
	}

	@Override
	public <S extends Category> List<S> findAll(Example<S> example) {
		return categoryRepository.findAll(example);
	}

	@Override
	public <S extends Category> List<S> findAll(Example<S> example, Sort sort) {
		return categoryRepository.findAll(example, sort);
	}

}

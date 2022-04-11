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

import com.example.demo.domain.post.Chapter;
import com.example.demo.repository.post.ChapterRepository;
import com.example.demo.service.post.ChapterService;
import com.example.demo.utils.SlugUtils;

@Service
public class ChapterServiceImpl implements ChapterService {

	@Autowired
	private ChapterRepository chapterRepository;

	@Override
	public Page<Chapter> findByCategoryId(Long categoryId, Pageable pageable) {
		return chapterRepository.findByCategoryId(categoryId, pageable);
	}

	@Override
	public Page<Chapter> findByNameContainingAndCategoryId(String name, Long categoryId, Pageable pageable) {
		return chapterRepository.findByNameContainingAndCategoryId(name, categoryId, pageable);
	}

	@Override
	public List<Chapter> findByNameContaining(String name) {
		return chapterRepository.findByNameContaining(name);
	}

	@Override
	public List<Chapter> findBySlugContaining(String slug) {
		return chapterRepository.findBySlugContaining(slug);
	}

	@Override
	public Page<Chapter> findByNameContaining(String name, Pageable pageable) {
		return chapterRepository.findByNameContaining(name, pageable);
	}

	@Override
	public <S extends Chapter> S save(S entity) {
		try {
			String slug = SlugUtils.toSlug(entity.getName());
			if (entity.getId() != null && slug.replaceFirst("(?s)" + "-[0-9]" + "(?!.*?" + "-[0-9]" + ")", "")
					.equalsIgnoreCase(SlugUtils.toSlug(entity.getName()))) {
				return chapterRepository.save(entity);
			}
			entity.setSlug(SlugUtils.toSlug(entity.getName()));
			return chapterRepository.save(entity);
		} catch (Exception e) {
			String slug = SlugUtils.toSlug(entity.getName());
			List<Chapter> chapters = findBySlugContaining(slug);
			if ((chapters.size() == 1 && entity.getId() != null)
					|| (entity.getId() != null && slug.replaceFirst("(?s)" + "-[0-9]" + "(?!.*?" + "-[0-9]" + ")", "")
							.equalsIgnoreCase(SlugUtils.toSlug(entity.getName())))) {
				return chapterRepository.save(entity);
			}
			Long count = chapters.get(chapters.size() - 1).getId();
			entity.setSlug(SlugUtils.toSlug(entity.getName()) + "-" + count);
			return chapterRepository.save(entity);
			// TODO: handle exception
		}
	}

	@Override
	public <S extends Chapter> Optional<S> findOne(Example<S> example) {
		return chapterRepository.findOne(example);
	}

	@Override
	public List<Chapter> findAll() {
		return chapterRepository.findAll();
	}

	@Override
	public Page<Chapter> findAll(Pageable pageable) {
		return chapterRepository.findAll(pageable);
	}

	@Override
	public List<Chapter> findAll(Sort sort) {
		return chapterRepository.findAll(sort);
	}

	@Override
	public List<Chapter> findAllById(Iterable<Long> ids) {
		return chapterRepository.findAllById(ids);
	}

	@Override
	public Optional<Chapter> findById(Long id) {
		return chapterRepository.findById(id);
	}

	@Override
	public <S extends Chapter> List<S> saveAll(Iterable<S> entities) {
		return chapterRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		chapterRepository.flush();
	}

	@Override
	public <S extends Chapter> S saveAndFlush(S entity) {
		return chapterRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return chapterRepository.existsById(id);
	}

	@Override
	public <S extends Chapter> List<S> saveAllAndFlush(Iterable<S> entities) {
		return chapterRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Chapter> Page<S> findAll(Example<S> example, Pageable pageable) {
		return chapterRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Chapter> entities) {
		chapterRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Chapter> long count(Example<S> example) {
		return chapterRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Chapter> entities) {
		chapterRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return chapterRepository.count();
	}

	@Override
	public <S extends Chapter> boolean exists(Example<S> example) {
		return chapterRepository.exists(example);
	}

	@Override
	public void deleteById(Long id) {
		chapterRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		chapterRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Chapter entity) {
		chapterRepository.delete(entity);
	}

	@Override
	public <S extends Chapter, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return chapterRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		chapterRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		chapterRepository.deleteAllInBatch();
	}

	@Override
	public Chapter getOne(Long id) {
		return chapterRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Chapter> entities) {
		chapterRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		chapterRepository.deleteAll();
	}

	@Override
	public Chapter getById(Long id) {
		return chapterRepository.getById(id);
	}

	@Override
	public <S extends Chapter> List<S> findAll(Example<S> example) {
		return chapterRepository.findAll(example);
	}

	@Override
	public <S extends Chapter> List<S> findAll(Example<S> example, Sort sort) {
		return chapterRepository.findAll(example, sort);
	}

}

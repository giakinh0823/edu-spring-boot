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

import com.example.demo.domain.post.Lesson;
import com.example.demo.repository.post.LessonRepository;
import com.example.demo.service.post.LessonService;
import com.example.demo.utils.SlugUtils;

@Service
public class LessonServiceImpl implements LessonService {
	@Autowired
	private LessonRepository lessonRepository;

	@Override
	public List<Lesson> findByNameContaining(String name) {
		return lessonRepository.findByNameContaining(name);
	}

	@Override
	public List<Lesson> findBySlugContaining(String slug) {
		return lessonRepository.findBySlugContaining(slug);
	}

	@Override
	public Page<Lesson> findByNameContaining(String name, Pageable pageable) {
		return lessonRepository.findByNameContaining(name, pageable);
	}

	@Override
	public <S extends Lesson> S save(S entity) {
		try {
			String slug = SlugUtils.toSlug(entity.getName());
			if (entity.getId() != null && slug.replaceFirst("(?s)" + "-[0-9]" + "(?!.*?" + "-[0-9]" + ")", "")
					.equalsIgnoreCase(SlugUtils.toSlug(entity.getName()))) {
				return lessonRepository.save(entity);
			}
			entity.setSlug(SlugUtils.toSlug(entity.getName()));
			return lessonRepository.save(entity);
		} catch (Exception e) {
			String slug = SlugUtils.toSlug(entity.getName());
			List<Lesson> lessons = findBySlugContaining(slug);
			if ((lessons.size() == 1 && entity.getId() != null)
					|| (entity.getId() != null && slug.replaceFirst("(?s)" + "-[0-9]" + "(?!.*?" + "-[0-9]" + ")", "")
							.equalsIgnoreCase(SlugUtils.toSlug(entity.getName())))) {
				return lessonRepository.save(entity);
			}
			Long count = lessons.get(lessons.size() - 1).getId();
			entity.setSlug(SlugUtils.toSlug(entity.getName()) + "-" + count);
			return lessonRepository.save(entity);
			// TODO: handle exception
		}
	}

	@Override
	public <S extends Lesson> Optional<S> findOne(Example<S> example) {
		return lessonRepository.findOne(example);
	}

	@Override
	public List<Lesson> findAll() {
		return lessonRepository.findAll();
	}

	@Override
	public Page<Lesson> findAll(Pageable pageable) {
		return lessonRepository.findAll(pageable);
	}

	@Override
	public List<Lesson> findAll(Sort sort) {
		return lessonRepository.findAll(sort);
	}

	@Override
	public List<Lesson> findAllById(Iterable<Long> ids) {
		return lessonRepository.findAllById(ids);
	}

	@Override
	public Optional<Lesson> findById(Long id) {
		return lessonRepository.findById(id);
	}

	@Override
	public <S extends Lesson> List<S> saveAll(Iterable<S> entities) {
		return lessonRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		lessonRepository.flush();
	}

	@Override
	public <S extends Lesson> S saveAndFlush(S entity) {
		return lessonRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return lessonRepository.existsById(id);
	}

	@Override
	public <S extends Lesson> List<S> saveAllAndFlush(Iterable<S> entities) {
		return lessonRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Lesson> Page<S> findAll(Example<S> example, Pageable pageable) {
		return lessonRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Lesson> entities) {
		lessonRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Lesson> long count(Example<S> example) {
		return lessonRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Lesson> entities) {
		lessonRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return lessonRepository.count();
	}

	@Override
	public <S extends Lesson> boolean exists(Example<S> example) {
		return lessonRepository.exists(example);
	}

	@Override
	public void deleteById(Long id) {
		lessonRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		lessonRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Lesson entity) {
		lessonRepository.delete(entity);
	}

	@Override
	public <S extends Lesson, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return lessonRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		lessonRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		lessonRepository.deleteAllInBatch();
	}

	@Override
	public Lesson getOne(Long id) {
		return lessonRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Lesson> entities) {
		lessonRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		lessonRepository.deleteAll();
	}

	@Override
	public Lesson getById(Long id) {
		return lessonRepository.getById(id);
	}

	@Override
	public <S extends Lesson> List<S> findAll(Example<S> example) {
		return lessonRepository.findAll(example);
	}

	@Override
	public <S extends Lesson> List<S> findAll(Example<S> example, Sort sort) {
		return lessonRepository.findAll(example, sort);
	}

}

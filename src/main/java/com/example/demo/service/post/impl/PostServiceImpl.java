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

import com.example.demo.domain.post.Post;
import com.example.demo.repository.post.PostRepository;
import com.example.demo.service.post.PostService;
import com.example.demo.utils.SlugUtils;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;

	@Override
	public List<Post> findBySlugContaining(String slug) {
		return postRepository.findBySlugContaining(slug);
	}

	@Override
	public Page<Post> findByTitleContaining(String title, Pageable pageable) {
		return postRepository.findByTitleContaining(title, pageable);
	}

	@Override
	public <S extends Post> S save(S entity) {
		try {
			String slug = SlugUtils.toSlug(entity.getTitle());
			if (entity.getId() != null && slug.replaceFirst("(?s)" + "-[0-9]" + "(?!.*?" + "-[0-9]" + ")", "")
					.equalsIgnoreCase(SlugUtils.toSlug(entity.getTitle()))) {
				return postRepository.save(entity);
			}
			entity.setSlug(SlugUtils.toSlug(entity.getTitle()));
			return postRepository.save(entity);
		} catch (Exception e) {
			String slug = SlugUtils.toSlug(entity.getTitle());
			List<Post> posts = findBySlugContaining(slug);
			if ((posts.size() == 1 && entity.getId() != null)
					|| (entity.getId() != null && slug.replaceFirst("(?s)" + "-[0-9]" + "(?!.*?" + "-[0-9]" + ")", "")
							.equalsIgnoreCase(SlugUtils.toSlug(entity.getTitle())))) {
				return postRepository.save(entity);
			}
			Long count = posts.get(posts.size() - 1).getId();
			entity.setSlug(SlugUtils.toSlug(entity.getTitle()) + "-" + count);
			return postRepository.save(entity);
			// TODO: handle exception
		}
	}

	@Override
	public <S extends Post> Optional<S> findOne(Example<S> example) {
		return postRepository.findOne(example);
	}

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public Page<Post> findAll(Pageable pageable) {
		return postRepository.findAll(pageable);
	}

	@Override
	public List<Post> findAll(Sort sort) {
		return postRepository.findAll(sort);
	}

	@Override
	public List<Post> findAllById(Iterable<Long> ids) {
		return postRepository.findAllById(ids);
	}

	@Override
	public Optional<Post> findById(Long id) {
		return postRepository.findById(id);
	}

	@Override
	public <S extends Post> List<S> saveAll(Iterable<S> entities) {
		return postRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		postRepository.flush();
	}

	@Override
	public <S extends Post> S saveAndFlush(S entity) {
		return postRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return postRepository.existsById(id);
	}

	@Override
	public <S extends Post> List<S> saveAllAndFlush(Iterable<S> entities) {
		return postRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Post> Page<S> findAll(Example<S> example, Pageable pageable) {
		return postRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Post> entities) {
		postRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Post> long count(Example<S> example) {
		return postRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Post> entities) {
		postRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return postRepository.count();
	}

	@Override
	public <S extends Post> boolean exists(Example<S> example) {
		return postRepository.exists(example);
	}

	@Override
	public void deleteById(Long id) {
		postRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		postRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Post entity) {
		postRepository.delete(entity);
	}

	@Override
	public <S extends Post, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return postRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		postRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		postRepository.deleteAllInBatch();
	}

	@Override
	public Post getOne(Long id) {
		return postRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Post> entities) {
		postRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		postRepository.deleteAll();
	}

	@Override
	public Post getById(Long id) {
		return postRepository.getById(id);
	}

	@Override
	public <S extends Post> List<S> findAll(Example<S> example) {
		return postRepository.findAll(example);
	}

	@Override
	public <S extends Post> List<S> findAll(Example<S> example, Sort sort) {
		return postRepository.findAll(example, sort);
	}

}

package com.example.demo.service.user.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.domain.user.User;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Page<User> findByUsernameContainingOrEmailContainingOrPhoneContaining(String username, String email, String phone,
			Pageable pageable) {
		return userRepository.findByUsernameContainingOrEmailContainingOrPhoneContaining(username, email, phone, pageable);
	}

	@Override
	public List<User> findByUsernameContaining(String username) {
		return userRepository.findByUsernameContaining(username);
	}

	@Override
	public Page<User> findByUsernameContaining(String username, Pageable pageable) {
		return userRepository.findByUsernameContaining(username, pageable);
	}

	@Override
	public Optional<User> findByUsernameIgnoreCase(String username) {
		return userRepository.findByUsernameIgnoreCase(username);
	}

	@Override
	public User login(String username, String password) {
		Optional<User> optional = findByUsernameIgnoreCase(username);
		if (optional.isPresent() && bCryptPasswordEncoder.matches(password, optional.get().getPassword())) {
			optional.get().setPassword("");
			return optional.get();
		}
		return null;
	}

	@Override
	public <S extends User> S save(S entity) {
		if (entity.getId() != null) {
			Optional<User> optional = findById(entity.getId());
			if (optional.isPresent()) {
				entity.setCreated_at(optional.get().getCreated_at());
				entity.setUpdated_at(new Date());
				if (entity.getPassword()==null || StringUtils.isEmpty(entity.getPassword())) {
					entity.setPassword(optional.get().getPassword());
				} else {
					entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
				}
			} else {
				entity.setCreated_at(new Date());
				entity.setUpdated_at(new Date());
				entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
			}
		} else {
			entity.setCreated_at(new Date());
			entity.setUpdated_at(new Date());
			entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
		}
		return userRepository.save(entity);
	}

	@Override
	public <S extends User> Optional<S> findOne(Example<S> example) {
		return userRepository.findOne(example);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public List<User> findAll(Sort sort) {
		return userRepository.findAll(sort);
	}

	@Override
	public List<User> findAllById(Iterable<Long> ids) {
		return userRepository.findAllById(ids);
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public <S extends User> List<S> saveAll(Iterable<S> entities) {
		return userRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		userRepository.flush();
	}

	@Override
	public <S extends User> S saveAndFlush(S entity) {
		return userRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(Long id) {
		return userRepository.existsById(id);
	}

	@Override
	public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
		return userRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
		return userRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<User> entities) {
		userRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends User> long count(Example<S> example) {
		return userRepository.count(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<User> entities) {
		userRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return userRepository.count();
	}

	@Override
	public <S extends User> boolean exists(Example<S> example) {
		return userRepository.exists(example);
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		userRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Override
	public <S extends User, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return userRepository.findBy(example, queryFunction);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		userRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		userRepository.deleteAllInBatch();
	}

	@Override
	public User getOne(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends User> entities) {
		userRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Override
	public User getById(Long id) {
		return userRepository.getById(id);
	}

	@Override
	public <S extends User> List<S> findAll(Example<S> example) {
		return userRepository.findAll(example);
	}

	@Override
	public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
		return userRepository.findAll(example, sort);
	}

}

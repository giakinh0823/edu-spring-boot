package com.example.demo.repository.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.post.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>{
	List<Group> findByNameContaining(String name);
	@Query(value="SELECT * FROM [group] WHERE [slug] LIKE %?1%",  nativeQuery = true)
	List<Group> findBySlugContaining(String slug);
	Page<Group> findByNameContaining(String name, Pageable pageable);
}

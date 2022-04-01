package com.example.demo.domain.post;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="[chapter]")
public class Chapter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "nvarchar(255) NOT NULL")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="categoryId")
	private Category category;
	
	@OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
	private Set<Lesson> lessons = new HashSet<>();
	
}

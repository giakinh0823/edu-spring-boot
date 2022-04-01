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
@Table(name="[category]")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "nvarchar(255) NOT NULL")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="groupId")
	private Group group;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Subject> subjects = new HashSet<>();
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Chapter> chapters = new HashSet<>();
}

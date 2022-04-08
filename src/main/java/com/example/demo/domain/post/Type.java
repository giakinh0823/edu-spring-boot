package com.example.demo.domain.post;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="[type]")
public class Type implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition = "nvarchar(255) NOT NULL")
	private String name;
	
	private Boolean is_fee;
	
	@OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
	private Set<Document> documents = new HashSet<>();
	
	@OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
	private Set<Post> posts = new HashSet<>();
}

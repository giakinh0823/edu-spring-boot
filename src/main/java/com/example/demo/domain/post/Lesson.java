package com.example.demo.domain.post;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="[lesson]")
public class Lesson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "nvarchar(255) NOT NULL")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="chapterId")
	private Chapter chapter;
	
	@OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL)
	private Post post;
}

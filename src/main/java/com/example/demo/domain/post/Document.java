package com.example.demo.domain.post;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="[document]")
public class Document{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "nvarchar(600) NOT NULL")
	private String title;
	@Column(name = "[file]" ,columnDefinition = "nvarchar(1000) NOT NULL")
	private String file;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_at;
	
	@ManyToOne
	@JoinColumn(name="subjectId")
	private Subject subject;
	
	@ManyToOne
	@JoinColumn(name="typeId")
	private Type type;
}

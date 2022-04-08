package com.example.demo.model.post;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject implements Serializable{
	private Long id;
	private String slug;
	@NotEmpty
	private String name;
	
	private boolean isEdit=false;
}

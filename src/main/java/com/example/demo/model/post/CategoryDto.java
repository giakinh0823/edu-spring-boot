package com.example.demo.model.post;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto implements Serializable {
	private Long id;
	private String slug;
	@NotEmpty
	private String name;
	
	@NotNull
	private Long groupId;
	
	private boolean isEdit=false;
}

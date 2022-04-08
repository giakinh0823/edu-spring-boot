package com.example.demo.model.post;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDto implements Serializable{
	private Long id;
	private String slug;
	@NotEmpty
	private String name;	
	
	private Long categoryId;
	
	private boolean isEdit=false;
}

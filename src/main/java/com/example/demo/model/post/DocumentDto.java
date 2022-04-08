package com.example.demo.model.post;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto implements Serializable{
	private Long id;
	private String slug;
	@NotEmpty
	private String title;
	private String file;
	private Date created_at;
	private Date updated_at;
	
	private boolean isEdit=false;
}

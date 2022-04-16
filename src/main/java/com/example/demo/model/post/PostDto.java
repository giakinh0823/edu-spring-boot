package com.example.demo.model.post;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto implements Serializable {
	private Long id;
	private String slug;
	@NotEmpty
	private String title;
	private String description;
	private String content;
	private String image;
	private String file;
	private Date created_at;
	private Date updated_at;

	private Long lessonId;
	private MultipartFile imageFile;
	private MultipartFile filePdf;

	private boolean isEdit = false;
}

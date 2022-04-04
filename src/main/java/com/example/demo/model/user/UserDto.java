package com.example.demo.model.user;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto{
	private Long id;
	@NotEmpty
	@Length(min=4)
	private String username;
	@NotEmpty
	@Length(min=6)
	private String password;
	@NotEmpty
	private String first_name;
	@NotEmpty
	private String last_name;
	@Email
	private String email;
	private String image;
	@NotEmpty
	@Length(min=9)
	private String phone;
	private Boolean is_super;
	private Boolean is_active;
	@NotNull
	private Boolean gender;
	private Date created_at;
	private Date updated_at;
	
	private MultipartFile imageFile;
	private boolean isEdit=false;
}

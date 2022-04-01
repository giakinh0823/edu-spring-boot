package com.example.demo.model.user;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

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
	private String first_name;
	private String last_name;
	@Email
	private String email;
	private String phone;
	private Boolean is_super;
	private Boolean is_active;
	private Boolean gender;
	private Date created_at;
	private Date updated_at;
	
	private boolean isEdit=false;
}

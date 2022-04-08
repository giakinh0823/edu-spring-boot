package com.example.demo.model.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	@NotEmpty(message = "Username cant not empty!")
	@Length(min=4, message = "Username length must be greater than 4")
	private String username;
	private String password;
	private String confirm_password;
	@NotEmpty(message = "First name cant not empty!")
	private String first_name;
	@NotEmpty(message = "Last name cant not empty!")
	private String last_name;
	@NotEmpty(message = "Email cant not empty!")
	@Email
	private String email;
	private String image;
	@NotEmpty
	@Length(min=9, message = "Phone length must be greater than 9")
	private String phone;
	private Boolean is_super;
	private Boolean is_active;
	private Boolean is_staff;
	@NotNull
	private Boolean gender;
	private Date created_at;
	private Date updated_at;
	
	private MultipartFile imageFile;
	private Set<PermissionDto> permissions = new HashSet<>();
	
	private boolean isEdit=false;
	
	public boolean checkPermission(Long idPermission) {
		for (PermissionDto permissionDto : permissions) {
			if(permissionDto.getId().equals(idPermission)) {
				return true;
			}
		}
		return false;
	}
}

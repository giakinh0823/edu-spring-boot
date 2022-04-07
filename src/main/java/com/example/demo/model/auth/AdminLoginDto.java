package com.example.demo.model.auth;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginDto {
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	
	private boolean rememberMe = false;
}

package com.example.demo.model.user;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {
	private Long id;
	@NotEmpty
	private String name;
	
	private boolean isEdit=false;
}

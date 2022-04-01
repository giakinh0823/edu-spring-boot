package com.example.demo.model.post;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDto {
	private Long id;
	@NotEmpty
	private String name;

	private boolean isEdit=false;
}

package com.example.demo.model.post;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDto implements Serializable{
	private Long id;
	@NotEmpty
	private String name;

	private Boolean is_fee;
	
	private boolean isEdit=false;
}

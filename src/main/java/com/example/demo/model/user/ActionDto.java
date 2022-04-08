package com.example.demo.model.user;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionDto implements Serializable{
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String feature;
	@NotEmpty
	private String code;
	
	private boolean isEdit=false;
}

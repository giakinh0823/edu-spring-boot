package com.example.demo.model.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto implements Serializable{
	private Long id;
	@NotEmpty
	private String name;
	
	private Set<ActionDto> actions = new HashSet<>();
	
	private boolean isEdit=false;
	
	public boolean checkAction(Long idAction) {
		for (ActionDto actionDto : actions) {
			if(actionDto.getId().equals(idAction)) {
				return true;
			}
		}
		return false;
	}
}

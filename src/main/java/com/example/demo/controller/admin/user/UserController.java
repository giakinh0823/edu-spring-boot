package com.example.demo.controller.admin.user;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.user.Action;
import com.example.demo.model.user.ActionDto;
import com.example.demo.model.user.PermissionDto;
import com.example.demo.service.storage.StorageService;
import com.example.demo.service.user.PermissionService;
import com.example.demo.service.user.UserService;

@Controller
@RequestMapping("admin/users")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private StorageService storageService;

	@ModelAttribute("permissions")
	public List<PermissionDto> getPermissions() {
		return permissionService.findAll().stream().map((item) -> {
			PermissionDto permissionDto = new PermissionDto();
			BeanUtils.copyProperties(item, permissionDto);
			for (Action action : item.getActions()) {
				ActionDto actionDto = new ActionDto();
				BeanUtils.copyProperties(action, actionDto);
				permissionDto.getActions().add(actionDto);
			}
			return permissionDto;
		}).sorted((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName())).toList();
	}
	
	
	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile(@PathVariable String filename){
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\""+file.getFilename()+"\"").body(file);
	}

}

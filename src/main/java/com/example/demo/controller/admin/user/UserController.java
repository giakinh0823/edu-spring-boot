package com.example.demo.controller.admin.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.user.Action;
import com.example.demo.domain.user.Permission;
import com.example.demo.domain.user.User;
import com.example.demo.model.user.ActionDto;
import com.example.demo.model.user.PermissionDto;
import com.example.demo.model.user.UserDto;
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
	public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
		storageService.setRootLocation("uploads/images/user");
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_GIF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getFilename() + "")
				.body(file);
	}

	@GetMapping("")
	public String get(@RequestParam(name = "search", required = false) String search, ModelMap model,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size) {
		int pageSize = size.orElse(20);
		model.addAttribute("size", pageSize);
		int currentPage = page.orElse(1);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());
		Page<User> results = null;
		if (StringUtils.hasText(search)) {
			results = userService.findByUsernameContainingOrEmailContainingOrPhoneContaining(search,search,search, pageable);
			model.addAttribute("search", search);
		} else {
			results = userService.findAll(pageable);
		}
		int totalPages = results.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, currentPage - 2);
			int end = Math.min(currentPage + 2, totalPages);
			if (totalPages > 5) {
				if (end == totalPages)
					start = end - 5;
				else if (start == 1)
					end = start + 5;
			}
			List<Integer> pages = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		model.addAttribute("results", results);
		return "admin/user/list";
	}

	@GetMapping("add")
	public String add(Model model) {
		UserDto userDto = new UserDto();
		userDto.setIs_active(true);
		userDto.setGender(true);
		model.addAttribute("user", userDto);
		return "admin/user/form";
	}

	@GetMapping("edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
		Optional<User> optional = userService.findById(id);
		UserDto userDto = new UserDto();
		if (!optional.isEmpty()) {
			User user = optional.get();
			BeanUtils.copyProperties(user, userDto);
			userDto.setEdit(true);
			userDto.setPassword("");
			userDto.setConfirm_password("");
			for (Permission permission : user.getPermissions()) {
				PermissionDto permissionDto = new PermissionDto();
				BeanUtils.copyProperties(permission, permissionDto);
				userDto.getPermissions().add(permissionDto);
			}
			model.addAttribute("user", userDto);
			return new ModelAndView("admin/user/form", model);
		}
		return new ModelAndView("redirect:admin/users", model);
	}

	@PostMapping("save")
	public ModelAndView save(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result,
			ModelMap model,
			@RequestParam("permission[]") Optional<String[]> permisstionDtos,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		if (!userDto.isEdit()) {
			if (userDto.getPassword().trim().isEmpty()) {
				result.addError(new FieldError("password", "password", "Password cant not empty"));
			}
			if (userDto.getPassword().length() < 6) {
				result.addError(new FieldError("password", "password", "Password length must be greater than 6"));
			}
			if (!userDto.getPassword().equals(userDto.getConfirm_password())) {
				result.addError(new FieldError("confirm_password", "confirm_password", "Confirm password not match"));
			}
		}
		if (!permisstionDtos.isPresent()) {
			result.addError(new FieldError("permissions", "permissions", "Please choose at least one permission"));
		}
		if (result.hasErrors()) {
			if (permisstionDtos.isPresent()) {
				for (String item : permisstionDtos.get()) {
					try {
						Long id = Long.parseLong(item);
						PermissionDto permissionDto = new PermissionDto();
						permissionDto.setId(id);
						userDto.getPermissions().add(permissionDto);
					} catch (Exception e) {
						// TODO: handle exception
						result.addError(new FieldError("permissions", "permissions", "Some thing wrong! Please try again"));
					}
				}
			}
			return new ModelAndView("admin/user/form");
		}
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		if (permisstionDtos.isPresent()) {
			for (String item : permisstionDtos.get()) {
				try {
					Long id = Long.parseLong(item);
					Permission permission = permissionService.getById(id);
					user.getPermissions().add(permission);
				} catch (Exception e) {
					// TODO: handle exception
					result.addError(new FieldError("permissions", "permissions", "Some thing wrong! Please try again"));
					return new ModelAndView("admin/user/form");
				}
			}
		}
		if(userDto.getImageFile()!=null && !userDto.getImageFile().isEmpty()) {
			storageService.setRootLocation("uploads/images/user");
			if (userDto.isEdit() && user.getImage()!=null && !user.getImage().isEmpty()) {
				try {
					storageService.delete(user.getImage());
				} catch (Exception e) {
					// TODO: handle exception
					model.addAttribute("error", "Some thing error!");
					return new ModelAndView("admin/user/form", model);
				}
			}
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			user.setImage(storageService.getStoredFileName(userDto.getImageFile(), uuString));
			System.out.print(user.getImage());
			try {
				storageService.store(userDto.getImageFile(), user.getImage());
				user.setImage(user.getImage());
			} catch (Exception e) {
				// TODO: handle exception
				model.addAttribute("error", "Cant not save file");
				return new ModelAndView("admin/user/form", model);
			}
		}
		userService.save(user);
		redirectAttributes.addFlashAttribute("success", "User save success!");
		if(userDto.isEdit()) {
			return new ModelAndView("redirect:"+referer);
		}
		return new ModelAndView("redirect:/admin/users");
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		userService.deleteById(id);
		redirectAttributes.addFlashAttribute("success", "User is deleted!");
		return new ModelAndView("redirect:" + referer);
	}

}

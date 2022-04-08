package com.example.demo.controller.admin.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.user.Action;
import com.example.demo.domain.user.Permission;
import com.example.demo.model.user.ActionDto;
import com.example.demo.model.user.PermissionDto;
import com.example.demo.service.user.ActionService;
import com.example.demo.service.user.PermissionService;

@Controller
@RequestMapping("admin/permissions")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private ActionService actionService;

	@ModelAttribute("actions")
	public List<ActionDto> getActions() {
		return actionService.findAll().stream().map((item) -> {
			ActionDto actionDto = new ActionDto();
			BeanUtils.copyProperties(item, actionDto);
			return actionDto;
		}).sorted((o1, o2) -> o1.getFeature().compareToIgnoreCase(o2.getFeature())).toList();
	}

	@GetMapping("")
	public String get(@RequestParam(name = "name", required = false) String name, ModelMap model,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size) {
		int pageSize = size.orElse(20);
		model.addAttribute("size", pageSize);
		int currentPage = page.orElse(1);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
		Page<Permission> results = null;
		System.out.println(name);
		if (StringUtils.hasText(name)) {
			results = permissionService.findByNameContaining(name, pageable);
			model.addAttribute("name", name);
		} else {
			results = permissionService.findAll(pageable);
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
		return "admin/permission/list";
	}

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("permission", new PermissionDto());
		return "admin/permission/form";
	}

	@GetMapping("edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
		Optional<Permission> optional = permissionService.findById(id);
		PermissionDto permissionDto = new PermissionDto();
		if (!optional.isEmpty()) {
			Permission permission = optional.get();
			BeanUtils.copyProperties(permission, permissionDto);
			permissionDto.setEdit(true);
			model.addAttribute("permission", permissionDto);
			for (Action action : permission.getActions()) {
				ActionDto actionDto = new ActionDto();
				BeanUtils.copyProperties(action, actionDto);
				permissionDto.getActions().add(actionDto);
			}
			return new ModelAndView("admin/permission/form", model);
		}
		return new ModelAndView("redirect:admin/permissions", model);
	}

	@PostMapping("save")
	public ModelAndView save(@Valid @ModelAttribute("permission") PermissionDto permissionDto, BindingResult result,
			@RequestParam("action[]") Optional<String[]> actionDtos, 
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		if (!actionDtos.isPresent()) {
			result.addError(new FieldError("actions", "actions", "Please choose at least one action"));
		}
		if (result.hasErrors()) {
			if (actionDtos.isPresent()) {
				for (String item : actionDtos.get()) {
					try {
						Long id = Long.parseLong(item);
						ActionDto actionDto = new ActionDto();
						actionDto.setId(id);
						permissionDto.getActions().add(actionDto);
					} catch (Exception e) {
						// TODO: handle exception
						result.addError(new FieldError("actions", "actions", "Some thing wrong! Please try again"));
					}
				}
			}
			return new ModelAndView("admin/permission/form");
		}
		Permission permission = new Permission();
		if (actionDtos.isPresent()) {
			for (String actionDto : actionDtos.get()) {
				try {
					Long id = Long.parseLong(actionDto);
					Action action = actionService.getById(id);
					permission.getActions().add(action);
				} catch (Exception e) {
					// TODO: handle exception
					result.addError(new FieldError("actions", "actions", "Some thing wrong! Please try again"));
					return new ModelAndView("admin/permission/form");
				}
			}
		}

		BeanUtils.copyProperties(permissionDto, permission);
		permissionService.save(permission);
		redirectAttributes.addFlashAttribute("success", "Permission save success!");
		if(permissionDto.isEdit()) {
			return new ModelAndView("redirect:"+referer);
		}
		return new ModelAndView("redirect:/admin/permissions");
	}

	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		permissionService.deleteById(id);
		redirectAttributes.addFlashAttribute("success", "Permission is deleted!");
		return new ModelAndView("redirect:" + referer);
	}
}

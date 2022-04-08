package com.example.demo.controller.admin.post;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.post.Group;
import com.example.demo.model.post.GroupDto;
import com.example.demo.service.post.GroupService;

@Controller
@RequestMapping("admin/groups")
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	
	@GetMapping("")
	public String get(@RequestParam(name = "name", required = false) String name, ModelMap model,
			@RequestParam(name= "page") Optional<Integer> page, 
			@RequestParam(name= "size") Optional<Integer> size) {
		int pageSize = size.orElse(20);
		model.addAttribute("size", pageSize);
		int currentPage = page.orElse(1);
		Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.by("id"));
		Page<Group> results = null;
		System.out.println(name);
		if (StringUtils.hasText(name)) {
			results = groupService.findByNameContaining(name, pageable);
			model.addAttribute("name", name);
		} else {
			results = groupService.findAll(pageable);
		}
		int totalPages = results.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, currentPage-2);
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
		return "admin/group/list";
	}
	
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("group", new GroupDto());
		return "admin/group/form";
	}

	@GetMapping("edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
		Optional<Group> optional = groupService.findById(id);
		GroupDto groupDto = new GroupDto();
		if (!optional.isEmpty()) {
			Group group = optional.get();
			BeanUtils.copyProperties(group, groupDto);
			groupDto.setEdit(true);
			model.addAttribute("group", groupDto);
			return new ModelAndView("admin/group/form", model);
		}
		return new ModelAndView("redirect:admin/groups", model);
	}

	@PostMapping("save")
	public ModelAndView save(@Valid @ModelAttribute("group") GroupDto groupDto,
			BindingResult result,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/group/form");
		}
		Group group = new Group();
		BeanUtils.copyProperties(groupDto, group);
		groupService.save(group);
		redirectAttributes.addFlashAttribute("success", "Group save success!");
		if(groupDto.isEdit()) {
			return new ModelAndView("redirect:"+referer);
		}
		return new ModelAndView("redirect:/admin/groups");
	}
	
	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		groupService.deleteById(id);
		redirectAttributes.addFlashAttribute("success", "Group is deleted!");
		return new ModelAndView("redirect:" + referer);
	}
}

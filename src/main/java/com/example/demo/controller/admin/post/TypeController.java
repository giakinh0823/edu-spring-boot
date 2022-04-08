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

import com.example.demo.domain.post.Type;
import com.example.demo.model.post.TypeDto;
import com.example.demo.service.post.TypeService;

@Controller
@RequestMapping("admin/types")
public class TypeController {
	@Autowired
	private TypeService typeService;

	@GetMapping("")
	public String get(@RequestParam(name = "name", required = false) String name, ModelMap model,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size) {
		int pageSize = size.orElse(20);
		model.addAttribute("size", pageSize);
		int currentPage = page.orElse(1);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
		Page<Type> results = null;
		if (StringUtils.hasText(name)) {
			results = typeService.findByNameContaining(name, pageable);
			model.addAttribute("name", name);
		} else {
			results = typeService.findAll(pageable);
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
		return "admin/type/list";
	}

	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("type", new TypeDto());
		return "admin/type/form";
	}

	@GetMapping("edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
		Optional<Type> optional = typeService.findById(id);
		TypeDto typeDto = new TypeDto();
		if (!optional.isEmpty()) {
			Type type = optional.get();
			BeanUtils.copyProperties(type, typeDto);
			typeDto.setEdit(true);
			model.addAttribute("type", typeDto);
			return new ModelAndView("admin/type/form", model);
		}
		return new ModelAndView("redirect:admin/types", model);
	}

	@PostMapping("save")
	public ModelAndView save(@Valid @ModelAttribute("type") TypeDto typeDto, BindingResult result,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/type/form");
		}
		Type type = new Type();
		BeanUtils.copyProperties(typeDto, type);
		typeService.save(type);
		redirectAttributes.addFlashAttribute("success", "Type save success!");
		if (typeDto.isEdit()) {
			return new ModelAndView("redirect:" + referer);
		}
		return new ModelAndView("redirect:/admin/types");
	}

	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		typeService.deleteById(id);
		redirectAttributes.addFlashAttribute("success", "Type is deleted!");
		return new ModelAndView("redirect:" + referer);
	}
}

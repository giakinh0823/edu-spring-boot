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

import com.example.demo.domain.post.Category;
import com.example.demo.domain.post.Chapter;
import com.example.demo.model.post.CategoryDto;
import com.example.demo.model.post.ChapterDto;
import com.example.demo.service.post.CategoryService;
import com.example.demo.service.post.ChapterService;

@Controller
@RequestMapping("admin/categories/detail/{categoryId}/chapters")
public class ChapterController {

	@Autowired
	private ChapterService chapterService;

	@Autowired
	private CategoryService categoryService;

	@ModelAttribute("category")
	public CategoryDto getCategory(@PathVariable("categoryId") Long id) {
		Category category = categoryService.getById(id);
		CategoryDto categoryDto = new CategoryDto();
		BeanUtils.copyProperties(category, categoryDto);
		return categoryDto;
	}

	@ModelAttribute("categories")
	public List<CategoryDto> getCategories() {
		return categoryService.findAll().stream().map((item) -> {
			CategoryDto categoryDto = new CategoryDto();
			BeanUtils.copyProperties(item, categoryDto);
			return categoryDto;
		}).toList();
	}

	@GetMapping("")
	public String get(@PathVariable("categoryId") Long categoryId,
			@RequestParam(name = "name", required = false) String name, ModelMap model,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size) {
		int pageSize = size.orElse(20);
		model.addAttribute("size", pageSize);
		int currentPage = page.orElse(1);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id"));
		Page<Chapter> results = null;
		if (StringUtils.hasText(name)) {
			results = chapterService.findByNameContainingAndCategoryId(name, categoryId, pageable);
			model.addAttribute("name", name);
		} else {
			results = chapterService.findByCategoryId(categoryId,pageable);
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
		return "admin/chapter/list";
	}

	@GetMapping("add")
	public String add(@PathVariable("categoryId") Long categoryId, Model model) {
		ChapterDto chapterDto = new ChapterDto();
		chapterDto.setCategoryId(categoryId);
		model.addAttribute("chapter", chapterDto);
		return "admin/chapter/form";
	}

	@GetMapping("edit/{id}")
	public ModelAndView edit(@PathVariable("categoryId") Long categoryId, @PathVariable("id") Long id, ModelMap model) {
		Optional<Chapter> optional = chapterService.findById(id);
		ChapterDto chapterDto = new ChapterDto();
		if (!optional.isEmpty()) {
			Chapter chapter = optional.get();
			BeanUtils.copyProperties(chapter, chapterDto);
			chapterDto.setEdit(true);
			chapterDto.setCategoryId(chapter.getCategory().getId());
			model.addAttribute("chapter", chapterDto);
			return new ModelAndView("admin/chapter/form", model);
		}
		return new ModelAndView("redirect:/admin/categories/detail/" + categoryId + "/chapters", model);
	}

	@PostMapping("save")
	public ModelAndView save(@Valid @ModelAttribute("chapter") ChapterDto chapterDto, BindingResult result,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/chapter/form");
		}
		Chapter chapter = new Chapter();
		Category category = new Category();
		category.setId(chapterDto.getCategoryId());
		chapter.setCategory(category);
		BeanUtils.copyProperties(chapterDto, chapter);
		chapterService.save(chapter);
		redirectAttributes.addFlashAttribute("success", "Chapter save success!");
		if (chapterDto.isEdit()) {
			return new ModelAndView("redirect:" + referer);
		}
		return new ModelAndView("redirect:/admin/categories/detail/" + chapterDto.getCategoryId() + "/chapters");
	}

	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		chapterService.deleteById(id);
		redirectAttributes.addFlashAttribute("success", "Chapter is deleted!");
		return new ModelAndView("redirect:" + referer);
	}
}

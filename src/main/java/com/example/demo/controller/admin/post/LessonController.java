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

import com.example.demo.domain.post.Chapter;
import com.example.demo.domain.post.Lesson;
import com.example.demo.model.post.ChapterDto;
import com.example.demo.model.post.LessonDto;
import com.example.demo.service.post.ChapterService;
import com.example.demo.service.post.LessonService;

@Controller
@RequestMapping("admin/categories/detail/{categoryId}/chapters/detail/{chapterId}/lessons")
public class LessonController {
	@Autowired
	private LessonService lessonService;

	@Autowired
	private ChapterService chapterService;

	@ModelAttribute("chapter")
	public ChapterDto getChapter(@PathVariable("chapterId") Long id) {
		Chapter chapter = chapterService.getById(id);
		ChapterDto chapterDto = new ChapterDto();
		BeanUtils.copyProperties(chapter, chapterDto);
		chapterDto.setCategoryId(chapter.getCategory().getId());
		return chapterDto;
	}

	@ModelAttribute("chapters")
	public List<ChapterDto> getChapters() {
		return chapterService.findAll().stream().map((item) -> {
			ChapterDto chapterDto = new ChapterDto();
			BeanUtils.copyProperties(item, chapterDto);
			return chapterDto;
		}).toList();
	}

	@GetMapping("")
	public String get(@PathVariable("chapterId") Long chapterId,
			@RequestParam(name = "name", required = false) String name, ModelMap model,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size) {
		int pageSize = size.orElse(20);
		model.addAttribute("size", pageSize);
		int currentPage = page.orElse(1);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id"));
		Page<Lesson> results = null;
		if (StringUtils.hasText(name)) {
			results = lessonService.findByNameContainingAndChapterId(name, chapterId, pageable);
			model.addAttribute("name", name);
		} else {
			results = lessonService.findByChapterId(chapterId, pageable);
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
		return "admin/lesson/list";
	}

	@GetMapping("add")
	public String add(@PathVariable("chapterId") Long chapterId, Model model) {
		LessonDto lessonDto = new LessonDto();
		lessonDto.setChapterId(chapterId);
		;
		model.addAttribute("lesson", lessonDto);
		return "admin/lesson/form";
	}

	@GetMapping("edit/{id}")
	public ModelAndView edit(@PathVariable("categoryId") Long categoryId, @PathVariable("chapterId") Long chapterId,
			@PathVariable("id") Long id, ModelMap model) {
		Optional<Lesson> optional = lessonService.findById(id);
		LessonDto lessonDto = new LessonDto();
		if (!optional.isEmpty()) {
			Lesson lesson = optional.get();
			BeanUtils.copyProperties(lesson, lessonDto);
			lessonDto.setChapterId(lesson.getChapter().getId());
			lessonDto.setEdit(true);
			model.addAttribute("lesson", lessonDto);
			return new ModelAndView("admin/lesson/form", model);
		}
		return new ModelAndView(
				"redirect:/admin/categories/detail/" + categoryId + "/chapters/detail/" + chapterId + "/lessons",
				model);
	}

	@PostMapping("save")
	public ModelAndView save(@PathVariable("categoryId") Long categoryId, @PathVariable("chapterId") Long chapterId,
			@Valid @ModelAttribute("lesson") LessonDto lessonDto, BindingResult result,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/lesson/form");
		}
		Lesson lesson = new Lesson();
		Chapter chapter = new Chapter();
		chapter.setId(lessonDto.getChapterId());
		lesson.setChapter(chapter);
		BeanUtils.copyProperties(lessonDto, lesson);
		lessonService.save(lesson);
		redirectAttributes.addFlashAttribute("success", "Lesson save success!");
		if (lessonDto.isEdit()) {
			return new ModelAndView("redirect:" + referer);
		}
		return new ModelAndView(
				"redirect:/admin/categories/detail/" + categoryId + "/chapters/detail/" + chapterId + "/lessons");
	}

	@GetMapping("delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		lessonService.deleteById(id);
		redirectAttributes.addFlashAttribute("success", "Lesson is deleted!");
		return new ModelAndView("redirect:" + referer);
	}
}

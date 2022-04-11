package com.example.demo.controller.admin.post;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.post.Chapter;
import com.example.demo.domain.post.Lesson;
import com.example.demo.domain.post.Post;
import com.example.demo.model.post.ChapterDto;
import com.example.demo.model.post.LessonDto;
import com.example.demo.model.post.PostDto;
import com.example.demo.service.post.ChapterService;
import com.example.demo.service.post.LessonService;
import com.example.demo.service.post.PostService;

@Controller
@RequestMapping("admin/categories/detail/{categoryId}/chapters/detail/{chapterId}/lessons/detail/{lessonId}/posts")

public class PostController {
	@Autowired
	private PostService postService;

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

	@ModelAttribute("lesson")
	public LessonDto getLesson(@PathVariable("lessonId") Long id) {
		Lesson lesson = lessonService.getById(id);
		LessonDto lessonDto = new LessonDto();
		BeanUtils.copyProperties(lesson, lessonDto);
		lessonDto.setChapterId(lesson.getChapter().getId());
		return lessonDto;
	}

	@ModelAttribute("lessons")
	public List<LessonDto> getCategories() {
		return lessonService.findAll().stream().map((item) -> {
			LessonDto lessonDto = new LessonDto();
			BeanUtils.copyProperties(item, lessonDto);
			return lessonDto;
		}).toList();
	}

	@GetMapping("")
	public String get(@PathVariable("lessonId") Long lessonId,
			@RequestParam(name = "name", required = false) String name, ModelMap model,
			@RequestParam(name = "page") Optional<Integer> page, @RequestParam(name = "size") Optional<Integer> size) {
		int pageSize = size.orElse(20);
		model.addAttribute("size", pageSize);
		int currentPage = page.orElse(1);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id"));
		Page<Post> results = null;
		if (StringUtils.hasText(name)) {
			results = postService.findByTitleContainingAndLessonId(name, lessonId, pageable);
			model.addAttribute("name", name);
		} else {
			results = postService.findByLessonId(lessonId, pageable);
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
		return "admin/post/list";
	}

	@GetMapping("add")
	public String add(@PathVariable("lessonId") Long lessonId, Model model) {
		PostDto postDto = new PostDto();
		postDto.setLessonId(lessonId);
		model.addAttribute("post", postDto);
		return "admin/post/form";
	}
}

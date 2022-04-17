package com.example.demo.controller.admin.post;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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

import com.example.demo.domain.post.Chapter;
import com.example.demo.domain.post.Lesson;
import com.example.demo.domain.post.Post;
import com.example.demo.domain.post.Type;
import com.example.demo.model.post.ChapterDto;
import com.example.demo.model.post.LessonDto;
import com.example.demo.model.post.PostDto;
import com.example.demo.model.post.TypeDto;
import com.example.demo.service.post.ChapterService;
import com.example.demo.service.post.LessonService;
import com.example.demo.service.post.PostService;
import com.example.demo.service.post.TypeService;
import com.example.demo.service.storage.StorageService;

@Controller
@RequestMapping("admin/categories/detail/{categoryId}/chapters/detail/{chapterId}/lessons/detail/{lessonId}/posts")

public class PostController {
	@Autowired
	private PostService postService;

	@Autowired
	private LessonService lessonService;

	@Autowired
	private ChapterService chapterService;
	
	@Autowired
	private StorageService storageService;

	@Autowired
	private TypeService typeService;
	
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
	public List<LessonDto> getLessons() {
		return lessonService.findAll().stream().map((item) -> {
			LessonDto lessonDto = new LessonDto();
			BeanUtils.copyProperties(item, lessonDto);
			return lessonDto;
		}).toList();
	}
	
	@ModelAttribute("types")
	public List<TypeDto> getTypes() {
		return typeService.findAll().stream().map((item) -> {
			TypeDto typeDto = new TypeDto();
			BeanUtils.copyProperties(item, typeDto);
			return typeDto;
		}).toList();
	}
	
	@GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverImage(@PathVariable String filename) {
		System.out.print(filename);
		storageService.setRootLocation("uploads/images/post");
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_GIF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serverFile(@PathVariable String filename) {
		storageService.setRootLocation("uploads/files/post");
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename\"" + file.getFilename() + "\"")
				.body(file);
	}

	@GetMapping("")
	public String get(@PathVariable("lessonId") Long lessonId,
			@RequestParam(name = "name", required = false) String name, ModelMap model) {
		Post post = postService.findByLessonId(lessonId);
		model.addAttribute("post", post);
		return "admin/post/list";
	}

	@GetMapping("add")
	public String add(@PathVariable("lessonId") Long lessonId, Model model) {
		PostDto postDto = new PostDto();
		postDto.setLessonId(lessonId);
		model.addAttribute("post", postDto);
		return "admin/post/form";
	}

	@GetMapping("edit/{id}")
	public ModelAndView edit(@PathVariable("categoryId") Long categoryId, 
			@PathVariable("chapterId") Long chapterId,
			@PathVariable("lessonId") Long lessonId,
			@PathVariable("id") Long id, ModelMap model) {
		Optional<Post> optional = postService.findById(id);
		PostDto postDto = new PostDto();
		if (!optional.isEmpty()) {
			Post post = optional.get();
			BeanUtils.copyProperties(post, postDto);
			postDto.setLessonId(post.getLesson().getId());
			postDto.setTypeId(post.getType().getId());
			postDto.setEdit(true);
			model.addAttribute("post", postDto);
			return new ModelAndView("admin/post/form", model);
		}
		return new ModelAndView(
				"redirect:/admin/categories/detail/" + categoryId + "/chapters/detail/" + chapterId
				+ "/lessons/detail/" + lessonId + "/posts",
				model);
	}
	
	@PostMapping("save")
	public ModelAndView save(@PathVariable("categoryId") Long categoryId, @PathVariable("chapterId") Long chapterId,
			@Valid @ModelAttribute("post") PostDto postDto, BindingResult result,
			ModelMap model,
			@RequestHeader(value = "referer", required = false) String referer,
			final RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/post/form");
		}
		Post post = new Post();
		Lesson lesson = new Lesson();
		lesson.setId(postDto.getLessonId());
		post.setLesson(lesson);
		Type type = new Type();
		type.setId(postDto.getTypeId());
		post.setType(type);
		BeanUtils.copyProperties(postDto, post);
		if(postDto.getFilePdf()!=null && !postDto.getFilePdf().isEmpty()) {
			storageService.setRootLocation("uploads/files/post");
			if (postDto.isEdit() && postDto.getFile()!=null) {
				try {
					storageService.delete(postDto.getFile());
				} catch (Exception e) {
					// TODO: handle exception
					model.addAttribute("error", "Some thing error!");
					return new ModelAndView("admin/post/form", model);
				}
			}
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			post.setFile(storageService.getStoredFileName(postDto.getFilePdf(), uuString));
			try {
				storageService.store(postDto.getFilePdf(), post.getFile());
			} catch (Exception e) {
				// TODO: handle exception
				model.addAttribute("error", "Cant not save file pdf");
				return new ModelAndView("admin/post/form", model);
			}
		}
		if(postDto.getImageFile()!=null && !postDto.getImageFile().isEmpty()) {
			storageService.setRootLocation("uploads/images/post");
			if (postDto.isEdit() && postDto.getImage()!=null) {
				try {
					storageService.delete(postDto.getImage());
				} catch (Exception e) {
					// TODO: handle exception
					model.addAttribute("error", "Some thing error!");
					return new ModelAndView("admin/post/form", model);
				}
			}
			UUID uuid = UUID.randomUUID();
			String uuString = uuid.toString();
			post.setImage(storageService.getStoredFileName(postDto.getImageFile(), uuString));
			try {
				storageService.store(postDto.getImageFile(), post.getImage());
			} catch (Exception e) {
				// TODO: handle exception
				model.addAttribute("error", "Cant not save file image");
				return new ModelAndView("admin/post/form", model);
			}
		}
		post.setCreated_at(new Date());
		post.setUpdated_at(new Date());
		postService.save(post);	
		redirectAttributes.addFlashAttribute("success", "Post save success!");
		if (postDto.isEdit()) {
			return new ModelAndView("redirect:" + referer);
		}
		return new ModelAndView("redirect:/admin/categories/detail/" + categoryId + "/chapters/detail/" + chapterId
				+ "/lessons/detail/" + postDto.getLessonId() + "/posts");
	}
	
	
	@PostMapping("media/upload")
	@ResponseBody
	public String media() {
		return "Upload success";
	}
}
